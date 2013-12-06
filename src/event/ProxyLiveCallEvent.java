package event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;

import pbx.PbxLiveCall;


public class ProxyLiveCallEvent extends ProxyEvent
{
  public ProxyLiveCallEvent(ArrayList<PbxLiveCall> pbxLiveCalls)
   {
    Map msg = new HashMap();
     msg.put("eventId", Integer.valueOf(11));
     msg.put("eventName", "ProxyLiveCallEvent");

     Map msgBody = new HashMap();

     JSONArray allExInfo = new JSONArray();
     ArrayList pool = pbxLiveCalls;
     for (int i = 0; i < pool.size(); ++i) {
         PbxLiveCall liveCall = (PbxLiveCall)pool.get(i);
         JSONArray oneItem = new JSONArray();
         oneItem.add(liveCall.getCallId());
         oneItem.add(liveCall.isConnected().toString().toLowerCase());
         oneItem.add(liveCall.getCallType());
         oneItem.add(liveCall.getCaller());
         oneItem.add(liveCall.getCalled());
         oneItem.add(liveCall.getCallStatus());
         oneItem.add(liveCall.getLinkStartTime());
         oneItem.add(liveCall.getQueueStartTime());
         oneItem.add(liveCall.getCallStartTime());
         oneItem.add(liveCall.getHangupStartTime());
         allExInfo.add(oneItem);
    }

    msg.put("eventEx", allExInfo);
    super.bindMsg(msg);
  }
 }
