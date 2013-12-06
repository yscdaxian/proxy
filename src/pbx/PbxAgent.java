/*    */ package pbx;
/*    */ 
/*    */ public class PbxAgent
/*    */ {
/*    */   private String agentName;
/*    */   private Boolean paused;
/*    */   private Boolean isBusy;
/*    */   private Boolean isLogin;
/*    */   private String lastCall;
/*    */   private String extStauts;
/*    */   private String loginStartTime;
/*    */   private String agentStatusStartTime;
/*    */   private String extStatusStartTime;
/*    */ 
/*    */   public PbxAgent()
/*    */   {
/* 14 */     this.agentName = "";
/* 15 */     this.isLogin = Boolean.valueOf(false);
/* 16 */     this.paused = Boolean.valueOf(false);
/* 17 */     this.lastCall = "";
/* 18 */     this.extStauts = "";
/*    */   }
/*    */ 
/*    */   public PbxAgent(String agentName, Boolean isLogin, Boolean paused) {
/* 22 */     this.agentName = agentName;
/* 23 */     this.paused = paused;
/* 24 */     this.isLogin = isLogin;
/* 25 */     this.lastCall = "";
/* 26 */     this.extStauts = "";
/*    */   }
/*    */ 
/*    */   public Boolean getPaused() {
/* 30 */     return this.paused;
/*    */   }
/*    */ 
/*    */   public void setPaused(Boolean paused) {
/* 34 */     this.paused = paused;
/*    */   }
/*    */   public Boolean getLogin() {
/* 37 */     return this.isLogin;
/*    */   }
/*    */ 
/*    */   public void setLogin(Boolean isLogin) {
/* 41 */     this.isLogin = isLogin;
/*    */   }
/*    */   public String getLoginStartTime() {
/* 44 */     return this.loginStartTime;
/*    */   }
/*    */   public void setLoginStartTime(String stime) {
/* 47 */     this.loginStartTime = stime;
/*    */   }
/*    */   public String getAgentStatusStartTime() {
/* 50 */     return this.agentStatusStartTime;
/*    */   }
/*    */   public void setAgentStatusStartTime(String stime) {
/* 53 */     this.agentStatusStartTime = stime;
/*    */   }
/*    */ 
/*    */   public void setExtStatusStartTime(String stime) {
/* 57 */     this.extStatusStartTime = stime;
/*    */   }
/*    */   public String getExtStatusStartTime() {
/* 60 */     return this.extStatusStartTime;
/*    */   }
/*    */   public String getLastCall() {
/* 63 */     return this.lastCall;
/*    */   }
/*    */   public void setLastCall(String lastCall) {
/* 66 */     this.lastCall = lastCall;
/*    */   }
/*    */ 
/*    */   public String getExtStatus() {
/* 70 */     return this.extStauts;
/*    */   }
/*    */ 
/*    */   public void setExtStatus(String extStatus) {
/* 74 */     this.extStauts = extStatus;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 80 */     return this.agentName;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 84 */     this.agentName = name;
/*    */   }
/*    */ 
/*    */   public Boolean isBusy() {
/* 88 */     return this.isBusy;
/*    */   }
/*    */   public Boolean isLogin() {
/* 91 */     return this.isLogin;
/*    */   }
/*    */ }

/* Location:           C:\Users\Admin\Desktop\edu_crm\txctieev2_1.jar
 * Qualified Name:     teltion.pbx.PbxAgent
 * JD-Core Version:    0.5.4
 */