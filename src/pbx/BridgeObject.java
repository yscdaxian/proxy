package pbx;
import org.asteriskjava.manager.event.BridgeEvent;
import org.asteriskjava.manager.event.*;
public class BridgeObject {
	ChannelObject srcChannel;
	ChannelObject dstChannel;
	ChannelObject extenChannel;
	Boolean		  isSend;
	int 		  removeCount;
	public BridgeObject(LinkEvent be){
		this.removeCount=0;
		this.srcChannel=new ChannelObject(be.getUniqueId1(),be.getChannel1());
		this.dstChannel=new ChannelObject(be.getUniqueId2(),be.getChannel2());
	}
	public BridgeObject(UnlinkEvent be){
		this.srcChannel=new ChannelObject(be.getUniqueId1(),be.getChannel1());
		this.dstChannel=new ChannelObject(be.getUniqueId2(),be.getChannel2());
		this.removeCount=0;
	}
	public BridgeObject(BridgeEvent be){
		this.removeCount=0;
		isSend=false;
		this.srcChannel=new ChannelObject(be.getUniqueId1(),be.getChannel1());
		this.dstChannel=new ChannelObject(be.getUniqueId2(),be.getChannel2());
	}
	public BridgeObject(ChannelObject src, ChannelObject dst){
		this.removeCount=0;
		isSend=false;
		this.srcChannel=src;
		this.dstChannel=dst;		
	}
	public Boolean isEquel(BridgeObject bo){
		if(bo.srcChannel.getChannel().equals(this.srcChannel.getChannel()))
			return true;
		if(bo.srcChannel.getChannel().equals(this.dstChannel.getChannel()))
			return true;
		if(bo.srcChannel.isLocalChannel() && this.dstChannel.isLocalChannel()){
			if(bo.srcChannel.getLocalChannel().equals(this.dstChannel.getLocalChannel()))
				return true;
		}
		if(bo.dstChannel.isLocalChannel() && this.srcChannel.isLocalChannel()){
			if(bo.dstChannel.getLocalChannel().equals(this.srcChannel.getLocalChannel()))
				return true;
		}
		return false;
	}
	public Boolean isComplement(){
		if(this.srcChannel.isLocalChannel() || this.dstChannel.isLocalChannel())
			return false;
		return true;
	}
	public Boolean isSend(){
		return this.isSend;
	}
	public void setSended(Boolean flag){
		this.isSend=flag;
		
	}
	public void setRemoveCount(int count){
		this.removeCount=count;
	}
	public int getRemoveCount(){
		return this.removeCount;
	}
	public void syncBridgeObject(BridgeObject be){
		if(!isEquel(be))
			return;
		if(!(this.srcChannel.isEquel(be.srcChannel) && this.dstChannel.isEquel(be.dstChannel))){
			if(this.dstChannel.isLocalChannel()&& !be.dstChannel.isLocalChannel())
				this.dstChannel=be.dstChannel;
			if(this.srcChannel.isLocalChannel() && !be.srcChannel.isLocalChannel())
				this.srcChannel=be.srcChannel;
		}		
	}
	public ChannelObject getSrcChannel(){
		return srcChannel;
	} 
	
	public ChannelObject getDstChannel(){
		return dstChannel;
	}
	
	public ChannelObject getExtenChannel(){
		if(srcChannel.getExten().isEmpty())
			return dstChannel;
		return srcChannel;
	}
}
