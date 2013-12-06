/*   */ package config;
/*   */ 
/*   */ public class UnknownDirectiveException extends ConfigParseException
/*   */ {
/*   */   private static final long serialVersionUID = 4356355066633810196L;
/*   */ 
/*   */   public UnknownDirectiveException(String filename, int lineno, String format, Object[] params)
/*   */   {
/* 9 */     super(filename, lineno, format, params);
/*   */   }
/*   */ }

