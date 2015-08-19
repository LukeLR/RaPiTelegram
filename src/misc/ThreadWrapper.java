package misc;

import logging.Logger;

public class ThreadWrapper {
	private Thread t;
	private String name;
	public ThreadWrapper(Thread t, String name) {
		this.t = t;
		this.name = name;
	}
	
	public Thread getThread(){
		return t;
	}
	
	public void setThread(Thread t){
		this.t = t;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public boolean equals (Object anObject){
		try{
			((ThreadWrapper)anObject).getName().equals(getName());
			return true;
		} catch(Exception ex) {
			Logger.logMessage('W', this, "Object provided to ThreadWrapper for comparison couldn't be compared."
					+ "Is it an instance of ThreadWrapper?");
			return false;
		}
	}
}
