/*    */ package event;
 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;

import org.json.simple.JSONArray;

import pbx.PbxAgent;

import client.SessionAgentMapPool;
public class ProxyAgentStatusEvent extends ProxyEvent
{
 public ProxyAgentStatusEvent(ArrayList<PbxAgent> eventPool)
 {
	Map msg = new HashMap();
    msg.put("eventId", Integer.valueOf(8));
    msg.put("eventName", "ProxyAgentStatusEvent");
    Map msgBody = new HashMap();
    
    JSONArray allExInfo = new JSONArray();
     ArrayList pool = eventPool;
     for (int i = 0; i < pool.size(); ++i) {
        PbxAgent agent = (PbxAgent)pool.get(i);
        if (SessionAgentMapPool.instance().isHaveAgent(agent.getName()).booleanValue()) {
        	JSONArray agentInfo = new JSONArray();
          	agentInfo.add(agent.getName());
          	agentInfo.add(agent.getLogin().toString().toLowerCase());
          	agentInfo.add(agent.getLoginStartTime());
          	agentInfo.add(agent.getPaused().toString().toLowerCase());
          	agentInfo.add(agent.getAgentStatusStartTime());
          	allExInfo.add(agentInfo);
        }
        
       msg.put("eventEx", allExInfo);
     }
     
     super.bindMsg(msg);
	}
 }

