/*    */ package config;
/*    */ 
/*    */ public abstract class ConfigElement
/*    */ {
/*    */   private String filename;
/*    */   private int lineno;
/*    */   private String preComment;
/*    */   private String samelineComment;
/*    */ 
/*    */   protected ConfigElement()
/*    */   {
/*    */   }
/*    */ 
/*    */   protected ConfigElement(String filename, int lineno)
/*    */   {
/* 23 */     this.filename = filename;
/* 24 */     this.lineno = lineno;
/*    */   }
/*    */ 
/*    */   public String getFileName()
/*    */   {
/* 29 */     return this.filename;
/*    */   }
/*    */ 
/*    */   public void setFileName(String filename)
/*    */   {
/* 34 */     this.filename = filename;
/*    */   }
/*    */ 
/*    */   public int getLineNumber()
/*    */   {
/* 39 */     return this.lineno;
/*    */   }
/*    */ 
/*    */   void setLineNumber(int lineno)
/*    */   {
/* 44 */     this.lineno = lineno;
/*    */   }
/*    */ 
/*    */   public String getPreComment()
/*    */   {
/* 49 */     return this.preComment;
/*    */   }
/*    */ 
/*    */   public void setPreComment(String preComment)
/*    */   {
/* 54 */     this.preComment = preComment;
/*    */   }
/*    */ 
/*    */   public String getComment()
/*    */   {
/* 59 */     return this.samelineComment;
/*    */   }
/*    */ 
/*    */   public void setComment(String samelineComment)
/*    */   {
/* 64 */     this.samelineComment = samelineComment;
/*    */   }
/*    */ 
/*    */   protected StringBuilder format(StringBuilder sb)
/*    */   {
/* 69 */     if ((this.preComment != null) && (this.preComment.length() != 0))
/*    */     {
/* 71 */       sb.append(this.preComment);
/*    */     }
/*    */ 
/* 74 */     rawFormat(sb);
/*    */ 
/* 76 */     if ((this.samelineComment != null) && (this.samelineComment.length() != 0))
/*    */     {
/* 78 */       sb.append(" ; ").append(this.samelineComment);
/*    */     }
/*    */ 
/* 81 */     return sb;
/*    */   }
/*    */ 
/*    */   protected abstract StringBuilder rawFormat(StringBuilder paramStringBuilder);
/*    */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.config.ConfigElement
 * JD-Core Version:    0.6.0
 */