package client;

/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.mina.core.session.IoSession;
/*    */ 
/*    */ public class SessionAgentMapPool
/*    */ {
/*  9 */   private static SessionAgentMapPool sessionAgentMapPool = new SessionAgentMapPool();
/* 10 */   private Map<IoSession, String> sessionAgentMap = new HashMap();
/*    */ 
/* 12 */   public static SessionAgentMapPool instance() { return sessionAgentMapPool; }
/*    */ 
/*    */   public void add(IoSession key, String agent) {
/* 15 */     this.sessionAgentMap.put(key, agent);
/*    */   }
/*    */ 
/*    */   public void remove(IoSession key) {
/* 19 */     this.sessionAgentMap.remove(key);
/*    */   }
/*    */ 
/*    */   public Boolean isHaveAgent(String agent) {
/* 23 */     return Boolean.valueOf(this.sessionAgentMap.containsValue(agent));
/*    */   }
/*    */ }

