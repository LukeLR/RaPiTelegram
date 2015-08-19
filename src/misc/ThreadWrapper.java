package misc;

import logging.Logger;

public class ThreadWrapper {
	private Thread t;
	private int id;
	public ThreadWrapper(Thread t, int id) {
		this.t = t;
		this.id = id;
	}
	
	public Thread getThread(){
		return t;
	}
	
	public void setThread(Thread t){
		this.t = t;
	}
	
	public int getID(){
		return id;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public boolean equals (Object anObject){
		try{
			return ((ThreadWrapper)anObject).getID() == getID();
		} catch(Exception ex) {
			Logger.logMessage('W', this, "Object provided to ThreadWrapper for comparison couldn't be compared."
					+ "Is it an instance of ThreadWrapper?");
			return false;
		}
	}
}
