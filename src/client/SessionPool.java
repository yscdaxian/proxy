package client;

import java.util.ArrayList;

import org.apache.mina.core.session.IoSession;

public class SessionPool {
			private static ArrayList<IoSession> allSession = new ArrayList();
	 
			public static ArrayList<IoSession> GetAllSession()
			{
				return allSession;
			} 
			public static void AddSession(IoSession session){
				allSession.add(session);
			}
	
			public static void RemoveSession(IoSession session){
				allSession.remove(session);
			}
}
