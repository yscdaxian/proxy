/*    */ package pbx;
/*    */ 
/*    */ public class PbxLiveCall
/*    */ {
/*    */   private String callId;
/*    */   private String callType;
/*    */   private String callStatus;
/*    */   private String caller;
/*    */   private String called;
/*    */   private String callStartTime;
/*    */   private String linkStartTime;
/*    */   private String queueStartTime;
/*    */   private String hangupStartTime;
/*    */   private Boolean isConnected;
/*    */ 
/*    */   public PbxLiveCall()
/*    */   {
/* 15 */     this.callId = "";
/* 16 */     this.callType = "";
/* 17 */     this.callStatus = "";
/* 18 */     this.caller = "";
/* 19 */     this.called = "";
/* 20 */     this.callStartTime = "";
/* 21 */     this.linkStartTime = "";
/* 22 */     this.queueStartTime = "";
/* 23 */     this.hangupStartTime = "";
/*    */   }
/*    */   public String getCallId() {
/* 26 */     return this.callId;
/*    */   }
/*    */   public void setCallId(String callId) {
/* 29 */     this.callId = callId;
/*    */   }
/*    */   public String getCallType() {
/* 32 */     return this.callType;
/*    */   }
/*    */   public void setCallType(String callType) {
/* 35 */     this.callType = callType;
/*    */   }
/*    */   public String getCallStatus() {
/* 38 */     return this.callStatus;
/*    */   }
/*    */   public void setCallStatus(String callStatus) {
/* 41 */     this.callStatus = callStatus;
/*    */   }
/*    */ 
/*    */   public String getCaller() {
/* 45 */     return this.caller;
/*    */   }
/*    */   public void setCaller(String caller) {
/* 48 */     this.caller = caller;
/*    */   }
/*    */   public String getCalled() {
/* 51 */     return this.called;
/*    */   }
/*    */   public void setCalled(String called) {
/* 54 */     this.called = called;
/*    */   }
/*    */   public String getCallStartTime() {
/* 57 */     return this.callStartTime;
/*    */   }
/*    */   public void setCallStartTime(String callStartTime) {
/* 60 */     this.callStartTime = callStartTime;
/*    */   }
/*    */   public String getLinkStartTime() {
/* 63 */     return this.linkStartTime;
/*    */   }
/*    */   public void setLinkStartTime(String linkStartTime) {
/* 66 */     this.linkStartTime = linkStartTime;
/*    */   }
/*    */   public String getQueueStartTime() {
/* 69 */     return this.queueStartTime;
/*    */   }
/*    */   public void setQueueStartTime(String queueStartTime) {
/* 72 */     this.queueStartTime = queueStartTime;
/*    */   }
/*    */   public String getHangupStartTime() {
/* 75 */     return this.hangupStartTime;
/*    */   }
/*    */   public void setHangupStartTime(String hangupStartTime) {
/* 78 */     this.hangupStartTime = hangupStartTime;
/*    */   }
/*    */   public Boolean isConnected() {
/* 81 */     return this.isConnected;
/*    */   }
/*    */   public void setConnected(Boolean isConnected) {
/* 84 */     this.isConnected = isConnected;
/*    */   }
/*    */ }

