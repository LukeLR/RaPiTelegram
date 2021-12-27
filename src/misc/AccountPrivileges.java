package misc;

<<<<<<< HEAD
import logging.Logger;

public class AccountPrivileges {
	private Account acc = null;
	private boolean verbose = false;
	
=======
import java.io.Serializable;

import exception.InsufficientPrivilegeException;
import exception.PrivilegeNotFoundException;
import logging.Logger;

public class AccountPrivileges implements Serializable {
	private Account acc = null;
	private boolean verbose = false;
	
	/*-1*/ private boolean admin = false;
	
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635
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
<<<<<<< HEAD
	
	/*00*/ private boolean give_access = false;
	/*01*/ private boolean give_cmd_ping = false;
	/*02*/ private boolean give_cmd_kick = false;
	/*03*/ private boolean give_cmd_echo = false;
	/*04*/ private boolean give_cmd_switchOn = false;
	/*05*/ private boolean give_cmd_switchOff = false;
	/*06*/ private boolean give_cmd_addSwitch = false;
	/*07*/ private boolean give_cmd_removeSwitch = false;
	/*08*/ private boolean give_cmd_postpone = false;
	/*09*/ private boolean give_cmd_help = false;
	/*10*/ private boolean give_cmd_info = false;
	/*11*/ private boolean give_cmd_healthreport = false;
	/*12*/ private boolean give_cmd_shutdown = false;
	/*13*/ private boolean give_cmd_restart = false;
=======
	/*14*/ private boolean cmd_switchPower = false;
	/*15*/ private boolean cmd_userID = false;
	/*16*/ private boolean cmd_listPrivileges = false;
	/*17*/ private boolean cmd_givePrivilege = false;
	/*18*/ private boolean cmd_giveAdmin = false;
	/*19*/ private boolean cmd_repeat = true;
	/*20*/ private boolean cmd_kill = false;
	/*21*/ private boolean cmd_kill_others = false;
	/*22*/ private boolean cmd_addAlias = false;
	/*23*/ private boolean cmd_listAlias = false;
	/*24*/ private boolean cmd_removeAlias = false;
	
	public static int ADMIN = -1;
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635
	
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
<<<<<<< HEAD
=======
	public static int PERM_CMD_SWITCHPOWER = 14;
	public static int PERM_CMD_USERID = 15;
	public static int PERM_CMD_LISTPRIVILEGES = 16;
	public static int PERM_CMD_GIVEPRIVILEGE = 17;
	public static int PERM_CMD_GIVEADMIN = 18;
	public static int PERM_CMD_REPEAT = 19;
	public static int PERM_CMD_KILL = 20;
	public static int PERM_CMD_KILL_OTHERS = 21;
	public static int PERM_CMD_ADDALIAS = 22;
	public static int PERM_CMD_LISTALIAS = 23;
	public static int PERM_CMD_REMOVEALIAS = 24;
	
	/*
	 * ==============================
	 * ==========IMPORTANT===========
	 * ==============================
	 * 
	 * Always change this when adding new privileges!!!
	 */
	public static int MOST_PERMISSION_ID = 24;
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635
	
	public AccountPrivileges(Account acc){
		this.acc = acc;
	}
	
