/*     */ package config;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ public class ConfigFileImpl
/*     */   implements ConfigFile
/*     */ {
/*     */   private final String filename;
/*     */   protected final Map<String, Category> categories;
/*     */ 
/*     */   public ConfigFileImpl(String filename, Map<String, Category> categories)
/*     */   {
/*  43 */     this.filename = filename;
/*  44 */     this.categories = categories;
/*     */   }
/*     */ 
/*     */   public String getFilename()
/*     */   {
/*  49 */     return this.filename;
/*     */   }
/*     */ 
/*     */   public Map<String, List<String>> getCategories()
/*     */   {
/*  56 */     Map c = new TreeMap();
/*     */ 
/*  58 */     synchronized (this.categories)
/*     */     {
/*  60 */       for (Category category : this.categories.values())
/*     */       {
/*  64 */         List lines = new ArrayList();
/*  65 */         for (ConfigElement element : category.getElements())
/*     */         {
/*  67 */           if (!(element instanceof ConfigVariable))
/*     */             continue;
/*  69 */           ConfigVariable cv = (ConfigVariable)element;
/*  70 */           lines.add(cv.getName() + "=" + cv.getValue());
/*     */         }
/*     */ 
/*  73 */         c.put(category.getName(), lines);
/*     */       }
/*     */     }
/*     */ 
/*  77 */     return c;
/*     */   }
/*     */ 
/*     */   public String getValue(String categoryName, String key)
/*     */   {
/*  84 */     Category category = getCategory(categoryName);
/*  85 */     if (category == null)
/*     */     {
/*  87 */       return null;
/*     */     }
/*     */ 
/*  90 */     for (ConfigElement element : category.getElements())
/*     */     {
/*  92 */       if (!(element instanceof ConfigVariable))
/*     */         continue;
/*  94 */       ConfigVariable cv = (ConfigVariable)element;
/*     */ 
/*  96 */       if (cv.getName().equals(key))
/*     */       {
/*  98 */         return cv.getValue();
/*     */       }
/*     */     }
/*     */ 
/* 102 */     return null;
/*     */   }
/*     */ 
/*     */   public List<String> getValues(String categoryName, String key)
/*     */   {
/* 110 */     Category category = getCategory(categoryName);
/* 111 */     List result = new ArrayList();
/* 112 */     if (category == null)
/*     */     {
/* 114 */       return result;
/*     */     }
/*     */ 
/* 117 */     for (ConfigElement element : category.getElements())
/*     */     {
/* 119 */       if (!(element instanceof ConfigVariable))
/*     */         continue;
/* 121 */       ConfigVariable cv = (ConfigVariable)element;
/*     */ 
/* 123 */       if (!cv.getName().equals(key))
/*     */         continue;
/* 125 */       result.add(cv.getValue());
/*     */     }
/*     */ 
/* 129 */     return result;
/*     */   }
/*     */ 
/*     */   protected Category getCategory(String name)
/*     */   {
/* 134 */     synchronized (this.categories)
/*     */     {
/* 136 */       return (Category)this.categories.get(name);
/*     */     }
/*     */   }
/*     */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.config.ConfigFileImpl
 * JD-Core Version:    0.6.0
 */