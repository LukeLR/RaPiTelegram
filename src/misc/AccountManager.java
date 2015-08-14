package misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.io.IOException;

import logging.Logger;

public class AccountManager {
	public static List<Account> accounts = new LinkedList<Account>();
	public static List<AccountOnlineManager> acos= new LinkedList<AccountOnlineManager>();
	public static String filename = "accounts.dat";
	public static long interval = 5000;
	
	public static void addAccount(Account acc){
		accounts.add(acc);
		acos.add(new AccountOnlineManager(acc, interval));
	}
	
	public static Account getAccount(Account dummy){
		int index = accounts.indexOf(dummy);
		if (index == -1){
			return null;
		} else {
			return accounts.get(index);
		}
	}
	
	public static AccountOnlineManager getAccountOnlineManager(Account find){
		AccountOnlineManager dummy = new AccountOnlineManager(find, 5000);
		int index = acos.indexOf(dummy);
		if (index == -1){
			return null;
		} else {
			return acos.get(index);
		}
	}
	
	public static List<Account> getAccounts(){
		return accounts;
	}
	
	@SuppressWarnings("unchecked")
	public static void loadAccounts(String filename){
		File constantsFile = new File (filename);
		if (constantsFile.exists()){
			Logger.logMessage('I', new AccountManager(), "Loading Accounts from " + filename);
			try {
				FileInputStream saveFile = new FileInputStream(constantsFile);
				ObjectInputStream restore = new ObjectInputStream(saveFile);
				Object obj = restore.readObject();
				restore.close();
				accounts = (List<Account>)obj;
//				return new Constants();
			} catch (IOException | ClassNotFoundException e) {
//				Logger.logException(new AccountManager(), "Error loading Accounts!", e);
				Logger.logMessage('E', new AccountManager(), "Error loading Accounts! Creating new ones.");
//				accounts = new LinkedList();
			}
		} else {
			Logger.logMessage('I', new AccountManager(), "Creating new Accounts");
			accounts = new LinkedList<Account>();
		}
		
		AccountManager.initOnlineManagers();
	}

	public static void loadAccounts(){
		Logger.logMessage('I', new AccountManager(), "Loading Accounts from default filename " + filename);
		loadAccounts(filename); //Invoking loadAccounts() with default filename
	}

	public static void saveAccounts (String filename){
		Logger.logMessage('I', new AccountManager(), "Saving Accounts to " + filename);
		try {
			FileOutputStream saveFile = new FileOutputStream(new File(filename));
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(accounts);
			save.close();
		} catch (IOException e) {
			Logger.logException(new AccountManager(), "Error saving Accounts!", e);
			e.printStackTrace();
		}
	}

	public static void saveAccounts(){
		Logger.logMessage('I', new AccountManager(), "Saving Accounts to default filename " + filename);
		saveAccounts (filename); //Invoking saveAccounts with default filename
	}

	public static void deleteAccounts(String filename) {
		Logger.logMessage('I', new AccountManager(), "Deleting Accounts-File saved at " + filename);
		File f = new File(filename);

		if (!f.exists()){
			Logger.logMessage('E', new AccountManager(), "Could not delete Accounts-File at " + filename + ": File does not exist");
		} else {
			if (!f.canWrite()){
				Logger.logMessage('E', new AccountManager(), "Could not delete Accounts-File at " + filename + ": No write access");
			} else {
				if (f.isDirectory()){
					String[] files = f.list();
					if (files.length > 0) {
						 Logger.logMessage('E', new AccountManager(), "Could not delete Accounts-File at " + filename + ": Is a direcotry (And not empty)");
					} else {
						boolean success = f.delete();
						if (!success){
							Logger.logMessage('E', new AccountManager(), "Could not delete Accounts-File at " + filename + ": An unknown error occured!");
						}
					}
				}
			}
		}
	}

	public void deleteAccounts (){
		Logger.logMessage('I', new AccountManager(), "Deleting Constants with default filename " + filename);
		deleteAccounts(filename); //Invoking deleteAccounts() with default filename
	}
	
	public static void initOnlineManagers(){
		acos = new LinkedList<AccountOnlineManager>();
		for (int i = 0; i < accounts.size(); i++){
			acos.add(new AccountOnlineManager(accounts.get(i), interval));
		}
	}
}
