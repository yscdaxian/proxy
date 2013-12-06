 package dbhelper;
 
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;

import log.Log;


public class DbPool
{
  static DbPool dbPool = new DbPool();
  private ComboPooledDataSource ds;
  private String 	dbHost;
  private int 		dbPort;
  private String 	dbName;
  private String 	dbUser;
  private String 	dbPwd;
  public DbPool()
  {
   
 }

 public Connection getConnect() {
	try {
		     return this.ds.getConnection();
    } catch (Exception ex) {
      return null;
    }
 }
public void setDbHost(String host){
	this.dbHost=host;
}
public void setDbPort(int port){
	this.dbPort=port;
}
public void setDbName(String dbName){
	this.dbName=dbName;
}
public void setDbUser(String user){
	this.dbUser=user;
}
public void setDbPwd(String pwd){
	this.dbPwd=pwd;
}

public Boolean init(){
	Boolean ret=false;
	 try
     {
    	this.ds = new ComboPooledDataSource();
    	this.ds.setDriverClass("com.mysql.jdbc.Driver");
        this.ds.setJdbcUrl("jdbc:mysql://" + this.dbHost + "/" + this.dbName + "?" + 
         "&user=" + this.dbUser + 
         "&password=" + this.dbPwd);
         this.ds.setInitialPoolSize(3); 
         this.ds.setMaxPoolSize(10); 
         this.ds.setAcquireIncrement(1);
         this.ds.setIdleConnectionTestPeriod(60);
         this.ds.setMaxIdleTime(25000); 
         this.ds.setAutoCommitOnClose(true);
         this.ds.setTestConnectionOnCheckout(true);
         this.ds.setTestConnectionOnCheckin(true);
         ret=true;
     }catch (Exception ex){
    	 Log.getInstance().error("connect db exception:"+ex.toString());
    	 return ret;
     }
	return  ret;
}
public static DbPool getInstance(){
    return dbPool;
  }
}
