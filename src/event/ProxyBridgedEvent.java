package event;

import java.util.HashMap;
import java.util.Map;

public class ProxyBridgedEvent extends ProxyEvent {
	public ProxyBridgedEvent(String caller,String called, String callType, String uniquied){
		Map msg=new HashMap();
		msg.put("eventId", 1);
		msg.put("eventName", "AgentStateChanged");
		Map msgBody=new HashMap();
		msgBody.put("exten", caller);
		msgBody.put("relatedCallNum", called);
		msgBody.put("uniqueid",uniquied);
		msgBody.put("floatInfo", callType);
		msgBody.put("status", "InPhone");
		msg.put("eventBody", msgBody);		
		super.bindMsg(msg);
	}
	
	
}
