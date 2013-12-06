package client;

import java.util.ArrayList;

import org.apache.mina.core.session.IoSession;

import event.ProxyEvent;

public class SendEventToClients {
	public Boolean sendEventToAllClient(ProxyEvent proxyEvent){
		Boolean ret=false;
		String msg=proxyEvent.getMsg();
		ArrayList<IoSession> sessions=SessionPool.GetAllSession();
		for(int i=0; i<sessions.size(); i++){
			sessions.get(i).write(msg);
		}
		
		return ret;
	}
}
