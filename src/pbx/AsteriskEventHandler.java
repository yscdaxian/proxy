package pbx;

import java.util.ArrayList;

import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.event.*;

import org.asteriskjava.manager.event.OriginateResponseEvent;

import event.ProxyBridgedEvent;
import event.ProxyOriginateResponseEvent;

import pbx.*;
import java.util.Map;
import java.util.HashMap;

public class AsteriskEventHandler implements ManagerEventListener {
	private 	Pbx pbx;
	private  	BridgeObjectPool  bridgeObjectPool;
	private  	ChannelObjectPool channelObjectPool;
	
	public Pbx getPbx(){
		return this.pbx;
	}
	
	public BridgeObjectPool getBridgeObjectPool(){
		return this.bridgeObjectPool;
	}
	public ChannelObjectPool getchannelObjectPool(){
		return this.channelObjectPool;
	}
	public AsteriskEventHandler(Pbx pbx){
		this.bridgeObjectPool=new BridgeObjectPool();
		this.channelObjectPool=new ChannelObjectPool();
		this.pbx=pbx;
		this.allRecvEvent.add("NewChannelEvent");
		this.allRecvEvent.add("BridgeEvent");
	    this.allRecvEvent.add("ExtensionStatusEvent");
	    this.allRecvEvent.add("HangupEvent");
	    this.allRecvEvent.add("DialEvent");
		this.allRecvEvent.add("NewStateEvent");
	    this.allRecvEvent.add("JoinEvent");
	    this.allRecvEvent.add("LeaveEvent");
        this.allRecvEvent.add("AgentCalledEvent");
        this.allRecvEvent.add("HoldEvent");
		this.allRecvEvent.add("TransferEvent");
		this.allRecvEvent.add("LinkEvent");
		this.allRecvEvent.add("UnLinkEvent");
	}
	private ArrayList<String> allRecvEvent = new ArrayList();
	public Boolean compareString(String scalssname, String dclassname) {
		return Boolean.valueOf(scalssname.equalsIgnoreCase(dclassname));
	}
	private Boolean IfRecvEevent(String event)
    {			 
		for (int i = 0; i < this.allRecvEvent.size(); i++){
        if (compareString((String)this.allRecvEvent.get(i), event).booleanValue())
	         return Boolean.valueOf(true);
	     }
	     return Boolean.valueOf(false);
   }
	public void onManagerEvent(ManagerEvent me) {
		String className=me.getClass().getSimpleName().toString();
		String classname = me.getClass().getSimpleName().toString();

		//if (IfRecvEevent(classname).booleanValue()){
			
		//}
		if(className.compareTo("NewChannelEvent") == 0){
			onEventHandler((NewChannelEvent)me);
		}else if(className.compareTo("NewStateEvent") == 0){
			onEventHandler((NewStateEvent)me);
		}else if(className.compareTo("ExtensionStatusEvent") == 0){
			onEventHandler((ExtensionStatusEvent)me);
		}else if(className.compareTo("DialEvent") == 0){
			onEventHandler((DialEvent)me);
		}else if(className.compareTo("HangupEvent") == 0){
			onEventHandler((HangupEvent)me);
		}else if(className.compareTo("JoinEvent") == 0){
			onEventHandler((JoinEvent)me);
		}else if(className.compareTo("LeaveEvent") == 0){
			onEventHandler((LeaveEvent)me);
		}else if(className.compareTo("AgentCalledEvent") == 0){
			onEventHandler((AgentCalledEvent)me);
		}else if(className.compareTo("HoldEvent") == 0){
			onEventHandler((HoldEvent)me);
		}else if(className.compareTo("TransferEvent") == 0){
			onEventHandler((TransferEvent)me);
		}else if(className.compareTo("UnlinkEvent") == 0){
			onEventHandler((UnlinkEvent)me);
		}else if(className.compareTo("BridgeEvent") == 0){
			onEventHandler((BridgeEvent)me);
		}else if(className.compareTo("OriginateResponseEvent") == 0){
			onEventHandler((OriginateResponseEvent)me);
		}
		
	}
	private void onEventHandler(OriginateResponseEvent event){
		ProxyOriginateResponseEvent proxyBridgedEvent=new ProxyOriginateResponseEvent(event.getChannel(),event.getCallerIdNum(), event.getExten(),event.getContext(), event.isSuccess(), event.getReason(),event.getResponse());				
		
		this.pbx.getSender().sendEventToAllClient(proxyBridgedEvent);
	}
	
