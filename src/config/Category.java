/*     */ package config;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Category extends ConfigElement
/*     */ {
/*     */   private String name;
/*     */   private boolean template;
/*  17 */   private final List<Category> baseCategories = new ArrayList();
/*  18 */   private final List<ConfigElement> elements = new ArrayList();
/*     */ 
/*     */   public Category(String name)
/*     */   {
/*  27 */     if (name == null)
/*     */     {
/*  29 */       throw new IllegalArgumentException("Name must not be null");
/*     */     }
/*  31 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Category(String filename, int lineno, String name)
/*     */   {
/*  36 */     super(filename, lineno);
/*  37 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  47 */     return this.name;
/*     */   }
/*     */ 
/*     */   public boolean isTemplate()
/*     */   {
/*  57 */     return this.template;
/*     */   }
/*     */ 
/*     */   public void markAsTemplate()
/*     */   {
/*  62 */     this.template = true;
/*     */   }
/*     */ 
/*     */   public List<Category> getBaseCategories()
/*     */   {
/*  72 */     return this.baseCategories;
/*     */   }
/*     */ 
/*     */   public void addBaseCategory(Category baseCategory)
/*     */   {
/*  77 */     this.baseCategories.add(baseCategory);
/*     */   }
/*     */ 
/*     */   public List<ConfigElement> getElements()
/*     */   {
/*  82 */     return this.elements;
/*     */   }
/*     */ 
/*     */   public void addElement(ConfigElement element)
/*     */   {
/*  87 */     if ((element instanceof Category))
/*     */     {
/*  89 */       throw new IllegalArgumentException("Nested categories are not allowed");
/*     */     }
/*     */ 
/*  92 */     this.elements.add(element);
/*     */   }
/*     */ 
/*     */   public String format()
/*     */   {
/*  97 */     StringBuilder sb = new StringBuilder();
/*     */ 
/*  99 */     format(sb);
/* 100 */     for (ConfigElement e : this.elements)
/*     */     {
/* 102 */       sb.append("\n");
/* 103 */       e.format(sb);
/*     */     }
/* 105 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   protected StringBuilder rawFormat(StringBuilder sb)
/*     */   {
/* 111 */     sb.append("[").append(this.name).append("]");
/* 112 */     if ((isTemplate()) || (!getBaseCategories().isEmpty()))
/*     */     {
/* 114 */       sb.append("(");
/* 115 */       if (isTemplate())
/*     */       {
/* 117 */         sb.append("!");
/* 118 */         if (!getBaseCategories().isEmpty())
/*     */         {
/* 120 */           sb.append(",");
/*     */         }
/*     */       }
/* 123 */       Iterator inheritsFromIterator = getBaseCategories().iterator();
/* 124 */       while (inheritsFromIterator.hasNext())
/*     */       {
/* 126 */         sb.append(((Category)inheritsFromIterator.next()).getName());
/* 127 */         if (!inheritsFromIterator.hasNext())
/*     */           continue;
/* 129 */         sb.append(",");
/*     */       }
/*     */ 
/* 132 */       sb.append(")");
/*     */     }
/*     */ 
/* 135 */     return sb;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 141 */     return this.name;
/*     */   }
/*     */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.config.Category
 * JD-Core Version:    0.6.0
 */