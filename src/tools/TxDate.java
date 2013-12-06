/*    */ package tools;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class TxDate
/*    */ {
/* 10 */   public static String LastError = "";
/*    */ 
/*    */   public static String TxNow(String dateformat)
/*    */   {
/* 18 */     DateFormat formatter = new SimpleDateFormat(dateformat);
/* 19 */     String datetime = formatter.format(new Date());
/* 20 */     return datetime;
/*    */   }
/*    */ 
/*    */   public static String TxNow() {
/* 24 */     return TxNow("yyyy-MM-dd HH:mm:ss");
/*    */   }
/*    */ 
/*    */   public static String GetTelnumPrefix(String telnum)
/*    */   {
/* 29 */     String p = "";
/* 30 */     if (telnum != null)
/*    */     {
/* 32 */       for (int i = 0; i < telnum.length(); i++)
/*    */       {
/* 34 */         char temp = telnum.charAt(i);
/* 35 */         if (temp != '0')
/*    */           break;
/* 37 */         p = p + "0";
/*    */       }
/*    */     }
/* 40 */     return p;
/*    */   }
/*    */ 
/*    */   public static long GetTimes(String stime, String etime)
/*    */   {
/* 45 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*    */ 
/* 47 */     long between = 0L;
/*    */     try {
/* 49 */       Date begin = sdf.parse(stime);
/* 50 */       Date end = sdf.parse(etime);
/* 51 */       between = (end.getTime() - begin.getTime()) / 1000L;
/*    */     }
/*    */     catch (Exception e) {
/* 54 */       return 0L;
/*    */     }
/*    */     Date begin;
/* 57 */     return between;
/*    */   }
/*    */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.unit.tools.TxDate
 * JD-Core Version:    0.6.0
 */