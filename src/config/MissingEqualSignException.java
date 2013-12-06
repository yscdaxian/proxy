/*    */ package config;
/*    */ 
/*    */ public class MissingEqualSignException extends ConfigParseException
/*    */ {
/*    */   private static final long serialVersionUID = 2694490330074765342L;
/*    */ 
/*    */   public MissingEqualSignException(String filename, int lineno, String format, Object[] params)
/*    */   {
/* 14 */     super(filename, lineno, format, params);
/*    */   }
/*    */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.config.MissingEqualSignException
 * JD-Core Version:    0.6.0
 */