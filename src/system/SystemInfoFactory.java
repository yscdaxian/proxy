/*    */ package system;
/*    */ 
/*    */ public class SystemInfoFactory
/*    */ {
/*  4 */   private SystemEnum se = SystemEnum.UnKnow;
/*    */ 
/*    */   public static ISystemInfo CreateSystemInfo()
/*    */   {
/* 13 */     SystemEnum se = getOsType();
/* 14 */     ISystemInfo isi = null;
/* 15 */     switch (se)
/*    */     {
/*    */     case Window:
/* 18 */       isi = new WindowSystemInfo();
/* 19 */       break;
/*    */     case Linux:
/* 21 */       isi = new LinuxSystemInfo();
/* 22 */       break;
/*    */     case Mac:
/* 24 */       break;
/*    */     case UnKnow:
/*    */     }
/*    */ 
/* 28 */     return isi;
/*    */   }
/*    */ 
/*    */   public static SystemEnum getOsType()
/*    */   {
/* 35 */     String osname = System.getProperty("os.name");
/*    */ 
/* 37 */     if (osname.toLowerCase().contains("linux"))
/* 38 */       return SystemEnum.Linux;
/* 39 */     if (osname.toLowerCase().contains("windows")) {
/* 40 */       return SystemEnum.Window;
/*    */     }
/* 42 */     return SystemEnum.UnKnow;
/*    */   }
/*    */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.system.SystemInfoFactory
 * JD-Core Version:    0.6.0
 */