package event;

import java.util.ArrayList;
import java.util.Map;


import org.json.simple.JSONValue;

public class ProxyEvent {
	protected String msg;
	public String getMsg(){
		return this.msg;
	}
	
	public void bindMsg(Map msg){
		try{
			this.msg=JSONValue.toJSONString(msg);
		}catch(Exception ex){}
	}
}