	private void onEventHandler(NewChannelEvent event){

		ChannelObject channelObject=new ChannelObject(event);
		this.channelObjectPool.putChannelObject(channelObject);	
	}
	private void onEventHandler(UnlinkEvent event){
		//this.bridgeObjectPool.removeBridgeObject(new BridgeObject(event));
	}
	private void onEventHandler(NewStateEvent event){
	
		 ChannelObject newChannelObject=new ChannelObject(event);
		 this.channelObjectPool.syncChannelObject(newChannelObject);
	}
	private void onEventHandler(ExtensionStatusEvent event){
				
	}
	
	private void onEventHandler(DialEvent event){
		//System.out.print(event.toString());
	}
	
	private void onEventHandler(HangupEvent event){

		HangupEvent he = (HangupEvent)event;
	
		ChannelObject channelObject=new ChannelObject(event);
		this.channelObjectPool.removeChannelObject(channelObject);
		if(!channelObject.isLocalChannel())
			this.bridgeObjectPool.removeBridgeObject(channelObject);
		
	}
	

	
	private void onEventHandler(JoinEvent event){

	}
	private void onEventHandler(LeaveEvent event){

	}
	
	private void onEventHandler(AgentCalledEvent event){

		
	}
	private void onEventHandler(HoldEvent event){
		
	}
	private void onEventHandler(TransferEvent event){
		
			
	}

	private void onEventHandler(BridgeEvent event){

		if(event.isLink()){
			BridgeObject newBridgeObject=new BridgeObject(event);
			BridgeObject bridgeObject=this.bridgeObjectPool.getBridgeObject(newBridgeObject);
			
			if(!(bridgeObject == null)){
				bridgeObject.syncBridgeObject(newBridgeObject);
				if(!bridgeObject.isSend() && bridgeObject.isComplement()){
					
					ChannelObject srcChannel=this.channelObjectPool.getChannelObject(bridgeObject.getSrcChannel());
					ChannelObject dstChannel=this.channelObjectPool.getChannelObject(bridgeObject.getDstChannel());
					if(dstChannel.getContext().equals("callout"))
						dstChannel.setReleatedNum(dstChannel.getLocalExten(dstChannel.getChannel()));
				    //删除旧的bridgeObject用包含新信息的nBridgeObject替代
					this.bridgeObjectPool.removeBridgeObject(bridgeObject);
					
					BridgeObject nBridgeObject=new BridgeObject(srcChannel,dstChannel);
					nBridgeObject.setSended(true);
					nBridgeObject.setRemoveCount(2);
					this.bridgeObjectPool.putBridgeObject(nBridgeObject);	
					ProxyBridgedEvent proxyBridgedEvent=new ProxyBridgedEvent(srcChannel.getReleatedNum(), dstChannel.getReleatedNum(), srcChannel.getContext(), srcChannel.getUniqueid());				
					this.pbx.getSender().sendEventToAllClient(proxyBridgedEvent);
				}
			}else{
				
				if(newBridgeObject.isComplement()){
					ChannelObject srcChannel=this.channelObjectPool.getChannelObject(newBridgeObject.getSrcChannel());
					ChannelObject dstChannel=this.channelObjectPool.getChannelObject(newBridgeObject.getDstChannel());
					if(dstChannel.getContext().equals("callout"))
						dstChannel.setReleatedNum(dstChannel.getLocalExten(dstChannel.getChannel()));
					
					newBridgeObject=new BridgeObject(srcChannel,dstChannel);
					ProxyBridgedEvent proxyBridgedEvent=new ProxyBridgedEvent(srcChannel.getReleatedNum(), dstChannel.getReleatedNum(), srcChannel.getContext(), srcChannel.getUniqueid());				
					this.pbx.getSender().sendEventToAllClient(proxyBridgedEvent);			
				}
				this.bridgeObjectPool.putBridgeObject(newBridgeObject);
			}				
		}else{
			this.bridgeObjectPool.removeBridgeObject(new BridgeObject(event));
		}
	}
	
}
