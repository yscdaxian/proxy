package pbx;

import java.util.ArrayList;

import client.SendEventToClients;
 
public abstract class Pbx{	
	
	public abstract void setSender(SendEventToClients sendEventToClients);
	public abstract SendEventToClients getSender();
	public abstract void setPbxAddr(String pbxAddr);
	public abstract	void setPbxPort(int pbxPort);
	public abstract	void setPbxUser(String  user);
	public abstract	void setPbxPwd(String  pwd);
	public abstract		Boolean Init();
	public abstract  	Boolean pbxAuthority();
	public abstract	void run();
	
	public abstract Boolean loginIn(String agent);
	public abstract Boolean loginOut(String agent);
	public abstract Boolean makeBusy(String agent, Boolean isBusy);
	public abstract Boolean transfer(String oldExten, String newExten,int mod);
	
	public abstract ArrayList<PbxLiveCall> getLiveCalls();
	public abstract ArrayList<PbxAgent>  getAllAgents();
}

