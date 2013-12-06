package pbx;

import java.util.ArrayList;

public class ChannelObjectPool {
	
	private ArrayList<ChannelObject> objects = new ArrayList();
	public void putChannelObject(ChannelObject co){	
		objects.add(co);
	}
	public void syncChannelObject(ChannelObject co){
		for(int i=0; i<objects.size(); i++){
			ChannelObject nco=(ChannelObject)objects.get(i);
			if(nco.isEquel(co))
				nco.syncChannelObject(co);
		}
	}
	public void removeChannelObject(ChannelObject co){
		int index=isInPool(co);
		if(index >= 0)
			objects.remove(index);
	}
	public ChannelObject getChannelObject(ChannelObject co){
		for(int i=0; i<objects.size(); i++){
			ChannelObject nco=(ChannelObject)objects.get(i);
			if(nco.isEquel(co))
				return nco;
		}
		return null;
	}
	public ChannelObject getLikeChannelObject(ChannelObject co){
		for(int i=0; i<objects.size(); i++){
			ChannelObject nco=(ChannelObject)objects.get(i);
			if(nco.isLike(co))
				return nco;
		}
		return null;
	}
	
	public int  isInPool(ChannelObject co){
		for(int i=0; i<objects.size(); i++){
			ChannelObject nco=(ChannelObject)objects.get(i);
			if(nco.isEquel(co))
				return i;
		}
		return -1;
	}
	public Integer size(){
		return  this.objects.size();
	}
}
