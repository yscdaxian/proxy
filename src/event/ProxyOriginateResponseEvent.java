package event;

import java.util.HashMap;
import java.util.Map;

public class ProxyOriginateResponseEvent extends ProxyEvent {
	public ProxyOriginateResponseEvent(String channel, String callerId, String exten, String context, Boolean isSuccess, Integer iReason,String reason){
		Map msg=new HashMap();
		msg.put("eventId", 5);
		msg.put("eventName", "OriginateResponEvent");
		Map msgBody=new HashMap();
		msgBody.put("channel", channel);
		msgBody.put("exten", exten);
		msgBody.put("context", context);
		msgBody.put("callerId", callerId);
		msgBody.put("isSuccess",isSuccess.toString());
		msgBody.put("iReason", iReason.toString());	
		msgBody.put("reason", reason);	
		
		msg.put("eventBody", msgBody);		
		super.bindMsg(msg);
	}
}
