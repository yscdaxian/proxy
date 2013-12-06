package timer;
import java.util.Timer;

import event.ProxyAgentStatusEvent;
import event.ProxyLiveCallEvent;
import event.ProxyQueueMemberStatusEvent;
import pbx.Pbx;

public   class HoldeTimerTask extends java.util.TimerTask {
	private Pbx pbx;
	public HoldeTimerTask(Pbx pbx){
		this.pbx=pbx;
	}
	
	public void run() {
        // TODO Auto-generated method stub
		try{
			if(pbx != null){
				ProxyQueueMemberStatusEvent proxyQueueMemberStatusEvent = new ProxyQueueMemberStatusEvent(pbx.getAllAgents());    
				this.pbx.getSender().sendEventToAllClient(proxyQueueMemberStatusEvent);
			    
				ProxyAgentStatusEvent proxyAgentStatusEvent = new ProxyAgentStatusEvent(pbx.getAllAgents());    
			    this.pbx.getSender().sendEventToAllClient(proxyAgentStatusEvent);
			    
			    ProxyLiveCallEvent proxyLiveCallEvent = new ProxyLiveCallEvent(pbx.getLiveCalls());
			    this.pbx.getSender().sendEventToAllClient(proxyLiveCallEvent);
			}
		    
		}catch(Exception ex){
			
		}
    }
}
