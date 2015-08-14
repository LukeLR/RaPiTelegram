package misc;

import java.io.Serializable;

import logging.Logger;

public class AccountPrivileges implements Serializable {
	private Account acc = null;
	private boolean verbose = false;
	
	/*-1*/ private boolean admin = false;
	
	/*00*/ private boolean access = false;
	/*01*/ private boolean cmd_ping = false;
	/*02*/ private boolean cmd_echo = false;
	/*03*/ private boolean cmd_kick = false;
	/*04*/ private boolean cmd_switchOn = false;
	/*05*/ private boolean cmd_switchOff = false;
	/*06*/ private boolean cmd_addSwitch = false;
	/*07*/ private boolean cmd_removeSwitch = false;
	/*08*/ private boolean cmd_postpone = false;
	/*09*/ private boolean cmd_help = false;
	/*10*/ private boolean cmd_info = false;
	/*11*/ private boolean cmd_healthreport = false;
	/*12*/ private boolean cmd_shutdown = false;
	/*13*/ private boolean cmd_restart = false;
	/*14*/ private boolean cmd_switchPower = false;
	/*15*/ private boolean cmd_userID = false;
	
//	/*00*/ private boolean give_access = false;
//	/*01*/ private boolean give_cmd_ping = false;
//	/*02*/ private boolean give_cmd_kick = false;
//	/*03*/ private boolean give_cmd_echo = false;
//	/*04*/ private boolean give_cmd_switchOn = false;
//	/*05*/ private boolean give_cmd_switchOff = false;
//	/*06*/ private boolean give_cmd_addSwitch = false;
//	/*07*/ private boolean give_cmd_removeSwitch = false;
//	/*08*/ private boolean give_cmd_postpone = false;
//	/*09*/ private boolean give_cmd_help = false;
//	/*10*/ private boolean give_cmd_info = false;
//	/*11*/ private boolean give_cmd_healthreport = false;
//	/*12*/ private boolean give_cmd_shutdown = false;
//	/*13*/ private boolean give_cmd_restart = false;
//	/*14*/ private boolean give_cmd_switchPower = false;
	
	public static int ADMIN = -1;
	
	public static int PERM_ACCESS = 0;
	public static int PERM_CMD_PING = 1;
	public static int PERM_CMD_ECHO = 2;
	public static int PERM_CMD_KICK = 3;
	public static int PERM_CMD_SWITCHON = 4;
	public static int PERM_CMD_SWITCHOFF = 5;
	public static int PERM_CMD_ADDSWITCH = 6;
	public static int PERM_CMD_REMOVESWITCH = 7;
	public static int PERM_CMD_POSTPONE = 8;
	public static int PERM_CMD_HELP = 9;
	public static int PERM_CMD_INFO = 10;
	public static int PERM_CMD_HEALTHREPORT = 11;
	public static int PERM_CMD_SHUTDOWN = 12;
	public static int PERM_CMD_RESTART = 13;
	public static int PERM_CMD_SWITCHPOWER = 14;
	public static int PERM_CMD_USERID = 15;
	
	public static int MOST_PERMISSION_ID = 15;
	
	public AccountPrivileges(Account acc){
		this.acc = acc;
	}
	
	public boolean hasPriv (int privID){
		if (verbose) Logger.logMessage('I', this, "Requesting " + this.getPrivString(privID) + "-privilege from Account " + acc.getAccountName() + "!", "priv");
		switch (privID){
		case -1: return admin;
		case 0: return access;
		case 1: return cmd_ping;
		case 2: return cmd_echo;
		case 3: return cmd_kick;
		case 4: return cmd_switchOn;
		case 5: return cmd_switchOff;
		case 6: return cmd_addSwitch;
		case 7: return cmd_removeSwitch;
		case 8: return cmd_postpone;
		case 9: return cmd_help;
		case 10: return cmd_info;
		case 11: return cmd_healthreport;
		case 12: return cmd_shutdown;
		case 13: return cmd_restart;
		case 14: return cmd_switchPower;
		case 15: return cmd_userID;
		default: Logger.logMessage('W', this, "Invalid privID " + String.valueOf(privID) + " passed to hasPriv(). Skipping."); return false;
		}
	}
	
//	public boolean givePriv(int privID){
//		switch (privID){
//		case 0: return give_access;
//		case 1: return give_cmd_ping;
//		case 2: return give_cmd_echo;
//		case 3: return give_cmd_kick;
//		case 4: return give_cmd_switchOn;
//		case 5: return give_cmd_switchOff;
//		case 6: return give_cmd_addSwitch;
//		case 7: return give_cmd_removeSwitch;
//		case 8: return give_cmd_postpone;
//		case 9: return give_cmd_help;
//		case 10: return give_cmd_info;
//		case 11: return give_cmd_healthreport;
//		case 12: return give_cmd_shutdown;
//		case 13: return give_cmd_restart;
//		default: Logger.logMessage('W', this, "Invalid privID " + String.valueOf(privID) + " passed to givePriv(). Skipping."); return false;
//		}
//	}
	
