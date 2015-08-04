package misc;

import java.io.Serializable;

import listener.Handler;
import logging.Logger;

public class Account implements Runnable, Serializable{
	private static final long serialVersionUID = 1L;
	
	private String accountName = "null";
	private int accountID = -1;
	private int accountState = 0;
	private int accountPrivileges = 0;
	
//	private Thread t = null;
	private Handler currentHandler = null;
	
	public static int STATE_LOGGEDOFF = 0;
	public static int STATE_LOGGEDIN = 1;
	
	public static int PRIVILEGES_NONE = 0;
	public static int PRIVILEGES_ROOT = 99;
	
	// ------ Constructors: ------
	
	public Account(String accountName, int accountID, int accountPrivileges, int accountState){
		this.accountName = accountName;
		this.accountID = accountID;
		this.accountState = accountState;
		this.accountPrivileges = accountPrivileges;
//		this.setDaemon(true);
	}
	
	public Account(String accountName, int accountID, int accountPrivileges){
		this.accountName = accountName;
		this.accountID = accountID;
		this.accountState = 0;
		this.accountPrivileges = accountPrivileges;
//		this.setDaemon(true);
	}
	
	public Account(String accountName, int accountID){
		this.accountName = accountName;
		this.accountID = accountID;
		this.accountState = 0;
//		this.setDaemon(true);
	}
	
	public Account(int accountID){
		this.accountName = "null";
		this.accountID = accountID;
		this.accountState = 0;
//		this.setDaemon(true);
	}
	
	// ------ Setter methods: ------
	
	public void setAccountName(String accountName){
		this.accountName = accountName;
	}
	
	public void setAccountID(int accountID){
		this.accountID = accountID;
	}
	
	public void setAccountState(int accountState){
		this.accountState = accountState;
	}
	
	public void setAccountPrivileges(int accountPrivileges){
		this.accountPrivileges = accountPrivileges;
	}
	
//	public void setOnline(){
//		if (this.accountState == this.STATE_LOGGEDOFF){
//			accountState = this.STATE_LOGGEDIN;
//			t = new Thread(this);
//			t.start();
//		} else {
//			t.interrupt();
////			try {
////				Thread.sleep(1000);
////			} catch (InterruptedException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//			t = new Thread(this);
//			t.start();
//		}
//		this.accountState = this.STATE_LOGGEDIN;
//	}
	
	public void setHandler(Handler h){
		currentHandler = h;
	}
	
	public void removeHandler(){
		currentHandler = null;
	}
	
	// ------ Getter methods: ------
	
	public String getAccountName(){
		if (accountName.equals("null")){
			return null;
		} else {
			return accountName;
		}
	}
	
	public int getAccountID(){
		return accountID;
	}
	
	public int getAccountState(){
		return accountState;
	}
	
	public int getAccountPrivileges(){
		return accountPrivileges;
	}
	
	public boolean hasAccountPrivileges(int checkPrivileges){
		return accountPrivileges >= checkPrivileges;
	}
	
	public Handler getHandler(){
		return currentHandler;
	}
	
	// ------ others: ------
	
	public void run(){
		System.err.println("Calling run");
		
		if (currentHandler != null){
			currentHandler.sendMessage("Calling run");
		}
		
		//This will set the user to STATE_LOGGEDOFF after a given time
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (!t.isInterrupted()){
			System.err.println("User offline");
			
			if (currentHandler != null){
				currentHandler.sendMessage("User offline");
			}
			
			accountState = this.STATE_LOGGEDOFF;
		}
	}
	
	public boolean equals(Object compare){
		try{
			return ((Account) compare).getAccountID() == getAccountID();
		} catch (Exception ex){
			Logger.logMessage('W', this, "Comparing failed! Is the comparison object of type 'Account'?");
			return false;
		}
	}
}
