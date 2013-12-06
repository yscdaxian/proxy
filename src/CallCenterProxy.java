import java.net.InetSocketAddress;
import java.util.Timer;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import client.SendEventToClients;

import pbx.Pbx;
import pbx.Asterisk;
import timer.HoldeTimerTask;
import log.Log;

import config.ConfigParser;
import dbhelper.DbPool;
public class CallCenterProxy {
	private static Pbx		pbx;
	private static ClientCommandParser clientCommandParser;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Boolean 		ret=false;
		ConfigParser	confParser=new ConfigParser();
		ret=confParser.loadConfig();
		if(ret){
			DbPool.getInstance().setDbHost(confParser.getDbHost());
			DbPool.getInstance().setDbName(confParser.getDbName());
			DbPool.getInstance().setDbUser(confParser.getDbUser());
			DbPool.getInstance().setDbPwd(confParser.getDbPwd());
			ret=DbPool.getInstance().init();
			if(ret){
				pbx=new Asterisk();
				pbx.setPbxAddr(confParser.getPbxAddr());
				pbx.setPbxPort(confParser.getPbxPort());
				pbx.setPbxUser(confParser.getPbxUser());
				pbx.setPbxPwd(confParser.getPbxPwd());
				SendEventToClients sender=new SendEventToClients();
				pbx.setSender(sender);
				
				if(pbx.Init() && pbx.pbxAuthority()){
					pbx.run();
					try{
						   IoAcceptor acceptor = new NioSocketAcceptor();
						   clientCommandParser= new ClientCommandParser(pbx);
						   acceptor.getFilterChain().addLast("logger", new LoggingFilter());
						   acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
						   acceptor.setHandler(clientCommandParser);
						   acceptor.getSessionConfig().setReadBufferSize(2048);
						   acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
						   acceptor.bind(new InetSocketAddress(confParser.getProxyPort()));			   
						   Timer timer = new Timer();
						   timer.schedule(new HoldeTimerTask(pbx), 1000, 2000);
			        }
					catch (Exception ex) {
					       Log.getInstance().error("net server start fail:"+ex.toString());
				    }	
				}else{
					Log.getInstance().error("pbxAuthority  fail");
				}
				
			}else{
				Log.getInstance().error("connect database fail");
			}
			
		}
		System.exit(0);
	}

}
