/*    */ package config;
/*    */ 
/*    */ public class ConfigParseException extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 4346366210261425734L;
/*    */   private String filename;
/*    */   private int lineno;
/*    */ 
/*    */   public ConfigParseException(String filename, int lineno, String message)
/*    */   {
/* 14 */     super(message);
/* 15 */     this.filename = filename;
/* 16 */     this.lineno = lineno;
/*    */   }
/*    */ 
/*    */   public ConfigParseException(String filename, int lineno, String format, Object[] params)
/*    */   {
/* 21 */     super(String.format(format, params));
/* 22 */     this.filename = filename;
/* 23 */     this.lineno = lineno;
/*    */   }
/*    */ 
/*    */   public String getFileName()
/*    */   {
/* 28 */     return this.filename;
/*    */   }
/*    */ 
/*    */   public int getLineNumber()
/*    */   {
/* 33 */     return this.lineno;
/*    */   }
/*    */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.config.ConfigParseException
 * JD-Core Version:    0.6.0
 */