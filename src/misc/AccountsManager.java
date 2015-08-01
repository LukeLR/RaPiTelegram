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

public class AccountsManager {
	public static List<Account> accounts = new LinkedList();
	public static String filename = "accounts.dat";
	
	public static void addAccount(Account acc){
		accounts.add(acc);
	}
	
	public static Account getAccount(Account dummy){
		int index = accounts.indexOf(dummy);
		if (index == -1){
			return null;
		} else {
			return accounts.get(index);
		}
	}
	
	public static List<Account> getAccounts(){
		return accounts;
	}
	
	public static List<Account> loadAccounts(String filename){
		File constantsFile = new File (filename);
		if (constantsFile.exists()){
			Logger.logMessage('I', new AccountsManager(), "Loading Accounts from " + filename);
			try {
				FileInputStream saveFile = new FileInputStream(constantsFile);
				ObjectInputStream restore = new ObjectInputStream(saveFile);
				Object obj = restore.readObject();
				restore.close();
				return (List<Account>)obj;
//				return new Constants();
			} catch (IOException | ClassNotFoundException e) {
				Logger.logException(new AccountsManager(), "Error loading Accounts!", e);
				return new LinkedList();
			}
		} else {
			Logger.logMessage('I', new AccountsManager(), "Creating new Accounts");
			return new LinkedList();
		}
	}

	public List<Account> loadConstants(){
		Logger.logMessage('I', new AccountsManager(), "Loading Accounts from default filename " + filename);
		return loadAccounts(filename); //Invoking loadConstants() with default filename
	}

	public static void saveConstants (String filename){
		Logger.logMessage('I', new AccountsManager(), "Saving Accounts to " + filename);
		try {
			FileOutputStream saveFile = new FileOutputStream(new File(filename));
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(accounts);
			save.close();
		} catch (IOException e) {
			Logger.logException(new AccountsManager(), "Error saving Accounts!", e);
			e.printStackTrace();
		}
	}

	public void saveConstants (){
		Logger.logMessage('I', new AccountsManager(), "Saving Constants to default filename " + filename);
		saveConstants (filename); //Invoking saveConstants with saved filename
	}

	public static void deleteConstants(String filename) {
		Logger.logMessage('I', new AccountsManager(), "Deleting Accounts-File saved at " + filename);
		File f = new File(filename);

		if (!f.exists()){
			Logger.logMessage('E', new AccountsManager(), "Could not delete Accounts-File at " + filename + ": File does not exist");
		} else {
			if (!f.canWrite()){
				Logger.logMessage('E', new AccountsManager(), "Could not delete Accounts-File at " + filename + ": No write access");
			} else {
				if (f.isDirectory()){
					String[] files = f.list();
					if (files.length > 0) {
						 Logger.logMessage('E', new AccountsManager(), "Could not delete Accounts-File at " + filename + ": Is a direcotry (And not empty)");
					} else {
						boolean success = f.delete();
						if (!success){
							Logger.logMessage('E', new AccountsManager(), "Could not delete Accounts-File at " + filename + ": An unknown error occured!");
						}
					}
				}
			}
		}
	}

	public void deleteConstants (){
		Logger.logMessage('I', new AccountsManager(), "Deleting Constants with default filename " + filename);
		deleteConstants(filename); //Invoking deleteConstants() with saved filename
	}
	
}
