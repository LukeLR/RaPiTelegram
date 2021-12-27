package misc;

import java.util.LinkedList;
import java.util.List;

public class ThreadManager {
	private static List<ThreadWrapper> threads = new LinkedList<ThreadWrapper>();
	private static int index = 0;
	
	public static int register(Thread t){
		threads.add(new ThreadWrapper(t, index));
		index++;
		return index - 1;
	}
	
	public static int register(Thread t, Account owner){
		threads.add(new ThreadWrapper(t, index, owner));
		index++;
		return index - 1;
	}
	
	public static Thread getThreadByIndex(int index){
		ThreadWrapper temp = ThreadManager.getThreadWrapperByIndex(index);
		if (temp != null){
			return temp.getThread();
		} else {
			return null;
		}
	}
	
	public static ThreadWrapper getThreadWrapperByIndex(int index){
		int result = threads.indexOf(new ThreadWrapper(null, index));
		if (result != -1){
			return threads.get(result);
		} else {
			return null;
		}
	}
	
	public static boolean allowKill(int index, Account anAccount) throws ThreadNotFoundException{
		ThreadWrapper temp = ThreadManager.getThreadWrapperByIndex(index);
		if (temp != null){
			return temp.allowKill(anAccount);
		} else {
			throw new ThreadNotFoundException("No Thread with given ID found.");
		}
	}
}
