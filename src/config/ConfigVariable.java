/*    */ package config;
/*    */ 
/*    */ public class ConfigVariable extends ConfigElement
/*    */ {
/*    */   private String name;
/*    */   private String value;
/*    */ 
/*    */   public ConfigVariable(String name, String value)
/*    */   {
/* 15 */     setName(name);
/* 16 */     setValue(value);
/*    */   }
/*    */ 
/*    */   public ConfigVariable(String filename, int lineno, String name, String value)
/*    */   {
/* 21 */     super(filename, lineno);
/* 22 */     setName(name);
/* 23 */     setValue(value);
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 28 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 33 */     if (name == null)
/*    */     {
/* 35 */       throw new IllegalArgumentException("Variable name must not be null");
/*    */     }
/* 37 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 42 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value)
/*    */   {
/* 47 */     this.value = value;
/*    */   }
/*    */ 
/*    */   protected StringBuilder rawFormat(StringBuilder sb)
/*    */   {
/* 53 */     sb.append(this.name).append(" = ");
/* 54 */     if (this.value != null)
/*    */     {
/* 56 */       sb.append(this.value);
/*    */     }
/* 58 */     return sb;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 64 */     return this.name + "=" + this.value;
/*    */   }
/*    */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.config.ConfigVariable
 * JD-Core Version:    0.6.0
 */