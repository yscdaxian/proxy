package pbx;

import java.util.ArrayList;


public class BridgeObjectPool {
	private ArrayList<BridgeObject> objects = new ArrayList();
	public void putBridgeObject(BridgeObject bo){
		if(isInPool(bo) == -1)
			objects.add(bo);
	}
	public void removeBridgeObject(BridgeObject bo){
		
		for(int i=0; i<objects.size(); i++){
			BridgeObject nco=(BridgeObject)objects.get(i);
		    int count=nco.getRemoveCount();
		    nco.setRemoveCount(--count);
			if(nco.isEquel(bo) && count <= 0){
				objects.remove(nco);
				break;
			}
		}
		
	}
	
	public void removeBridgeObject(ChannelObject channelObject){
		for(int i=0; i<objects.size(); i++){
			BridgeObject nco=(BridgeObject)objects.get(i);
			if(channelObject.isEquel(nco.getSrcChannel()) ||channelObject.isEquel(nco.getDstChannel())){
				objects.remove(nco);
				break;
			}
		}
	}
	public BridgeObject getBridgeObject(BridgeObject bridgeObject){
		for(int i=0; i<objects.size(); i++){
			BridgeObject nco=(BridgeObject)objects.get(i);
			if(nco.isEquel(bridgeObject))
				return nco;
		}
		return null;
		
	}
	public BridgeObject getBridgeObject(ChannelObject co){
		for(int i=0; i<objects.size(); i++){
			BridgeObject nco=(BridgeObject)objects.get(i);
			if(nco.isComplement()){
				if(nco.getSrcChannel().isEquel(co) || nco.getDstChannel().isEquel(co))
					return nco;
			}	
		}
		return null;	
	}
	public BridgeObject getBridgeObject(String  agent){
		for(int i=0; i<objects.size(); i++){
			BridgeObject nco=(BridgeObject)objects.get(i);	
			if(nco.isComplement())
				if(nco.srcChannel.getReleatedNum().equals(agent) || nco.dstChannel.getReleatedNum().equals(agent))
					return nco;
		}
		return null;	
	}
	public int isInPool(BridgeObject bo){
		for(int i=0; i<objects.size(); i++){
			BridgeObject nco=(BridgeObject)objects.get(i);
			if(nco.isEquel(bo))
				return i;
		}
		return -1;
	}
	public Integer size(){
		return this.objects.size();
	}
}
