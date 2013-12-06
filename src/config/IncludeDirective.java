/*    */ package config;
/*    */ 
/*    */ public class IncludeDirective extends ConfigDirective
/*    */ {
/*    */   private final String includeFile;
/*    */ 
/*    */   public IncludeDirective(String includeFile)
/*    */   {
/* 15 */     this.includeFile = includeFile;
/*    */   }
/*    */ 
/*    */   public IncludeDirective(String filename, int lineno, String includeFile)
/*    */   {
/* 20 */     super(filename, lineno);
/* 21 */     this.includeFile = includeFile;
/*    */   }
/*    */ 
/*    */   public String getIncludeFile()
/*    */   {
/* 26 */     return this.includeFile;
/*    */   }
/*    */ 
/*    */   protected StringBuilder rawFormat(StringBuilder sb)
/*    */   {
/* 32 */     sb.append("#include \"").append(this.includeFile).append("\"");
/* 33 */     return sb;
/*    */   }
/*    */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.config.IncludeDirective
 * JD-Core Version:    0.6.0
 */