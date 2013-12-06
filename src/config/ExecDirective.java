/*    */ package config;
/*    */ 
/*    */ public class ExecDirective extends ConfigDirective
/*    */ {
/*    */   private final String execFile;
/*    */ 
/*    */   public ExecDirective(String execFile)
/*    */   {
/* 15 */     this.execFile = execFile;
/*    */   }
/*    */ 
/*    */   public ExecDirective(String filename, int lineno, String execFile)
/*    */   {
/* 20 */     super(filename, lineno);
/* 21 */     this.execFile = execFile;
/*    */   }
/*    */ 
/*    */   public String getExecFile()
/*    */   {
/* 26 */     return this.execFile;
/*    */   }
/*    */ 
/*    */   protected StringBuilder rawFormat(StringBuilder sb)
/*    */   {
/* 32 */     sb.append("#exec \"").append(this.execFile).append("\"");
/* 33 */     return sb;
/*    */   }
/*    */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.config.ExecDirective
 * JD-Core Version:    0.6.0
 */