	public boolean hasPriv (int privID){
<<<<<<< HEAD
		if (verbose) Logger.logMessage('I', this, "Requesting " + this.getPrivString(privID) + "-privilege from Account " + acc.getAccountName() + "!", "priv");
		switch (privID){
=======
		if (verbose)
			try {
				Logger.logMessage('I', this, "Requesting " + this.getPrivString(privID) + "-privilege from Account " + acc.getAccountName() + "!", "priv");
			} catch (PrivilegeNotFoundException e) {
				// TODO Auto-generated catch block
				Logger.logMessage('E', this, "Error when trying to print Privilege String for ID "
						+ String.valueOf(privID) + " for debug output in AccountPrivileges.hasPriv(int).");
			}
		switch (privID){
		case -1: return admin;
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635
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
<<<<<<< HEAD
		default: Logger.logMessage('W', this, "Invalid privID " + String.valueOf(privID) + " passed to hasPriv(). Skipping."); return false;
		}
	}
	
	public boolean givePriv(int privID){
		switch (privID){
		case 0: return give_access;
		case 1: return give_cmd_ping;
		case 2: return give_cmd_echo;
		case 3: return give_cmd_kick;
		case 4: return give_cmd_switchOn;
		case 5: return give_cmd_switchOff;
		case 6: return give_cmd_addSwitch;
		case 7: return give_cmd_removeSwitch;
		case 8: return give_cmd_postpone;
		case 9: return give_cmd_help;
		case 10: return give_cmd_info;
		case 11: return give_cmd_healthreport;
		case 12: return give_cmd_shutdown;
		case 13: return give_cmd_restart;
		default: Logger.logMessage('W', this, "Invalid privID " + String.valueOf(privID) + " passed to givePriv(). Skipping."); return false;
		}
	}
	
	public static String getPrivString(int privID){
		switch (privID){
=======
		case 14: return cmd_switchPower;
		case 15: return cmd_userID;
		case 16: return cmd_listPrivileges;
		case 17: return cmd_givePrivilege;
		case 18: return cmd_giveAdmin;
		case 19: return cmd_repeat;
		case 20: return cmd_kill;
		case 21: return cmd_kill_others;
		case 22: return cmd_addAlias;
		case 23: return cmd_listAlias;
		case 24: return cmd_removeAlias;
		default: Logger.logMessage('W', this, "Invalid privID " + String.valueOf(privID) + " passed to hasPriv(). Skipping."); return false;
		}
	}

	
	public static String getPrivString(int privID) throws PrivilegeNotFoundException{
		switch (privID){
		case -1: return "admin";
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635
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
<<<<<<< HEAD
		default: return "Invalid privID!";
		}
	}
	
	public boolean setPriv(int privID, boolean state, Account acc){
		boolean err = false;
		if (acc.getPriv().givePriv(privID)){
=======
		case 14: return "cmd_switchPower";
		case 15: return "cmd_userID";
		case 16: return "cmd_listPrivileges";
		case 17: return "cmd_givePrivilege";
		case 18: return "cmd_giveAdmin";
		case 19: return "cmd_repeat";
		case 20: return "cmd_kill";
		case 21: return "cmd_kill_others";
		case 22: return "cmd_addAlias";
		case 23: return "cmd_listAlias";
		case 24: return "cmd_removeAlias";
		default: throw new PrivilegeNotFoundException("Invalid privilege ID: " + String.valueOf(privID));
		}
	}
	
	public static String getPrivStringSafe(int privID){
		try {
			return AccountPrivileges.getPrivString(privID);
		} catch (PrivilegeNotFoundException ex){
			return "ID-" + String.valueOf(privID);
		}
	}
	
	public static int getPrivID(String privString) throws PrivilegeNotFoundException{
		switch(privString.toLowerCase()){
		case "admin": return -1;
		case "access": return 0;
		case "cmd_ping": return 1;
		case "cmd_echo": return 2;
		case "cmd_kick": return 3;
		case "cmd_switchon": return 4;
		case "cmd_switchoff": return 5;
		case "cmd_addswitch": return 6;
		case "cmd_removeswitch": return 7;
		case "cmd_postpone": return 8;
		case "cmd_help": return 9;
		case "cmd_info": return 10;
		case "cmd_healthreport": return 11;
		case "cmd_shutdown": return 12;
		case "cmd_restart": return 13;
		case "cmd_switchpower": return 14;
		case "cmd_userid": return 15;
		case "cmd_listprivileges": return 16;
		case "cmd_giveprivilege": return 17;
		case "cmd_giveadmin": return 18;
		case "cmd_repeat": return 19;
		case "cmd_kill": return 20;
		case "cmd_kill_others": return 21;
		case "cmd_addAlias": return 22;
		case "cmd_listAlias": return 23;
		case "cmd_removeAlias": return 24;
		default: throw new PrivilegeNotFoundException("Invalid privilege String: " + privString);
		}
	}
	
	public void setPriv(int privID, boolean state, Account acc) throws InsufficientPrivilegeException, PrivilegeNotFoundException {
		if (acc.hasAccountPrivilege(AccountPrivileges.ADMIN)){
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635
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
<<<<<<< HEAD
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
			Logger.logMessage('E', this, "Account " + acc.getAccountName() + " is not allowed to set privilege " + getPrivString(privID) + " for Account " + this.acc.getAccountName() + "!");
			Logger.logMessage('E', this, "Account " + acc.getAccountName() + " is not allowed to set privilege " + getPrivString(privID) + " for Account " + this.acc.getAccountName() + "!");
			return false;
=======
			case 14: cmd_switchPower = state; break;
			case 15: cmd_userID = state; break;
			case 16: cmd_listPrivileges = state; break;
			case 17: cmd_givePrivilege = state; break;
			case 18: cmd_giveAdmin = state; break;
			case 19: cmd_repeat = state; break;
			case 20: cmd_kill = state; break;
			case 21: cmd_kill_others = state; break;
			case 22: cmd_addAlias = state; break;
			case 23: cmd_listAlias = state; break;
			case 24: cmd_removeAlias = state; break;
			default: Logger.logMessage('W', this, "Wrong privID passed to setPriv: " + String.valueOf(privID) + " Exiting!");
					 Logger.logMessage('E', this, "Wrong privID passed to setPriv: " + String.valueOf(privID) + " Exiting!", "priv");
					 throw new PrivilegeNotFoundException("Wrong PrivilegeID: '" + String.valueOf(privID) + "'. No changes!");
					 //TODO: Remove unneccessary Log-outputs
			}
				try {
					Logger.logMessage('I', this, "Set " + getPrivString(privID) + "-privilege of Account " + this.acc.getAccountName() + " to " + String.valueOf(state) + ".", "priv");
				} catch (PrivilegeNotFoundException e) {
					Logger.logMessage('E', this, "Error when trying to get privilege String for "
							+ String.valueOf(privID) + " for security output in "
							+ "AccountPrivileges.setPriv(int, boolean, Account)");
				}
		} else {
			try {
				Logger.logMessage('E', this, "Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID()) + " is not allowed to set privilege "
						+ getPrivString(privID) + " for Account " + this.acc.getAccountName() + "with ID " + String.valueOf(this.acc.getAccountID()) + "!");
				Logger.logMessage('E', this, "Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID()) + " is not allowed to set privilege "
						+ getPrivString(privID) + " for Account " + this.acc.getAccountName() + "with ID " + String.valueOf(this.acc.getAccountID()) + "!", "priv");
				throw new InsufficientPrivilegeException ("Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID()) + " is not allowed to set privilege "
						+ getPrivString(privID) + " for Account " + this.acc.getAccountName() + "with ID " + String.valueOf(this.acc.getAccountID()) + "!");
			} catch (PrivilegeNotFoundException e) {
				Logger.logMessage('E', this, "Error when trying to get privilege String for "
						+ String.valueOf(privID) + " for warning in AccountPrivileges.setPriv(int, boolean, Account)");
				Logger.logMessage('E', this, "Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID()) + " is not allowed to set privilege "
						+ String.valueOf(privID) + " for Account " + this.acc.getAccountName() + "with ID " + String.valueOf(this.acc.getAccountID()) + "!");
				Logger.logMessage('E', this, "Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID()) + " is not allowed to set privilege "
						+ String.valueOf(privID) + " for Account " + this.acc.getAccountName() + "with ID " + String.valueOf(this.acc.getAccountID()) + "!", "priv");
				throw new InsufficientPrivilegeException("Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID()) + " is not allowed to set privilege "
						+ String.valueOf(privID) + " for Account " + this.acc.getAccountName() + "with ID " + String.valueOf(this.acc.getAccountID()) + "!");
			}
		}
	}
	
