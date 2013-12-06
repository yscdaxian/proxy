package event;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import pbx.PbxAgent;

public class ProxyQueueMemberStatusEvent extends ProxyEvent {
	public ProxyQueueMemberStatusEvent(ArrayList<PbxAgent> eventPool){
		Map msg=new HashMap();
		msg.put("eventId", 7);
		msg.put("eventName", "ProxyQueuePausedEvent");	
		Map msgBody=new HashMap();
		
		ArrayList<PbxAgent> pool=eventPool;
		for(int i=0; i<pool.size();i++){
			PbxAgent agent= pool.get(i);
			msgBody.put(agent.getName(), agent.getPaused().toString().toLowerCase());
		}
		msg.put("eventBody", msgBody);		
		super.bindMsg(msg);
	}
}
