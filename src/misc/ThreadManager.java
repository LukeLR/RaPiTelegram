package misc;

import java.util.LinkedList;
import java.util.List;

public class ThreadManager {
	private static List<ThreadWrapper> threads = new LinkedList<ThreadWrapper>();
	private static int index = 0;
	
	public static int register(Thread t){
		threads.add(new ThreadWrapper(t, index));
		index++;
		return index -1;
	}
	
	public static Thread getThreadByIndex(int index){
		int result = threads.indexOf(new ThreadWrapper(null, index));
		if (result != -1){
			return threads.get(result).getThread();
		} else {
			return null;
		}
	}
}