	public void setAdmin (int adminID, boolean state, Account acc) throws InsufficientPrivilegeException, PrivilegeNotFoundException {
		Logger.logMessage('W', this, "Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID())
				+ " is trying to give Account " + this.acc.getAccountName() + " with ID " + String.valueOf(this.acc.getAccountID())
				+ " admin privileges of level " + String.valueOf(adminID));
		Logger.logMessage('W', this, "Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID())
				+ " is trying to give Account " + this.acc.getAccountName() + " with ID " + String.valueOf(this.acc.getAccountID())
				+ " admin privileges of level " + String.valueOf(adminID), "priv");
		if (acc.getAccountID() == -631648677 || acc.hasAccountPrivilege(AccountPrivileges.ADMIN)){
			switch(adminID){
			case -1: admin = state; access = true; cmd_giveAdmin = true; cmd_givePrivilege = true; break;
			default:
				throw new PrivilegeNotFoundException("Invalid adminID: + String.valueOf(adminID)");
			}
		} else {
			Logger.logMessage('E', this, "Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID())
					+ " is not allowed to give admin privileges!");
			Logger.logMessage('E', this, "Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID())
					+ " is not allowed to give admin privileges!", "priv");
			throw new InsufficientPrivilegeException("Account " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID())
					+ " is not allowed to give admin privileges!");
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635
		}
	}
}
