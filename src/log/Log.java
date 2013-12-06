 package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import tools.TxDate;

public class Log
{
	static Log log=new Log();
	private String logPath = "./";
	public Boolean IsDebug = Boolean.valueOf(false);
	
	public static Log getInstance(){
		return log;
	}
	
	public void SetLogPath(String logpath)
	{
		this.logPath = logpath;
	}
 
	private void writeLog(LogType lt, String str)
	{
		Calendar c = GregorianCalendar.getInstance();
		String filename = c.get(1) + fillZero(new StringBuilder(String.valueOf(1 + c.get(2))).toString(), 2) + 
		fillZero(new StringBuilder().append(c.get(5)).toString(), 2) + "." + lt.toString();
		try
		{
			File f = new File(this.logPath + "/" + filename);
			BufferedWriter bufOut;

			if (f.exists())
				bufOut = new BufferedWriter(new FileWriter(f, true));
			else {
				bufOut = new BufferedWriter(new FileWriter(f));
			}
			String datetime = TxDate.TxNow();
			bufOut.write("\t" + datetime + ": ");
			bufOut.write(str + "\n");
			bufOut.close();
			if (this.IsDebug.booleanValue())
			{
				debug(str);
				System.out.println(str);
			}
		}
		catch (Exception localException)
		{
		}
	}

	private String fillZero(String str, int len) {
		int tmp = str.length();
		
		String str1 = str;
		if (tmp >= len)
			return str1;
			int t = len - tmp;
			for (int i = 0; i < t; i++)
				str1 = "0" + str1;
				return str1;
			}

	public void error(String str)
	{
		writeLog(LogType.error, str);
	}

	public void info(String str) {
		writeLog(LogType.info, str);
	}

	public void warning(String str) {
		writeLog(LogType.warning, str);
	}

	public void debug(String str) {
		if (this.IsDebug.booleanValue())
		{
			Calendar c = GregorianCalendar.getInstance();
			String filename = c.get(1) + fillZero(new StringBuilder(String.valueOf(1 + c.get(2))).toString(), 2) + 
					fillZero(new StringBuilder().append(c.get(5)).toString(), 2) + ".debug";
			try
			{
				File f = new File(this.logPath + "/" + filename);
				BufferedWriter bufOut;
    
				if (f.exists())
					bufOut = new BufferedWriter(new FileWriter(f, true));
				else {
					bufOut = new BufferedWriter(new FileWriter(f));
				}
				String datetime = TxDate.TxNow();
					bufOut.write("\t" + datetime + ":");
					bufOut.write(str + "\n");
					bufOut.close();
				}
			catch (Exception localException)
			{
			}
		}
	}
 }

