package misc;

import listener.Notifier;

public class NotifierManager {
	public static Notifier n = null;
	
	public static void setNotifier(Notifier nn){
		n = nn;
	}
	
	public static Notifier currentNotifier(){
		return n;
	}
	
	public static Notifier getNotifier(){
		return currentNotifier();
	}
}

