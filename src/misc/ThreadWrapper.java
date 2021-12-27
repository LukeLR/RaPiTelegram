package misc;

import logging.Logger;

public class ThreadWrapper {
	private Thread t = null;
	private int id = -1;
	private Account owner = null;
	public ThreadWrapper(Thread t, int id, Account owner) {
		this.t = t;
		this.id = id;
		this.owner = owner;
	}
	
	public ThreadWrapper(Thread t, int id){
		this.t = t;
		this.id = id;
		this.owner = null;
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
	
	public boolean allowKill(Account anAccount){
		if (owner == null){
			return true; //If no owner is set, anyone is allowed to kill.
		} else {
			return (owner.equals(anAccount)); //If owner is set, only owner may kill (or overrides)
		}
	}
	
	public Account owner(){
		return owner;
	}
	
	public Account getOwner(){
		return owner;
	}
}
