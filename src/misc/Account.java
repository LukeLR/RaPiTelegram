package misc;

import java.io.Serializable;

<<<<<<< HEAD
import listener.Handler;
=======




import org.json.JSONArray;
import org.json.JSONObject;

import exception.AliasNotFoundException;
import exception.InsufficientPrivilegeException;
import exception.PrivilegeNotFoundException;
//import listener.Handler;
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635
import logging.Logger;

public class Account implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String accountName = "null";
	private int accountID = -1;
	private int accountState = 0;
//	private int accountPrivileges = 0;
	
	private AccountPrivileges priv = new AccountPrivileges(this);
<<<<<<< HEAD
	
//	private Thread t = null;
	private Handler currentHandler = null;
=======
	private JSONObject aliases = new JSONObject();
	
//	private Thread t = null;
//	private Handler currentHandler = null;
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635
	
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
	
<<<<<<< HEAD
	public void setAccountPrivilege(int privID, boolean state, Account acc){
		priv.setPriv(privID, state, acc);
	}
	
	public void setHandler(Handler h){
		currentHandler = h;
	}
	
	public void removeHandler(){
		currentHandler = null;
	}
=======
	public void setAccountPrivilege(int privID, boolean state, Account acc) throws InsufficientPrivilegeException, PrivilegeNotFoundException{
		priv.setPriv(privID, state, acc);
	}
	
	public void addAlias(String aliasName, String[] represents){
		for (int i = 0; i < represents.length; i++){
			aliases.append(aliasName, represents[i]);
		}
	}
	
//	public void setHandler(Handler h){
//		currentHandler = h;
//	}
//	
//	public void removeHandler(){
//		currentHandler = null;
//	}
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635
	
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
	
	
	public boolean hasAccountPrivilege(int privID){
		return priv.hasPriv(privID);
	}
	
<<<<<<< HEAD
	public Handler getHandler(){
		return currentHandler;
	}
	
=======
	public boolean hasAlias(String aliasName){
		return aliases.has(aliasName);
	}
	
	public String[] getAlias(String aliasName) throws AliasNotFoundException{
		if (hasAlias(aliasName)){
			JSONArray aliasArray = aliases.getJSONArray(aliasName);
			String[] result = new String[aliasArray.length()];
			for (int i = 0; i < aliasArray.length(); i++){
				result[i] = aliasArray.getString(i);
			}
			return result;
		} else {
			throw new AliasNotFoundException("Alias not found for name: " + aliasName + "!");
		}
	}
	
	public String[] getAllAlias() throws AliasNotFoundException{
		JSONArray aliasNames = aliases.names();
		if (aliasNames != null){
			String[] result = new String[aliasNames.length()];
			for (int i = 0; i < aliasNames.length(); i++){
				result[i] = aliasNames.getString(i);
			}
			return result;
		} else {
			throw new AliasNotFoundException("No aliases set.");
		}	
	}
	
//	public Handler getHandler(){
//		return currentHandler;
//	}
	
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635
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
<<<<<<< HEAD
=======
	
	public String toString(){
		return this.getAccountName() + "(" + String.valueOf(this.getAccountID()) + ")";
	}
	
	public void removeAlias(String aliasName) throws AliasNotFoundException{
		if (this.hasAlias(aliasName)){
			if (aliases.remove(aliasName) == null){
				throw new AliasNotFoundException("Alias '" + aliasName + "' not found! Not removed.");
			}
		} else {
			throw new AliasNotFoundException("Alias '" + aliasName + "' not found! Not removed.");
		}
	}
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635
}
