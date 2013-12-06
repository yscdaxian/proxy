/*     */ package config;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.nio.CharBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ public class ConfigFileReader
/*     */ {
/*     */   private static final int MAX_LINE_LENGTH = 8192;
/*  35 */   private static char COMMENT_META = ';';
/*  36 */   private static char COMMENT_TAG = '-';
/*     */ 
/*  41 */   private static final Set<Class> WARNING_CLASSES = new HashSet();
/*     */   private StringBuilder commentBlock;
/*     */   protected final Map<String, Category> categories;
/*     */   private final List<ConfigParseException> warnings;
/*     */   protected Category currentCategory;
/*  52 */   private int currentCommentLevel = 0;
/*     */ 
/*     */   static
/*     */   {
/*  42 */     WARNING_CLASSES.add(MissingEqualSignException.class);
/*  43 */     WARNING_CLASSES.add(UnknownDirectiveException.class);
/*  44 */     WARNING_CLASSES.add(MissingDirectiveParameterException.class);
/*     */   }
/*     */ 
/*     */   public ConfigFileReader()
/*     */   {
/*  56 */     this.commentBlock = new StringBuilder();
/*  57 */     this.categories = new LinkedHashMap();
/*  58 */     this.warnings = new ArrayList();
/*     */   }
/*     */ 
/*     */   public ConfigFile readFile(String configfile)
/*     */     throws IOException, ConfigParseException
/*     */   {
/*  66 */     BufferedReader reader = new BufferedReader(new FileReader(configfile));
/*     */     try
/*     */     {
/*  69 */       readFile(configfile, reader);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  75 */         reader.close();
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  83 */     ConfigFile result = new ConfigFileImpl(configfile, new TreeMap(this.categories));
/*     */ 
/*  86 */     return result;
/*     */   }
/*     */ 
/*     */   void reset()
/*     */   {
/*  91 */     this.commentBlock = new StringBuilder();
/*  92 */     this.categories.clear();
/*  93 */     this.warnings.clear();
/*  94 */     this.currentCategory = null;
/*  95 */     this.currentCommentLevel = 0;
/*     */   }
/*     */ 
/*     */   Collection<Category> getCategories()
/*     */   {
/* 100 */     return this.categories.values();
/*     */   }
/*     */ 
/*     */   public Collection<ConfigParseException> getWarnings()
/*     */   {
/* 105 */     return new ArrayList(this.warnings);
/*     */   }
/*     */ 
/*     */   void readFile(String configfile, BufferedReader reader)
/*     */     throws IOException, ConfigParseException
/*     */   {
/* 111 */     int lineno = 0;
/* 112 */     CharBuffer buffer = CharBuffer.allocate(8192);
/*     */ 
/* 114 */     reset();
/*     */     String line;
/* 115 */     while ((line = reader.readLine()) != null)
/*     */     {
				
/*     */       //ysc-String line;
/* 117 */       lineno++;
/* 118 */       buffer.clear();
/* 119 */       buffer.put(line);
/* 120 */       buffer.put("\n");
/* 121 */       buffer.flip();
/*     */ 
/* 123 */       processLine(configfile, lineno, buffer);
/*     */     }
/*     */   }
/*     */ 
/*     */   ConfigElement processLine(String configfile, int lineno, CharBuffer buffer)
/*     */     throws ConfigParseException
/*     */   {
/* 133 */     StringBuilder processLineBuilder = new StringBuilder(8192);
/* 134 */     StringBuilder lineCommentBuilder = new StringBuilder(8192);
/* 135 */     buffer.mark();
/*     */ 
/* 137 */     while (buffer.hasRemaining())
/*     */     {
/* 141 */       int position = buffer.position();
/* 142 */       char c = buffer.get();
/*     */ 
/* 145 */       if (c == COMMENT_META)
/*     */       {
/* 147 */         if ((position < 1) || (buffer.get(position - 1) != '\\'))
/*     */         {
/* 151 */           if ((buffer.remaining() >= 3) && 
/* 152 */             (buffer.get(position + 1) == COMMENT_TAG) && 
/* 153 */             (buffer.get(position + 2) == COMMENT_TAG) && 
/* 154 */             (buffer.get(position + 3) != COMMENT_TAG))
/*     */           {
/* 158 */             this.currentCommentLevel += 1;
/*     */ 
/* 161 */             if (!inComment())
/*     */             {
/* 163 */               this.commentBlock.append(";--");
/* 164 */               buffer.position(position + 3);
/* 165 */               buffer.mark();
/* 166 */               continue;
/*     */             }
/*     */           }
/* 169 */           else if ((inComment()) && 
/* 170 */             (position >= 2) && 
/* 171 */             (buffer.get(position - 1) == COMMENT_TAG) && 
/* 172 */             (buffer.get(position - 2) == COMMENT_TAG))
/*     */           {
/* 176 */             this.currentCommentLevel -= 1;
/*     */ 
/* 178 */             if (!inComment())
/*     */             {
/* 180 */               buffer.reset();
/*     */ 
/* 190 */               this.commentBlock.append(c);
/*     */ 
/* 193 */               buffer.position(position + 1);
/* 194 */               buffer.compact();
/* 195 */               buffer.flip();
/*     */ 
/* 198 */               continue;
/*     */             }
/*     */ 
/*     */           }
/* 203 */           else if (!inComment())
/*     */           {
/* 207 */             while (buffer.hasRemaining())
/*     */             {
/* 209 */               lineCommentBuilder.append(buffer.get());
/*     */             }
/* 211 */             break;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 221 */       if (inComment())
/*     */       {
/* 223 */         this.commentBlock.append(c);
/*     */       }
/*     */       else
/*     */       {
/* 228 */         processLineBuilder.append(c);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 236 */     String processLineString = processLineBuilder.toString().trim();
/* 237 */     String lineCommentString = lineCommentBuilder.toString().trim();
/*     */ 
/* 240 */     if (processLineString.length() == 0)
/*     */     {
/* 242 */       if (lineCommentString.length() != 0)
/*     */       {
/* 244 */         this.commentBlock.append(";");
/* 245 */         this.commentBlock.append(lineCommentString);
/*     */       }
/* 247 */       if (!inComment())
/*     */       {
/* 249 */         this.commentBlock.append("\n");
/*     */       }
/* 251 */       return null;
/*     */     }
/*     */ 	 ConfigElement configElement;
/*     */     try
/*     */     {
/* 256 */       configElement = processTextLine(configfile, lineno, processLineString);
/*     */     }
/*     */     catch (ConfigParseException e)
/*     */     {
/*     */       
/* 261 */       if (WARNING_CLASSES.contains(e.getClass()))
/*     */       {
/* 263 */         this.warnings.add(e);
/* 264 */         return null;
/*     */       }
/*     */ 
/* 268 */       throw e;
/*     */     }
/*     */    
/* 272 */     if (lineCommentString.length() != 0)
/*     */     {
/* 274 */       configElement.setComment(lineCommentString);
/*     */     }
/*     */ 
/* 277 */     if (this.commentBlock.length() != 0)
/*     */     {
/* 279 */       configElement.setPreComment(this.commentBlock.toString());
/* 280 */       this.commentBlock.delete(0, this.commentBlock.length());
/*     */     }
/*     */ 
/* 283 */     return configElement;
/*     */   }
/*     */ 
/*     */   boolean inComment()
/*     */   {
/* 288 */     return this.currentCommentLevel != 0;
/*     */   }
/*     */ 
/*     */   protected ConfigElement processTextLine(String configfile, int lineno, String line)
/*     */     throws ConfigParseException
/*     */   {
/*     */     ConfigElement configElement;
/*     */   
/* 295 */     if (line.charAt(0) == '[')
/*     */     {
/* 297 */       configElement = parseCategoryHeader(configfile, lineno, line);
/*     */     }
/*     */     else
/*     */     {
/*     */       
/* 299 */       if (line.charAt(0) == '#')
/*     */       {
/* 301 */         configElement = parseDirective(configfile, lineno, line);
/*     */       }
/*     */       else
/*     */       {
/* 305 */         if (this.currentCategory == null)
/*     */         {
/* 307 */           throw new ConfigParseException(configfile, lineno, 
/* 308 */             "parse error: No category context for line %d of %s", new Object[] { Integer.valueOf(lineno), configfile });
/*     */         }
/*     */ 
/* 311 */         configElement = parseVariable(configfile, lineno, line);
/* 312 */         this.currentCategory.addElement(configElement);
/*     */       }
/*     */     }
/* 315 */     return configElement;
/*     */   }
/*     */ 
/*     */   protected Category parseCategoryHeader(String configfile, int lineno, String line)
/*     */     throws ConfigParseException
/*     */   {
/* 333 */     int nameEndPos = line.indexOf(']');
/* 334 */     if (nameEndPos == -1)
/*     */     {
/* 336 */       throw new ConfigParseException(configfile, lineno, 
/* 337 */         "parse error: no closing ']', line %d of %s", new Object[] { Integer.valueOf(lineno), configfile });
/*     */     }
/* 339 */     String name = line.substring(1, nameEndPos);
/* 340 */     Category category = new Category(configfile, lineno, name);
/*     */ 
/* 343 */     if ((line.length() > nameEndPos + 1) && (line.charAt(nameEndPos + 1) == '('))
/*     */     {
/* 349 */       String optionsString = line.substring(nameEndPos + 1);
/* 350 */       int optionsEndPos = optionsString.indexOf(')');
/* 351 */       if (optionsEndPos == -1)
/*     */       {
/* 353 */         throw new ConfigParseException(configfile, lineno, 
/* 354 */           "parse error: no closing ')', line %d of %s", new Object[] { Integer.valueOf(lineno), configfile });
/*     */       }
/*     */ 
/* 357 */       String[] options = optionsString.substring(1, optionsEndPos).split(",");
/* 358 */       for (String cur : options)
/*     */       {
/* 360 */         if ("!".equals(cur))
/*     */         {
/* 362 */           category.markAsTemplate();
/*     */         } else {
/* 364 */           if ("+".equals(cur))
/*     */           {
/* 368 */             Category categoryToAddTo = (Category)this.categories.get(name);
/* 369 */             if (categoryToAddTo != null)
/*     */               continue;
/* 371 */             throw new ConfigParseException(configfile, lineno, 
/* 372 */               "Category addition requested, but category '%s' does not exist, line %d of %s", new Object[] { 
/* 373 */               name, Integer.valueOf(lineno), configfile });
/*     */           }
/*     */ 
/* 383 */           Category baseCategory = (Category)this.categories.get(cur);
/* 384 */           if (baseCategory == null)
/*     */           {
/* 386 */             throw new ConfigParseException(configfile, lineno, 
/* 387 */               "Inheritance requested, but category '%s' does not exist, line %d of %s", new Object[] { 
/* 388 */               cur, Integer.valueOf(lineno), configfile });
/*     */           }
/* 390 */           inheritCategory(category, baseCategory);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 395 */     appendCategory(category);
/* 396 */     return category;
/*     */   }
/*     */ 
/*     */   ConfigDirective parseDirective(String configfile, int lineno, String line)
/*     */     throws ConfigParseException
/*     */   {
/* 404 */     String name = null;
/*     */ 
/* 407 */     StringBuilder nameBuilder = new StringBuilder();
/* 408 */     StringBuilder paramBuilder = new StringBuilder();
/* 409 */     for (int i = 1; i < line.length(); i++)
/*     */     {
/* 413 */       char c = line.charAt(i);
/* 414 */       if (name == null)
/*     */       {
/* 416 */         nameBuilder.append(c);
/* 417 */         if ((!Character.isWhitespace(c)) && (i + 1 != line.length()))
/*     */           continue;
/* 419 */         name = nameBuilder.toString().trim();
/*     */       }
/*     */       else
/*     */       {
/* 424 */         paramBuilder.append(c);
/*     */       }
/*     */     }
/*     */ 
/* 428 */     String param = paramBuilder.toString().trim();
/*     */ 
/* 430 */     if ((param.length() != 0) && (
/* 431 */       (param.charAt(0) == '"') || (param.charAt(0) == '<') || (param.charAt(0) == '>')))
/*     */     {
/* 433 */       param = param.substring(1);
/*     */     }
/*     */ 
/* 436 */     int endPos = param.length() - 1;
/* 437 */     if ((param.length() != 0) && (
/* 438 */       (param.charAt(endPos) == '"') || (param.charAt(endPos) == '<') || (param.charAt(endPos) == '>')))
/*     */     {
/* 440 */       param = param.substring(0, endPos);
/*     */     }
/*     */     ConfigDirective directive;
/* 443 */     if ("exec".equalsIgnoreCase(name))
/*     */     {
/* 445 */       directive = new ExecDirective(configfile, lineno, param);
/*     */     }
/*     */     else
/*     */     {
/*     */       
/* 447 */       if ("include".equalsIgnoreCase(name))
/*     */       {
/* 449 */         directive = new IncludeDirective(configfile, lineno, param);
/*     */       }
/*     */       else
/*     */       {
/* 453 */         throw new UnknownDirectiveException(configfile, lineno, 
/* 454 */           "Unknown directive '%s' at line %d of %s", new Object[] { name, Integer.valueOf(lineno), configfile });
/*     */       }
/*     */     }
/*     */    
/* 457 */     if (param.length() == 0)
/*     */     {
/* 459 */       throw new MissingDirectiveParameterException(configfile, lineno, 
/* 460 */         "Directive '#%s' needs an argument (%s) at line %d of %s", new Object[] { 
/* 461 */         name.toLowerCase(Locale.US), 
/* 462 */         "include".equalsIgnoreCase(name) ? "filename" : "/path/to/executable", 
/* 463 */         Integer.valueOf(lineno), 
/* 464 */         configfile });
/*     */     }
/*     */ 
/* 467 */     return directive;
/*     */   }
/*     */ 
/*     */   protected ConfigVariable parseVariable(String configfile, int lineno, String line)
/*     */     throws ConfigParseException
/*     */   {
/* 476 */     int pos = line.indexOf('=');
/* 477 */     if (pos == -1)
/*     */     {
/* 479 */       throw new MissingEqualSignException(configfile, lineno, 
/* 480 */         "No '=' (equal sign) in line %d of %s", new Object[] { Integer.valueOf(lineno), configfile });
/*     */     }
/*     */ 
/* 483 */     String name = line.substring(0, pos).trim();
/*     */ 
/* 486 */     if ((line.length() > pos + 1) && (line.charAt(pos + 1) == '>'))
/*     */     {
/* 488 */       pos++;
/*     */     }
/*     */ 
/* 491 */     String value = line.length() > pos + 1 ? line.substring(pos + 1).trim() : "";
/* 492 */     return new ConfigVariable(configfile, lineno, name, value);
/*     */   }
/*     */ 
/*     */   void inheritCategory(Category category, Category baseCategory)
/*     */   {
/* 497 */     category.addBaseCategory(baseCategory);
/*     */   }
/*     */ 
/*     */   void appendCategory(Category category)
/*     */   {
/* 502 */     this.categories.put(category.getName(), category);
/* 503 */     this.currentCategory = category;
/*     */   }
/*     */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.config.ConfigFileReader
 * JD-Core Version:    0.6.0
 */