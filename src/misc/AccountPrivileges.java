package misc;

import logging.Logger;

public class AccountPrivileges {
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
	
	public AccountPrivileges(){
		//No constructor needed
	}
	
	public boolean hasPriv (int privID){
		switch (privID){
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
		default: Logger.logMessage('W', this, "Invalid privID passed to hasPriv(). Skipping."); return false;
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
		default: Logger.logMessage('W', this, "Invalid privID passed to givePriv(). Skipping."); return false;
		}
	}
}
