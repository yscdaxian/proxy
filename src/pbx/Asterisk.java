package pbx;

import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import log.Log;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.action.AbstractManagerAction;
import org.asteriskjava.manager.action.AtxferAction;
import org.asteriskjava.manager.action.QueueAddAction;
import org.asteriskjava.manager.action.QueuePauseAction;
import org.asteriskjava.manager.action.QueueRemoveAction;
import org.asteriskjava.manager.action.RedirectAction;
import org.asteriskjava.manager.response.ManagerResponse;

import client.SendEventToClients;
import com.mchange.v2.resourcepool.TimeoutException;
import dbhelper.DbPool;

public class Asterisk extends Pbx {
	private static int  CPU_POOL_SIZE=10;
	private ManagerConnectionFactory managerFactory = null;
	private ManagerConnection managerConnection = null;
	private AsteriskEventHandler pbxEventHandler = null;
	private ExecutorService executorService;
	private String 	pbxHost;
	private int	    pbxPort;
	private String 	pbxUser;
	private String 	pbxPwd;
	
	
	public  SendEventToClients sendEventToClients;
	
	public Asterisk(){
		
	}
	public void setSender(SendEventToClients sendEventToClients){
		this.sendEventToClients=sendEventToClients;
	}
	public SendEventToClients getSender(){
		return this.sendEventToClients;
	}
	@Override
	public void setPbxAddr(String pbxAddr) {
		// TODO Auto-generated method stub
		this.pbxHost=pbxAddr;
	}
	@Override
	public void setPbxPort(int port) {
		// TODO Auto-generated method stub
		this.pbxPort=port;
	}
	public void setPbxUser(String user) {
		// TODO Auto-generated method stub
		this.pbxUser=user;
	}
	public void setPbxPwd(String pwd){
		this.pbxPwd=pwd;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		/*
	    this.executorService = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors() * CPU_POOL_SIZE);
	    if (this.executorService != null) {	    	    
		    	this.executorService.execute(this);	
		    	
	    }
	    */
	}
	
	public 		Boolean Init(){
		this.managerFactory = new ManagerConnectionFactory(this.pbxHost,this.pbxPort, this.pbxUser, this.pbxPwd);
		this.managerConnection = this.managerFactory.createManagerConnection();
		AsteriskEventHandler pbxEventHandle = new AsteriskEventHandler(this);
    	this.pbxEventHandler = pbxEventHandle;
    	this.managerConnection.addEventListener(pbxEventHandle);
    	return true;
	}
	public   	Boolean pbxAuthority(){
		Boolean ret=false;
		if (this.managerConnection != null) {
		       try {
			        this.managerConnection.login();      
			        ret=true;
			   }
		       catch (IllegalStateException ie) {
		    	   Log.getInstance().error("pbxAuthority fail:"+ie.toString());
		    	   return Boolean.valueOf(false);
			   }catch (AuthenticationFailedException ae) {
				   Log.getInstance().error("pbxAuthority fail:"+ae.toString());
				   return false;
			   } catch (IOException ex) {
				   Log.getInstance().error("pbxAuthority fail:"+ex.toString());
				   return false;
			   } catch (org.asteriskjava.manager.TimeoutException e) {
				// TODO Auto-generated catch block
				   Log.getInstance().error("pbxAuthority fail:"+e.toString());
				return false;
			  }
		}
		return ret;		
	}
	
