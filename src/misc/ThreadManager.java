package misc;

import java.util.LinkedList;
import java.util.List;

public class ThreadManager {
	private static List<ThreadWrapper> threads = new LinkedList<ThreadWrapper>();
	
	public static void register(Thread t, String name){
		threads.add(new ThreadWrapper(t, name));
	}
	
	public static Thread getThreadByName(String name){
		int index = threads.indexOf(new ThreadWrapper(null, name));
		if (index != -1){
			return threads.get(index).getThread();
		} else {
			return null;
		}
	}
}
