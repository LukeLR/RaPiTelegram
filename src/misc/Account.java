package misc;

import java.io.Serializable;

import listener.Handler;
import logging.Logger;

public class Account implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String accountName = "null";
	private int accountID = -1;
	private int accountState = 0;
//	private int accountPrivileges = 0;
	
	private AccountPrivileges priv = new AccountPrivileges(this);
	
//	private Thread t = null;
	private Handler currentHandler = null;
	
	public static int STATE_LOGGEDOFF = 0;
	public static int STATE_LOGGEDIN = 1;
	
	public static int PRIVILEGES_NONE = 0;
	public static int PRIVILEGES_ROOT = 99;
	
	// ------ Constructors: ------
	
	public Account(String accountName, int accountID, int accountState){
		this.accountName = accountName;
		this.accountID = accountID;
		this.accountState = accountState;
		this.priv = new AccountPrivileges(this);
	}
	
	public Account(String accountName, int accountID){
		this.accountName = accountName;
		this.accountID = accountID;
		this.accountState = 0;
		this.priv = new AccountPrivileges(this);
	}
	
	public Account(int accountID){
		this.accountName = "null";
		this.accountID = accountID;
		this.accountState = 0;
		this.priv = new AccountPrivileges(this);
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
	
	public void setAccountPrivileges(int privID, boolean state, Account acc){
		priv.setPriv(privID, state, acc);
	}
	
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
	
	
	public boolean hasAccountPrivileges(int privID){
		return priv.hasPriv(privID);
	}
	
	public Handler getHandler(){
		return currentHandler;
	}
	
	public AccountPrivileges getPriv(){
		return priv;
	}
	
	// ------ others: ------
	
	public boolean equals(Object compare){
		try{
			return ((Account) compare).getAccountID() == getAccountID();
		} catch (Exception ex){
			Logger.logMessage('W', this, "Comparing failed! Is the comparison object of type 'Account'?");
			return false;
		}
	}
}