	@Override
	public  Boolean loginIn(String agent){
		// TODO Auto-generated method stub
		Boolean ret=false;
		Statement stmt = null;
		ResultSet rs = null;
	    Connection con = null;
	    Boolean localBoolean;
		try{
			    con = DbPool.getInstance().getConnect();
			    stmt = con.createStatement();
			    String sql = "select queue_name,interface,penalty from tx_queue_member where membername='" + agent + "'";
		    	rs = stmt.executeQuery(sql);
		    	if(rs == null)
		    		return false;
		    	while (rs.next()){
		    		   QueueAddAction qaa = new QueueAddAction();
		    		   String inter = rs.getString("interface");
		    		   qaa.setInterface(inter.substring(0, inter.length() - 1));
					   qaa.setPenalty(rs.getInt("penalty"));
				       qaa.setQueue(rs.getString("queue_name"));
				       ManagerResponse mr = this.managerConnection.sendAction(qaa);
	    			   if (mr.getResponse().equalsIgnoreCase("Success"))
	    				   ret=true;
		    	}
			  
		       if (!isAgentLogin(agent).booleanValue()) {   
		    	   sql = "update  cc_live_agent set login=1, login_stime=now() where agent='" + agent + "'";
		    	   if (!stmt.execute(sql)) {
		    		   sql = "insert into  cc_live_agent  (agent,login,login_stime,agent_status,agent_cstatus_stime) values('" + agent + "',1,now(),'sbusy',now())";
		    		   stmt.execute(sql);	   
		    	   }
		    	   ret=true;
		       }
		 }
	     catch (Exception ex) {
	    	 return ret;
	     } finally{
		       if (con != null)
			       try {
			    	   con.close();
			    	   con=null;
			       } catch (Exception localException7) {}
		       if (stmt != null)
			       try {
			           stmt.close(); 
			           stmt=null;
			       } 
			       catch (Exception localException8) {
		           }
		       if (rs != null)
			       try {
			           rs.close();
			           rs=null;
			       }
			       catch (Exception localException9){
			       }
		 }	  			
		 return ret;
	}
	@Override
	public  Boolean loginOut(String agent){
		// TODO Auto-generated method stub
		Boolean ret=false;
		Statement stmt = null;
	    Connection con = null;
	    ResultSet rs = null;
	    Boolean localBoolean;
	    try {
		     	con = DbPool.getInstance().getConnect();
	         	stmt = con.createStatement();
	         	String sql = "select queue_name,interface from tx_queue_member where membername='" + agent + "'";
		    	rs = stmt.executeQuery(sql);
		    	if(rs == null)
		    		return false;
		    	while (rs.next()){
		    		   QueueRemoveAction qra = new QueueRemoveAction();
		    		   String inter = rs.getString("interface");
		    		   qra.setInterface(inter.substring(0, inter.length() - 1));
		    		   qra.setQueue(rs.getString("queue_name"));
				       ManagerResponse mr = this.managerConnection.sendAction(qra);
	    			   if (mr.getResponse().equalsIgnoreCase("Success"))
	    				   ret=true;
		    	}
	         
	         sql = "update tx_queue_member set paused =1 where membername='" + agent + "'";
		     stmt.execute(sql);
		     sql = "delete from    cc_live_agent  where login=1 and agent='" + agent + "'";
		     stmt.execute(sql);
		     ret=true;    
		}
		catch (Exception ex) {
			return Boolean.valueOf(false);
	    } finally {
	    	if (con != null)
		        try {
		        	con.close(); 
		        } catch (Exception localException5) {}
	    	if (rs != null)
		    	try{
		    		rs.close();
		    		rs=null;
		    	}catch(Exception ex){
		    		
		    	}
	    	if (stmt != null)
		    	try {
		    		stmt.close();
		    		stmt=null;
		    	}
		        catch (Exception localException6){}
	    }
		return ret;
	}
	
