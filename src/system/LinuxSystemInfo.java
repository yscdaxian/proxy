/*    */ package system;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.Inet6Address;
/*    */ import java.net.InetAddress;
/*    */ import java.net.NetworkInterface;
/*    */ import java.net.SocketException;
/*    */ import java.util.Enumeration;
/*    */ 
/*    */ public class LinuxSystemInfo
/*    */   implements ISystemInfo
/*    */ {
/*    */   private String byteHEX(byte ib)
/*    */   {
/* 17 */     char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 
/* 18 */       'b', 'c', 'd', 'e', 'f' };
/* 19 */     char[] ob = new char[2];
/* 20 */     ob[0] = Digit[(ib >>> 4 & 0xF)];
/* 21 */     ob[1] = Digit[(ib & 0xF)];
/* 22 */     String s = new String(ob);
/* 23 */     return s;
/*    */   }
/*    */ 
/*    */   public String getMacAddr()
/*    */   {
/* 28 */     String MacAddr = "";
/* 29 */     String str = "";
/*    */     try {
/* 31 */       NetworkInterface NIC = NetworkInterface.getByName("eth0");
/* 32 */       byte[] buf = NIC.getHardwareAddress();
/* 33 */       for (int i = 0; i < buf.length; i++) {
/* 34 */         str = str + byteHEX(buf[i]);
/*    */       }
/* 36 */       MacAddr = str.toUpperCase();
/*    */     } catch (SocketException e) {
/* 38 */       e.printStackTrace();
/* 39 */       System.exit(-1);
/*    */     }
/* 41 */     return MacAddr;
/*    */   }
/*    */ 
/*    */   public String getLocalIP()
/*    */   {
/* 47 */     String ip = "";
/*    */     try {
/* 49 */       Enumeration e1 = NetworkInterface.getNetworkInterfaces();
/* 50 */       while (e1.hasMoreElements()) {
/* 51 */         NetworkInterface ni = (NetworkInterface)e1.nextElement();
/* 52 */         if (!ni.getName().equals("eth0")) {
/*    */           continue;
/*    */         }
/* 55 */         Enumeration e2 = ni.getInetAddresses();
/* 56 */         while (e2.hasMoreElements()) {
/* 57 */           InetAddress ia = (InetAddress)e2.nextElement();
/* 58 */           if ((ia instanceof Inet6Address))
/*    */             continue;
/* 60 */           ip = ia.getHostAddress();
/*    */         }
/* 62 */         break;
/*    */       }
/*    */     }
/*    */     catch (SocketException e) {
/* 66 */       e.printStackTrace();
/* 67 */       System.exit(-1);
/*    */     }
/* 69 */     return ip;
/*    */   }
/*    */ 
/*    */   public String getCpuId()
/*    */   {
/* 75 */     String result = "";
/* 76 */     Runtime rt = Runtime.getRuntime();
/*    */     try {
/* 78 */       Process proc = rt.exec("dmidecode");
/* 79 */       InputStreamReader isr = new InputStreamReader(proc.getInputStream());
/* 80 */       BufferedReader br = new BufferedReader(isr);
/* 81 */       String line = null;
/* 82 */       boolean istest = false;
/* 83 */       while ((line = br.readLine()) != null) {
/* 84 */         if (line.toUpperCase().contains(
/* 85 */           "Processor Information".toUpperCase())) {
/* 86 */           istest = true;
/*    */         }
/* 88 */         if ((istest) && (line.trim().toUpperCase().startsWith("ID:"))) {
/* 89 */           result = line.toUpperCase().trim().substring(3);
/* 90 */           break;
/*    */         }
/*    */       }
/* 93 */       isr.close();
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 97 */       return e.toString();
/*    */     }
/* 99 */     return result.trim().replace(" ", "");
/*    */   }
/*    */ }

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.system.LinuxSystemInfo
 * JD-Core Version:    0.6.0
 */