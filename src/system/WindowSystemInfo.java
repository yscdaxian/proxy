/*    */ package system;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.Inet6Address;
/*    */ import java.net.InetAddress;
/*    */ import java.net.NetworkInterface;
/*    */ import java.net.SocketException;
/*    */ import java.util.Enumeration;
/*    */ 
/*    */ public class WindowSystemInfo
/*    */   implements ISystemInfo
/*    */ {
/*    */   public String getMacAddr()
/*    */   {
/* 19 */     String address = "";
/*    */     try
/*    */     {
/* 22 */       String command = "cmd.exe /c ipconfig /all";
/* 23 */       Process p = Runtime.getRuntime().exec(command);
/* 24 */       BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
/*    */       String line;
/* 26 */       while ((line = br.readLine()) != null)
/*    */       {
/*    */         
/* 27 */         if (line.indexOf("Physical Address") > 0) {
/* 28 */           int index = line.indexOf(":");
/* 29 */           index += 2;
/* 30 */           address = line.substring(index);
/* 31 */           break;
/*    */         }
/*    */       }
/* 34 */       br.close();
/* 35 */       return address.trim().replace("-", ""); } catch (IOException localIOException) {
/*    */     }
/* 37 */     return address.replace("-", "");
/*    */   }
/*    */ 
/*    */   public String getLocalIP()
/*    */   {
/* 43 */     String ip = "";
/*    */     try {
/* 45 */       Enumeration e1 = NetworkInterface.getNetworkInterfaces();
/* 46 */       while (e1.hasMoreElements()) {
/* 47 */         NetworkInterface ni = (NetworkInterface)e1.nextElement();
/* 48 */         if (!ni.getName().equals("eth0")) {
/*    */           continue;
/*    */         }
/* 51 */         Enumeration e2 = ni.getInetAddresses();
/* 52 */         while (e2.hasMoreElements()) {
/* 53 */           InetAddress ia = (InetAddress)e2.nextElement();
/* 54 */           if ((ia instanceof Inet6Address))
/*    */             continue;
/* 56 */           ip = ia.getHostAddress();
/*    */         }
/* 58 */         break;
/*    */       }
/*    */     }
/*    */     catch (SocketException e) {
/* 62 */       e.printStackTrace();
/* 63 */       System.exit(-1);
/*    */     }
/* 65 */     return ip;
/*    */   }
/*    */ 
/*    */   public String getCpuId()
/*    */   {
/* 71 */     String result = "";
/*    */     try {
/* 73 */       File file = File.createTempFile("realhowto", ".vbs");
/* 74 */       file.deleteOnExit();
/* 75 */       FileWriter fw = new FileWriter(file);
/*    */ 
/* 77 */       String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_Processor\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.ProcessorId \n    exit for  ' do the first cpu only! \nNext \n";
/*    */ 
/* 85 */       fw.write(vbs);
/* 86 */       fw.close();
/* 87 */       Process p = Runtime.getRuntime().exec(
/* 88 */         "cscript //NoLogo " + file.getPath());
/* 89 */       BufferedReader input = new BufferedReader(
/* 90 */         new InputStreamReader(p.getInputStream()));
/*    */       String line;
/* 92 */       while ((line = input.readLine()) != null)
/*    */       {
/*    */       
/* 93 */         result = result + line;
/*    */       }
/* 95 */       input.close();
/*    */     } catch (Exception e) {
/* 97 */       return e.toString();
/*    */     }
/* 99 */     return result.trim().replace(" ", "");
/*    */   }
/*    */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.system.WindowSystemInfo
 * JD-Core Version:    0.6.0
 */