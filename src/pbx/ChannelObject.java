package pbx;
import org.asteriskjava.manager.event.NewChannelEvent;
import org.asteriskjava.manager.event.HangupEvent;
import org.asteriskjava.manager.event.NewStateEvent;


public class ChannelObject {
	private String channel;
	private String uniqueid;
	private String ext;
	private String releatedNum;
	private String status;
	private Boolean isLocalChannel;
	private String  localChannel;
	private String  context;
  public  ChannelObject(NewChannelEvent nce){
		this.channel=nce.getChannel();
		this.uniqueid=nce.getUniqueId();
		this.ext=nce.getExten();
	
		this.status=nce.getChannelState().toString();
		this.ext=nce.getExten();
		
		this.context=nce.getContext();
		this.releatedNum=nce.getCallerIdName();
		
		
		this.isLocalChannel=false;
		if(this.channel.toLowerCase().contains("local")){
			this.isLocalChannel=true;
			int index=this.channel.indexOf(';');
			if(index>0)
				this.localChannel=this.channel.substring(0,index-1);
		}
		
	}

  public ChannelObject(NewStateEvent newStateEvent){
		this.uniqueid=newStateEvent.getUniqueId();
		this.releatedNum=newStateEvent.getCallerIdNum();
		
		String callerName=newStateEvent.getCallerIdName();
		//¼æÈÝ×Ô¶¯Íâºô
		if(callerName != null && !callerName.isEmpty()){
			int index=callerName.lastIndexOf('-');
			if(index>0 && index<callerName.length())
				this.releatedNum=callerName.substring(index+1,callerName.length());
		}	
		
	}
	public ChannelObject(HangupEvent hangupEvent){
		this.channel=hangupEvent.getChannel();
		this.uniqueid=hangupEvent.getUniqueId();
		this.isLocalChannel=false;
		if(this.channel.toLowerCase().contains("local")){
			this.isLocalChannel=true;
			int index=this.channel.indexOf(';');
			if(index>0)
				this.localChannel=this.channel.substring(0,index-1);
		}
	}
	public ChannelObject(String uniqueid,String channel){
		this.uniqueid=uniqueid;
		this.channel=channel;
		this.isLocalChannel=false;
		if(this.channel.toLowerCase().contains("local")){
			this.isLocalChannel=true;
			int index=this.channel.indexOf(';');
			if(index>0)
				this.localChannel=this.channel.substring(0,index-1);
		}
	}
	public void syncChannelObject(ChannelObject co){
		this.releatedNum=co.getReleatedNum();	
	}
	public String getUniqueid(){
		return this.uniqueid;
	}
	public String getChannel(){
		if(this.channel == null)
			return "";
		return this.channel;
	}
	public String getExten(){
		if(this.ext == null)
			return "";
		return this.ext;
	}
	public String getReleatedNum(){
		if(this.releatedNum == null)
			return "";
		return this.releatedNum;
	}
	public String getStatus(){
		return this.status;
	}
	public String getContext(){
		if(this.context == null)
			return "";
		return this.context;
	}
	public void setReleatedNum(String num){
		this.releatedNum=num;
	}
	public Boolean isEquel(ChannelObject co){
		if(!this.uniqueid.equals("") && this.uniqueid.equals(co.getUniqueid()))
			return true;
		
		if(!this.channel.equals("") && this.uniqueid.equals(co.getChannel()))
			return true;
	    return false;
	}
	public Boolean isLike(ChannelObject co)
	{
		if(!co.getChannel().equals("")){
			if(this.getChannel().toLowerCase().contains(co.getChannel().toLowerCase())){
				return true;
			}
		}
		return false;	
	}
	public Boolean isLocalChannel(){	
		return this.isLocalChannel;
	}
	public String getLocalChannel(){
		return this.localChannel;
	}
	
   public String getLocalExten(String channelStr){
		  int rPos=channelStr.indexOf('-');
	  int lPos=channelStr.indexOf('/');
	  if(rPos>lPos){
		  return channelStr.substring(lPos+1, rPos);
	  }
	  return "";
   }
}
