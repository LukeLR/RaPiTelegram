package misc;

import logging.Logger;

public class AccountOnlineManager implements Runnable {
	private Thread t = null;
	private Account acc = null;
	private long interval = 5000;
	private boolean verbose = true;
	
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
				t.setDaemon(true);
				t.start();
			} else {
				t = new Thread(this);
				t.setDaemon(true);
				t.start();
			}
			if (verbose) Logger.logMessage('I', this, "Account " + acc.getAccountName() + " now online! Offline in " + 
			String.valueOf(interval) + " milliseconds!");
		} else if (acc.getAccountState() > Account.STATE_LOGGEDOFF){
			if (t != null && t.isAlive()){
				t.interrupt();
				t = new Thread(this);
				t.setDaemon(true);
				t.start();
			} else {
				t = new Thread(this);
				t.setDaemon(true);
				t.start();
			}
			if (verbose) Logger.logMessage('I', this, "Account " + acc.getAccountName() + " was already online." + 
			" Resetting online timer. Offline in " + String.valueOf(interval) + " milliseconds!");
		} else {
			Logger.logMessage('E', this, "AccoundState of Account " + acc.getAccountName() + " is neither 0 nor greater than 0."
					+ System.lineSeparator() + "Couldn't determine Account state when setting online. No online timer set.");
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		boolean interrupted = false;
		try {
			Thread.currentThread().sleep(interval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			Logger.logMessage('W', this, "Sleep interrupted.");
			interrupted = true;
		}
		
		if (!interrupted){
			acc.setAccountState(Account.STATE_LOGGEDOFF);
			if (verbose) Logger.logMessage('I', this, "Account " + acc.getAccountName() + " offline!");
			
		}
	}
	
	public Account getAccount(){
		return acc;
	}
	
	public long getInterval(){
		return interval;
	}
	
	public void setInterval(long interval){
		this.interval = interval;
	}
	
	public boolean equals(Object compare){
		try{
			return ((AccountOnlineManager) compare).getAccount().equals(this.getAccount());
		} catch (Exception ex){
			//TODO: Find possible exceptions
			Logger.logMessage('E', this, "Unable to compare Accounts of AccountOnlineManager. Is comparison object of type AccountOnlineManager?");
			return false;
		}
	}
}
