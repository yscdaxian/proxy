/*     */ package tools;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class Convert
/*     */ {
/*   7 */   public static String LastError = "";
/*     */ 
/*     */   public static int StringConvertInt(String str, int defaultvalue)
/*     */   {
/*  16 */     int temp = defaultvalue;
/*     */     try {
/*  18 */       temp = Integer.parseInt(str);
/*     */     }
/*     */     catch (NumberFormatException e) {
/*  21 */       temp = defaultvalue;
/*     */     }
/*  23 */     return temp;
/*     */   }
/*     */ 
/*     */   public static int ObjectConvertInt(Object str, int defaultvalue) {
/*  27 */     int temp = defaultvalue;
/*  28 */     if (str == null) return temp; try
/*     */     {
/*  30 */       temp = Integer.parseInt(str.toString());
/*     */     }
/*     */     catch (NumberFormatException e) {
/*  33 */       temp = defaultvalue;
/*     */     }
/*  35 */     return temp;
/*     */   }
/*     */ 
/*     */   public static String ObjectConvertToFieldNameString(Object obj, String split) {
/*  39 */     if (obj != null)
/*     */     {
/*  41 */       Field[] objs = obj.getClass().getFields();
/*  42 */       StringBuilder sb = new StringBuilder();
/*     */ 
/*  44 */       for (int i = 0; i < objs.length; i++)
/*     */       {
/*  46 */         sb.append("`" + objs[i].getName() + "`");
/*  47 */         if (i >= objs.length - 1) continue; sb.append(split);
/*     */       }
/*  49 */       return sb.toString();
/*     */     }
/*  51 */     LastError = "obj is null";
/*  52 */     return null;
/*     */   }
/*     */ 
/*     */   public static String ObjectConvertToValueString(Object obj, String split)
/*     */   {
/*  68 */     if (obj != null)
/*     */     {
/*  70 */       Field[] objs = obj.getClass().getFields();
/*  71 */       StringBuilder sb = new StringBuilder();
/*  72 */       for (int i = 0; i < objs.length; i++)
/*     */       {
/*     */         try {
/*  75 */           if (IfStr(objs[i].getType().getSimpleName()).booleanValue())
/*     */           {
/*  77 */             sb.append("'");
/*  78 */             sb.append(objs[i].get(obj));
/*  79 */             sb.append("'");
/*     */           }
/*     */           else {
/*  82 */             sb.append(objs[i].get(obj));
/*     */           }
/*     */         } catch (IllegalArgumentException e) {
/*  85 */           LastError = "ArrayConvertToValueString:" + e.getMessage();
/*  86 */           return null;
/*     */         } catch (IllegalAccessException e) {
/*  88 */           LastError = "ArrayConvertToValueString:" + e.getMessage();
/*  89 */           return null;
/*     */         }
/*  91 */         if (i >= objs.length - 1) continue; sb.append(split);
/*     */       }
/*  93 */       return sb.toString();
/*     */     }
/*  95 */     LastError = "ArrayConvertToValueString:obj is null";
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */   public static String ObjectConvertToFieldValueString(Object obj, String s1, String s2, ArrayList fields)
/*     */   {
/* 107 */     if (obj != null)
/*     */     {
/* 109 */       Field[] objs = obj.getClass().getFields();
/* 110 */       StringBuilder sb = new StringBuilder();
/* 111 */       StringBuilder where = new StringBuilder();
/* 112 */       for (int i = 0; i < objs.length; i++)
/*     */       {
/* 114 */         if (fields.contains(objs[i].getName())) continue;
/*     */         try {
/* 116 */           if (IfStr(objs[i].getType().getSimpleName()).booleanValue())
/*     */           {
/* 118 */             sb.append(objs[i].getName());
/* 119 */             sb.append(s1);
/* 120 */             sb.append("'");
/* 121 */             sb.append(objs[i].get(obj));
/* 122 */             sb.append("'");
/*     */           }
/*     */           else
/*     */           {
/* 126 */             sb.append(objs[i].getName());
/* 127 */             sb.append(s1);
/* 128 */             sb.append(objs[i].get(obj));
/*     */           }
/*     */         } catch (IllegalArgumentException e) {
/* 131 */           LastError = "ArrayConvertToValueString:" + e.getMessage();
/* 132 */           return null;
/*     */         } catch (IllegalAccessException e) {
/* 134 */           LastError = "ArrayConvertToValueString:" + e.getMessage();
/* 135 */           return null;
/*     */         }
/* 137 */         if (i >= objs.length - 1) continue; sb.append(s2);
/*     */       }
/* 139 */       for (int k = 0; k < fields.size(); k++)
/*     */       {
/*     */         try {
/* 142 */           Field f = null;
/*     */           try {
/* 144 */             f = obj.getClass().getField((String)fields.get(k));
/*     */           }
/*     */           catch (SecurityException e) {
/* 147 */             LastError = "ObjectConvertToFieldValueString:" + e.getMessage();
/* 148 */             return null;
/*     */           }
/*     */           catch (NoSuchFieldException e)
/*     */           {
/* 152 */             LastError = "ObjectConvertToFieldValueString:" + e.getMessage();
/* 153 */             return null;
/*     */           }
/* 155 */           if (IfStr(f.getType().getSimpleName()).booleanValue())
/*     */           {
/* 157 */             where.append(f.getName());
/* 158 */             where.append(s1);
/* 159 */             where.append("'");
/* 160 */             where.append(f.get(obj));
/* 161 */             where.append("'");
/*     */           }
/*     */           else
/*     */           {
/* 165 */             where.append(f.getName());
/* 166 */             where.append(s1);
/* 167 */             where.append(f.get(obj));
/*     */           }
/*     */         } catch (IllegalArgumentException e) {
/* 170 */           LastError = "ObjectConvertToFieldValueString:" + e.getMessage();
/* 171 */           return null;
/*     */         } catch (IllegalAccessException e) {
/* 173 */           LastError = "ObjectConvertToFieldValueString:" + e.getMessage();
/* 174 */           return null;
/*     */         }
/* 176 */         if (k >= fields.size() - 1) continue; sb.append(s2);
/*     */       }
/* 178 */       if (fields.size() > 0)
/* 179 */         sb.append(" where " + where.toString());
/* 180 */       return sb.toString();
/*     */     }
/* 182 */     LastError = "ArrayConvertToValueString:obj is null";
/* 183 */     return null;
/*     */   }
/*     */ 
/*     */   public static String ObjectToString(Object obj)
/*     */   {
/* 188 */     if (obj == null) return "";
/* 189 */     return obj.toString();
/*     */   }
/*     */ 
/*     */   public static Boolean IfStr(String type)
/*     */   {
/* 194 */     if (type.equalsIgnoreCase("String"))
/* 195 */       return Boolean.valueOf(true);
/* 196 */     if (type.equalsIgnoreCase("Date"))
/* 197 */       return Boolean.valueOf(true);
/* 198 */     if (type.equalsIgnoreCase("char")) {
/* 199 */       return Boolean.valueOf(true);
/*     */     }
/* 201 */     return Boolean.valueOf(false);
/*     */   }
/*     */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.unit.tools.Convert
 * JD-Core Version:    0.6.0
 */