	public  Boolean makeBusy(String agent, Boolean isBusy){
		Boolean ret=false;
		
		Integer status = Integer.valueOf((isBusy.booleanValue()) ? 1 : 0);
		Connection con = null;
		Statement stmt = null;
	    ResultSet rs = null;
		
	    try{
	    	String sql = "select queue_name,interface from tx_queue_member where membername='" + agent + "'";
	    	con = DbPool.getInstance().getConnect();
	    	stmt = con.createStatement();
	    	rs = stmt.executeQuery(sql);
	    	if(rs == null)
	    		return false;
	    	while (rs.next()){
	    				  QueuePauseAction qpa = new QueuePauseAction();
	    				  String inter = rs.getString("interface");
	    				  qpa.setInterface(inter.substring(0, inter.length() - 1));

	    				  qpa.setPaused(isBusy);
	    				  qpa.setQueue(rs.getString("queue_name"));
	    				  ManagerResponse mr = this.managerConnection.sendAction(qpa);
	    				  if (mr.getResponse().equalsIgnoreCase("Success")){
	    					  sql = "update tx_queue_member set paused =" + status.toString() + " where membername='" + agent + "'";
		    				  stmt.execute(sql);
		    				  
		    				  if (status.intValue() == 1)
		    				  {
		    					  sql = "update cc_live_agent set agent_status='sbusy',agent_cstatus_stime=now() where agent='" + agent + "' and login=1";
		    					  stmt.execute(sql);
		    				  } else {
		    					  sql = "update cc_live_agent set agent_status='sidle',agent_cstatus_stime=now() where agent='" + agent + "' and login=1";
		    					  stmt.execute(sql);
		    				  }
		    				  
		    				  sql = "insert into cc_agent_log(agent,action,stime,etime)  select agent,agent_status,agent_cstatus_stime,now() from cc_live_agent where  agent='" + 
		    						  agent + "'";
		    				  stmt.execute(sql);
		    				  ret =true;
	    				  }		
	    				}
	    	
	    }
	    catch (Exception ex){
	    		return Boolean.valueOf(false);
	    } finally {
	    	if (con != null)
	    		try {
	    			con.close(); } catch (Exception localException4) {
	    			}
	    	if (rs != null)
	    		try {
	    			rs.close();
	    		}
	    	catch (Exception localException5) {
	    	}
	    	if (stmt != null)
	    		try {
	    			stmt.close();
	    		}
	    	catch (Exception localException6)
	    	{
	    	}
	    }
	    
		return ret;
	}
	
	public Boolean transfer(String oldExten, String newExten,int mod){
		
			BridgeObject sbo=this.pbxEventHandler.getBridgeObjectPool().getBridgeObject(oldExten);
			if(sbo != null){
				 String transferChannel="";						
				 String releatedNum=sbo.getDstChannel().getReleatedNum();
				 if(mod == 2){
					transferChannel=releatedNum.equals(oldExten)?sbo.getSrcChannel().getChannel():sbo.getSrcChannel().getChannel();
				 }else{
					transferChannel=releatedNum.equals(oldExten)?sbo.getDstChannel().getChannel():sbo.getSrcChannel().getChannel();
				 }
				 String channel=transferChannel;
				 String context="cc-transfer";
				 String exten=newExten;
				 AbstractManagerAction am = null;
				 
				 switch (mod)
			     {
					     case 1:
					       AtxferAction aa = new AtxferAction();
					       aa.setChannel(channel);
					       aa.setContext(context);
					       aa.setExten(exten);
					       aa.setPriority(1);
					       am = aa;
					 
					       		break;
					    case 2:
					      RedirectAction ra = new RedirectAction();
					       ra.setChannel(channel);
					       ra.setContext(context);
					       ra.setExten(exten);
					       ra.setPriority(1);
					       am = ra;
									
				    }
				
				    try{
					       ManagerResponse mr = this.managerConnection.sendAction(am);
					       if (mr.getResponse().equalsIgnoreCase("Success")){
					         return true;
					       }     
					}
					catch (Exception ex){
					    	 return false;
					     }
					    
		}	
			
		return false;
		
	}
	public Boolean isAgentLogin(String agent) {
		     Boolean ret = Boolean.valueOf(false);
		     Statement stmt = null;
		     ResultSet rs = null;
		     Connection con = null;
		     try{
		    	 	String sql = "select count(agent) as size from cc_live_agent where login=1 and agent='" + agent + "'";
		    	 	con = DbPool.getInstance().getConnect();
		            stmt = con.createStatement();
		            rs = stmt.executeQuery(sql);
		            rs.next();
		            Integer loginCount = Integer.valueOf(Integer.parseInt(rs.getString("size")));
		            if (loginCount.intValue() <= 0)
		            	ret = Boolean.valueOf(false);
		            	else
		            	ret = Boolean.valueOf(true);
		     		}
		     		catch (Exception ex) {
		     			return Boolean.valueOf(false);
		     		} finally {
		     		if (con != null)
		     		try {
		     			con.close();
		     			con=null;
		     		} catch (Exception localException4) {
		     		}
		     		if (stmt != null)
		     		try {
		     			stmt.close(); 
		     			stmt=null;
		     		} 
		     		catch (Exception localException5) {
		     		}
		     		if (rs != null)
		     		try {
		     			rs.close();
		     			rs=null;
		     		} catch (Exception localException6) {
		     		}
		       }
		       return ret;
	 }
	 public ArrayList<PbxLiveCall> getLiveCalls() {
		    ArrayList pool = new ArrayList();
		     Statement stmt = null;
		      ResultSet rs = null;
		     Connection con = null;
		     try {
		        con = DbPool.getInstance().getConnect();
		       stmt = con.createStatement();
		       rs = stmt.executeQuery("select call_id,caller,called,call_type,status,link_stime,queue_stime,call_stime,hangup_stime,connected from cc_live_call");
		       while (rs.next()) {
		    	   PbxLiveCall pbxLiveCall = new PbxLiveCall();
		    	   pbxLiveCall.setCallId(rs.getString("call_id"));
		    	   pbxLiveCall.setCaller(rs.getString("caller"));
		    	   pbxLiveCall.setCalled(rs.getString("called"));
		    	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		    	   pbxLiveCall.setCallType(new Integer(rs.getInt("call_type")).toString());
		    	   pbxLiveCall.setCallStatus(rs.getString("status"));
		    	   
		    	   Timestamp tTime = rs.getTimestamp("link_stime");
		    	   if (tTime != null)
		    		   	pbxLiveCall.setLinkStartTime(sdf.format(tTime));
		    	   		tTime = rs.getTimestamp("queue_stime");
		    	   		if (tTime != null)
		    	   			pbxLiveCall.setQueueStartTime(sdf.format(tTime));
		    	   			tTime = rs.getTimestamp("call_stime");
		    	   			if (tTime != null)
		    	   				pbxLiveCall.setCallStartTime(sdf.format(tTime));
		    	   				tTime = rs.getTimestamp("hangup_stime");
		    	   				if (tTime != null)
		    	   					pbxLiveCall.setHangupStartTime(sdf.format(tTime));
		    	   					pbxLiveCall.setConnected(Boolean.valueOf(rs.getInt("connected") == 1));
		    	   					
		    	   					label294: pool.add(pbxLiveCall);
		       	}
		       
		     }
		     catch (SQLException ex){
		    	 return pool;
		     } finally {
		    	 if (con != null)
		    		 try {
		    			 con.close();
		    			 con = null;
		    		 } catch (Exception localException3) {
		    		 }
		    	 if (rs != null)
		    		 try {
		    			 rs.close();
		    			 rs = null;
		    		 } catch (Exception localException4) {
        }
      if (stmt != null)
        try {
           stmt.close();
           stmt = null;
         }
         catch (Exception localException5) {
       }
     }
    return pool;
   }

