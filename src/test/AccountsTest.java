package test;

import java.util.List;

import misc.Account;
import misc.AccountManager;

public class AccountsTest {
	public static void main(String[] args){
		Account acc1 = new Account("Lukas", 1);
		Account acc2 = new Account("Elisa<3", 2);
		
//		AccountManager.loadAccounts();
		
		String accString = "";
		List<Account> accs = AccountManager.getAccounts();
		int accSize = accs.size();
		
		for (int i = 0; i < accSize; i++){
			if (accString.equals("")){
				accString = accs.get(i).getAccountName();
			} else {
				accString = accString + ", " + accs.get(i).getAccountName();
			}
		}
		
		System.out.println(accString);
		
		AccountManager.addAccount(acc1);
		AccountManager.addAccount(acc2);
		
		acc1.setOnline();
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		acc1.setOnline();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		acc1.setOnline();
		
//		AccountManager.saveAccounts();
	}
}
