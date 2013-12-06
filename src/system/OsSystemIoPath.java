/*    */ package system;
/*    */ 
/*    */ public class OsSystemIoPath
/*    */ {
/*    */   public static String GetLogPath()
/*    */   {
/* 15 */     String path = "./";
/* 16 */     if (SystemInfoFactory.getOsType() == SystemEnum.Window)
/*    */     {
/* 18 */       path = "./";
/* 19 */     } else if (SystemInfoFactory.getOsType() == SystemEnum.Linux)
/*    */     {
/* 21 */       path = "/var/log/teltion/";
/*    */     }
/* 23 */     return path;
/*    */   }
/*    */ 
/*    */   public static String GetConfigPath()
/*    */   {
/* 32 */     String path = "./";
/* 33 */     if (SystemInfoFactory.getOsType() == SystemEnum.Window)
/*    */     {
/* 35 */       path = "./";
/* 36 */     } else if (SystemInfoFactory.getOsType() == SystemEnum.Linux)
/*    */     {
/* 38 */       path = "/etc/teltion/";
/*    */     }
/* 40 */     return path;
/*    */   }
/*    */ 
/*    */   public static String GetOutgoingPath()
/*    */   {
/* 49 */     String path = "./";
/* 50 */     if (SystemInfoFactory.getOsType() == SystemEnum.Window)
/*    */     {
/* 52 */       path = "./";
/* 53 */     } else if (SystemInfoFactory.getOsType() == SystemEnum.Linux)
/*    */     {
/* 55 */       path = "/var/spool/asterisk/outgoing/";
/*    */     }
/* 57 */     return path;
/*    */   }
/*    */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.system.OsSystemIoPath
 * JD-Core Version:    0.6.0
 */