   public ArrayList<PbxAgent> getAllAgents() {
	     ArrayList pool = new ArrayList();
	     Statement stmt = null;
	     ResultSet rs = null;
	     Connection con = null; 
	     try {
		      con = DbPool.getInstance().getConnect();
		      stmt = con.createStatement();
		      String sql = "select agent,login,login_stime,agent_cstatus_stime,agent_status,ext_status,ext_cstatus_stime,paused from cc_live_agent left join tx_queue_member on agent=membername";
		      rs = stmt.executeQuery(sql);
		      if (rs == null) 
		    	  return pool;
		      while (rs.next()) {
		        		Boolean isLogin = Boolean.valueOf(rs.getInt("login") != 0);
		        		Boolean isBusy = Boolean.valueOf(rs.getInt("paused") == 1);
		        		PbxAgent pbxAgent = new PbxAgent(rs.getString("agent"), isLogin, isBusy);
		        		pbxAgent.setPaused(isBusy);
		        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		        		pbxAgent.setLoginStartTime(sdf.format(rs.getTimestamp("login_stime")));
		        		pbxAgent.setAgentStatusStartTime(sdf.format(rs.getTimestamp("agent_cstatus_stime")));
		
		        		pool.add(pbxAgent);
		      }
	     }
	     catch (SQLException ex){
	       return pool;
	     } 
	     finally {
		    if (con != null)
		    	try {
		    		con.close();
		    		con = null;
		    	} catch (Exception localException3) {
		    	}
		    if (rs != null)
		    	try {
		    		rs.close();
		    		rs = null;
		        } catch (Exception localException4) {
		        }
		    if (stmt != null)
		    	try {
		    		stmt.close();
		    		stmt = null;
		        }
		        catch (Exception localException5) {
		        }
		   }
		   return pool;
	  }
}
