package misc;

import logging.Logger;

public class AccountOnlineManager implements Runnable {
	Thread t = null;
	Account acc = null;
	long interval = 5000;
	
	public AccountOnlineManager(Account acc, long interval){
		this.acc = acc;
		this.interval = interval;
	}
	
	public void setOnline(){
		if (acc.getAccountState() == Account.STATE_LOGGEDOFF){
			acc.setAccountState(Account.STATE_LOGGEDIN);
			if (t != null && t.isAlive()){
				t.interrupt();
				t = new Thread(this);
				t.start();
			} else {
				t = new Thread(this);
				t.start();
			}
		} else if (acc.getAccountState() > Account.STATE_LOGGEDOFF){
			if (t != null && t.isAlive()){
				t.interrupt();
				t = new Thread(this);
				t.start();
			} else {
				t = new Thread(this);
				t.start();
			}
		} else {
			Logger.logMessage('E', this, "AccoundState of Account " + acc.getAccountName() + " is neither 0 nor greater than 0."
					+ System.lineSeparator() + "Couldn't determine Account state when setting online. No online timer set.");
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			Thread.currentThread().sleep(interval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		acc.setAccountState(Account.STATE_LOGGEDOFF);
	}
}
