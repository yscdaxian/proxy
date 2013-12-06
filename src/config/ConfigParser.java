package config;

import org.asteriskjava.config.ConfigFile;
import org.asteriskjava.config.ConfigFileReader;

import system.OsSystemIoPath;
import tools.Convert;


public class ConfigParser {
	ConfigFileReader cfr = new ConfigFileReader();
	ConfigFile cf = null;
	public static Boolean system_ifdebug = Boolean.valueOf(false);
	private int 	pbxPort=5038;
	private String 	pbxAddr="127.0.0.1";
	private String 	pbxUser;
	private String  pbxPwd;
	
	private String 	dbHost="127.0.0.1";
	private String  dbName;
	private String  dbUser;
	private String  dbPwd;
	private int 	proxyPort=5138;
	
	public ConfigParser(){
		   
	}
	public Boolean  loadConfig(){
		 Boolean ret=false;
		 try {
		      cf = cfr.readFile(OsSystemIoPath.GetConfigPath() + "txctiee.conf");
		      system_ifdebug = Boolean.valueOf(cf.getValue("system", "debug").equalsIgnoreCase("1"));   
		      this.dbHost = cf.getValue("db", "host");
		      this.dbName=cf.getValue("db", "name");
		      this.dbUser=cf.getValue("db", "user");
		      this.dbPwd=cf.getValue("db", "pwd");
		      
		      this.pbxAddr = cf.getValue("pbx", "host");
		      this.pbxPort=	 Convert.ObjectConvertInt(cf.getValue("pbx", "port"), 5038);
		      this.pbxUser=cf.getValue("pbx", "user");
		      this.pbxPwd=cf.getValue("pbx", "pwd");
		      
		      this.proxyPort = Convert.ObjectConvertInt(cf.getValue("cti", "port"), 5138);
		      
		      ret=true;
		      return ret;
		 }catch(Exception ex){
			  return ret;
		 }
	}
	public int  getPbxPort(){	
		return this.pbxPort;
	}
	
	public String getPbxAddr(){
		return this.pbxAddr;
	}
	public String getPbxUser(){
		return this.pbxUser;
	}
	public String getPbxPwd(){
		return this.pbxPwd;
	}
	
	public String getDbHost(){
		return this.dbHost;
	}
	public String getDbUser(){
		return this.dbUser;
	}
	public String getDbPwd(){
		return this.dbPwd;
	}
	public String getDbName(){
		return this.dbName;
	}
	public int getProxyPort(){
		return this.proxyPort;
	}
	
}
