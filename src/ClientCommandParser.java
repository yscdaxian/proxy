import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

import client.SessionAgentMapPool;
import client.SessionPool;


import pbx.Pbx;

public class ClientCommandParser extends IoHandlerAdapter {
	Pbx	pbx;
	public ClientCommandParser(Pbx pbx){
		this.pbx=pbx;
	}
	
	public void exceptionCaught(IoSession session, Throwable cause)
    throws Exception{
	     cause.printStackTrace();
	}
		
	public void messageReceived(IoSession session, Object message)
			throws Exception {

		String msg=message.toString(); 
        try{
        	JSONParser parser = new JSONParser(); 
    		ContainerFactory containerFactory = new ContainerFactory(){
    		  public List creatArrayContainer() {
    		    return new LinkedList();
    		  }
    		  public Map createObjectContainer() {
    		    return new LinkedHashMap();
    		  }	                      
    		};
    		int cmdType=-1;
    		
    		Map mapMsg = (Map)parser.parse(msg, containerFactory);
    		cmdType=Integer.parseInt(mapMsg.get("cmdType").toString());
    		switch(cmdType){
			case 1:
				//loginin
				loginIn(mapMsg,session);
				break;
			case 2:
				//loginoff
				loginOut(mapMsg,session);
				break;
			case 3:
				//makebusy
				makeBusy(mapMsg,session);
				break;
			case 5:
				//queuepaused
				break;
			case 7:
				//transfer
				transfer(mapMsg,session);
				break;
			case 8:
				//nwaycallstart
				break;
			case 9:
				//NwayCallAddOneCmd
				break;
		}
        	
        }catch (Exception ex)
	    {}
	}
   private  void loginIn(Map msg, IoSession session){
	   Boolean ret=false;
	   String agent=msg.get("requester").toString();
	   SessionAgentMapPool.instance().add(session, agent);
	   ret=this.pbx.loginIn(agent);
	   if(ret){		 
		   response(1,true, session);
	   }
	   else{
		   response(1,false, session);
	   }
   }
   
   private 	void loginOut(Map msg, IoSession session){
	   Boolean ret=false;
	   String agent=msg.get("requester").toString();
	   ret=this.pbx.loginOut(agent);
	   if(ret){
		   response(2,true, session);
	   }
	   else{
		   response(2,false, session);
	   }
   }
   private 	void makeBusy(Map msg, IoSession session){
	   Boolean ret=false;
	   String agent=msg.get("requester").toString();
	   Boolean isBusy=(Boolean)msg.get("isBusy")?true:false;
	   ret=this.pbx.makeBusy(agent,isBusy);
	   if(ret)
		   response(3,true, session);
	   else
		   response(3,false, session);
   }
   private  void transfer(Map msg, IoSession session){
	   int mod=new Integer((String)msg.get("mod"));
	   String oldExten=(String)msg.get("oldExt");
	   String newAgent=(String)msg.get("newExt");   
	   Boolean ret=this.pbx.transfer(oldExten,newAgent,mod);
	   if(ret){
		   response(7,true, session);
	   }
	   else{
		   response(7,false, session);
	   }
   }
   private  Boolean response(int cmdType,Boolean isOk, IoSession addr){
			Map data=new HashMap();
			data.put("eventId", cmdType+10);
			data.put("status", isOk);
		    data.put("eventName", "CmdResult");
			addr.write(JSONValue.toJSONString(data));
		return true;
  }
   public void messageSent(IoSession session, Object message){
   }
	
   public void sessionCreated(IoSession session){
	   SessionPool.AddSession(session);
   }
 
	public void sessionClosed(IoSession session){
	  SessionPool.RemoveSession(session);	
	  SessionAgentMapPool.instance().remove(session);
	}
	
	public void sessionOpened(IoSession session){
	}
		
	public void sessionIdle(IoSession session, IdleStatus status)
     throws Exception{
    }
	
}
