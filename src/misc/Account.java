package misc;

import java.io.Serializable;

import logging.Logger;

public class Account extends Thread implements Serializable{
	private String name = "null";
	private int accountID = -1;
	private int accountState = 0;
	
	public static int STATE_LOGGEEDOUT = 0;
	public static int STATE_LOGGEDIN = 1;
	
	public Account(String name, int id, int accountState){
		this.name = name;
		this.accountID = accountID;
		this.accountState = accountState;
	}
	
	public Account(String name, int id){
		this.name = name;
		this.accountID = accountID;
		this.accountState = 0;
	}
	
	public Account(int id){
		this.name = "null";
		this.accountID = accountID;
		this.accountState = 0;
	}
	
	public void setAccountName(String name){
		this.name = name;
	}
	
	public void setAccountID(int id){
		this.accountID = accountID;
	}
	
	public void setAccountState(int accountState){
		this.accountState = accountState;
	}
	
	public String getAccountName(){
		if (name.equals("null")){
			return null;
		} else {
			return name;
		}
	}
	
	public int getAccountID(){
		return accountID;
	}
	
	public int getAccountState(){
		return accountState;
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