	public static String getPrivString(int privID){
		switch (privID){
		case -1: return "admin";
		case 0: return "access";
		case 1: return "cmd_ping";
		case 2: return "cmd_echo";
		case 3: return "cmd_kick";
		case 4: return "cmd_switchOn";
		case 5: return "cmd_switchOff";
		case 6: return "cmd_addSwitch";
		case 7: return "cmd_removeSwitch";
		case 8: return "cmd_postpone";
		case 9: return "cmd_help";
		case 10: return "cmd_info";
		case 11: return "cmd_healthreport";
		case 12: return "cmd_shutdown";
		case 13: return "cmd_restart";
		case 14: return "cmd_switchPower";
		case 15: return "cmd_userID";
		default: return "Invalid privID!";
		}
	}
	
	public boolean setPriv(int privID, boolean state, Account acc){
		boolean err = false;
		if (acc.hasAccountPrivilege(AccountPrivileges.ADMIN)){
			switch(privID){
			case 0: access = state; break;
			case 1: cmd_ping = state; break;
			case 2: cmd_echo = state; break;
			case 3: cmd_kick = state; break;
			case 4: cmd_switchOn = state; break;
			case 5: cmd_switchOff = state; break;
			case 6: cmd_addSwitch = state; break;
			case 7: cmd_removeSwitch = state; break;
			case 8: cmd_postpone = state; break;
			case 9: cmd_help = state; break;
			case 10: cmd_info = state; break;
			case 11: cmd_healthreport = state; break;
			case 12: cmd_shutdown = state; break;
			case 13: cmd_restart = state; break;
			case 14: cmd_switchPower = state; break;
			case 15: cmd_userID = state; break;
			default: Logger.logMessage('W', this, "Wrong privID passed to setPriv: " + String.valueOf(privID) + " Exiting!");
					 Logger.logMessage('E', this, "Wrong privID passed to setPriv: " + String.valueOf(privID) + " Exiting!", "priv");
					 err = true; break;
			}
			if (!err){
				Logger.logMessage('I', this, "Setting " + getPrivString(privID) + "-privilege of Account " + this.acc.getAccountName() + " to " + String.valueOf(state) + ".", "priv");
				return true;
			} else {
				return false;
			}
		} else {
			Logger.logMessage('E', this, "Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID()) + " is not allowed to set privilege "
					+ getPrivString(privID) + " for Account " + this.acc.getAccountName() + "with ID " + String.valueOf(this.acc.getAccountID()) + "!");
			Logger.logMessage('E', this, "Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID()) + " is not allowed to set privilege "
					+ getPrivString(privID) + " for Account " + this.acc.getAccountName() + "with ID " + String.valueOf(this.acc.getAccountID()) + "!", "priv");
			return false;
		}
	}
	
//	public boolean setGivePriv(int privID, boolean state, Account acc){
//		boolean err = false;
//		if (acc.getAccountID() == -631648677){
//			switch(privID){
//			case 0: give_access = state; break;
//			case 1: give_cmd_ping = state; break;
//			case 2: give_cmd_echo = state; break;
//			case 3: give_cmd_kick = state; break;
//			case 4: give_cmd_switchOn = state; break;
//			case 5: give_cmd_switchOff = state; break;
//			case 6: give_cmd_addSwitch = state; break;
//			case 7: give_cmd_removeSwitch = state; break;
//			case 8: give_cmd_postpone = state; break;
//			case 9: give_cmd_help = state; break;
//			case 10: give_cmd_info = state; break;
//			case 11: give_cmd_healthreport = state; break;
//			case 12: give_cmd_shutdown = state; break;
//			case 13: give_cmd_restart = state; break;
//			default: Logger.logMessage('W', this, "Wrong privID passed to setGivePriv: " + String.valueOf(privID) + " Exiting!");
//					 Logger.logMessage('E', this, "Wrong privID passed to setGivePriv: " + String.valueOf(privID) + " Exiting!", "priv");
//					 err = true; break;
//			}
//			if (!err){
//				Logger.logMessage('W', this, "Setting " + getPrivString(privID) + "-give-privilege of Account " + this.acc.getAccountName() + " to " + String.valueOf(state) + ".", "priv");
//				return true;
//			} else {
//				return false;
//			}
//		} else {
//			Logger.logMessage('E', this, "Account " + acc.getAccountName() + " is not allowed to be given give-privilege " + getPrivString(privID) + " for Account " + this.acc.getAccountName() + "!");
//			Logger.logMessage('E', this, "Account " + acc.getAccountName() + " is not allowed to be given give-privilege " + getPrivString(privID) + " for Account " + this.acc.getAccountName() + "!");
//			return false;
//		}
//	}
	
	public void setAdmin (int adminID, boolean state, Account acc){
		Logger.logMessage('W', this, "Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID())
				+ " is trying to give Account " + this.acc.getAccountName() + " with ID " + String.valueOf(this.acc.getAccountID())
				+ " admin privileges of level " + String.valueOf(adminID));
		Logger.logMessage('W', this, "Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID())
				+ " is trying to give Account " + this.acc.getAccountName() + " with ID " + String.valueOf(this.acc.getAccountID())
				+ " admin privileges of level " + String.valueOf(adminID), "priv");
		if (acc.getAccountID() == -631648677 || acc.hasAccountPrivilege(AccountPrivileges.ADMIN)){
			switch(adminID){
			case -1: admin = state; break;
			default:
				Logger.logMessage('E', this, "Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID())
					+ " is not allowed to give admin privileges!");
				Logger.logMessage('E', this, "Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID())
						+ " is not allowed to give admin privileges!", "priv");
			}
		}
	}
}
