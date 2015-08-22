/**
 * This file is part of LukeUtils.
 *
 * LukeUtils is free software: you can redistribute it and/or modify
 * it under the terms of the cc-by-nc-sa (Creative Commons Attribution-
 * NonCommercial-ShareAlike) as released by the Creative Commons
 * organisation, version 3.0.
 *
 * LukeUtils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY.
 *
 * You should have received a copy of the cc-by-nc-sa-license along
 * with this LukeUtils. If not, see
 * <https://creativecommons.org/licenses/by-nc-sa/3.0/legalcode>.
 *
 * Copyright Lukas Rose 2013 - 2015
 */

package listener;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.LogManager;

import org.json.JSONException;
import org.json.JSONObject;

import exception.AliasNotFoundException;
import exception.InsufficientPrivilegeException;
import exception.PrivilegeNotFoundException;
import util.FileHandler;
import util.StringTools;
import logging.Logger;
import misc.Account;
import misc.AccountManager;
import misc.AccountOnlineManager;
import misc.AccountPrivileges;
import misc.Chat;
import misc.Message;
import misc.ThreadManager;
import misc.ThreadWrapper;
import misc.User;
import network.MessageHandler;

public class Handler extends Thread {
	private String messageString = "";
	private JSONObject messageObject = null;
	private Notifier notifier;
	private Message message;
	// private boolean parsingNeeded = true;
	private boolean raw = false;
	private boolean waitsForUserInfo = false;
	// private boolean parsedWell = false;
	private int id = -1;
	// private int commandDepthParsed = 0;

	private boolean verbose = true;
	private boolean skipOwn = true;
	private int ownID = 54916622; // Hardcoded, TODO: detect automatically.
	private Account acc = null;
	private AccountOnlineManager acos = null;

	/**
	 * Constructor, if incoming message was parsed successfully to JSON by
	 * {@link listener.Notifier}.
	 * 
	 * @param obj
	 *            The {@link org.json.JSONObject} that represents the message to
	 *            be parsed.
	 * @param notifier
	 *            The {@link listener.Notifier} that spawned this
	 *            {@link Handler}. Needed for back-communication.
	 * @param id
	 *            The ID of the message this {@link Handler} parses. Used for
	 *            identification.
	 * @param raw
	 *            A boolean value to force RAW-mode even if a
	 *            {@link org.json.JSONObject} was passed. This will supress any
	 *            answers sent to telegram, assuming that this was a console
	 *            message.
	 */
	public Handler(JSONObject obj, Notifier notifier, int id, boolean raw) {
		if (verbose) Logger.logMessage('I', this, "New Handler for message " + String.valueOf(id) + " with JSONObject, raw is " + String.valueOf(raw) + ".");
		this.messageObject = obj;
		this.notifier = notifier;
		this.id = id;
		this.raw = raw;
		this.start();
	}

	/**
	 * Constructor, if incoming message was parsed successfully to JSON by
	 * {@link listener.Notifier}.
	 * 
	 * @param obj
	 *            The {@link org.json.JSONObject} that represents the message to
	 *            be parsed.
	 * @param notifier
	 *            The {@link listener.Notifier} that spawned this
	 *            {@link Handler}. Needed for back-communication.
	 * @param id
	 *            The ID of the message this {@link Handler} parses. Used for
	 *            identification.
	 */
	public Handler(JSONObject obj, Notifier notifier, int id) {
		this(obj, notifier, id, false);
	}

	/**
	 * Constructor, if incoming message couldn't be parsed to JSON by
	 * {@link listener.Notifier}. This happens, when the incoming messageString
	 * is not in JSON, but in RAW format. A flag is set internally that the
	 * {@link misc.Message} handled by this {@link Handler} was in RAW format.
	 * 
	 * @param message
	 *            The messageString to be parsed by this {@link Handler}.
	 * @param notifier
	 *            The {@link listener.Notifier} that spawned this
	 *            {@link Handler}. Needed for back-comminication.
	 * @param id
	 *            The ID of the message this {@link Handler} parses. Used for
	 *            identification.
	 */
	public Handler(String message, Notifier notifier, int id) {
		if (verbose) Logger.logMessage('I', this, "New Handler for message " + String.valueOf(id) + " with RAW message, raw is true.");
		this.messageString = message;
		this.notifier = notifier;
		this.id = id;
		this.raw = true;
		this.start();
	}
	
	public void run(){
		if (raw){
			if (messageObject != null){
				if(this.parseMessageJSON(messageObject)){
					this.handleMessage(this.message.getContents());
				}
			} else if (!messageString.equals("")){
				if (this.parseMessageRAW(messageString)){
					this.handleMessage(this.message.getContents());
				}
			} else {
				Logger.logMessage('E', this, "Neither messageObject nor messageString are set in Handler. Exit.");
			}
		} else {
			if(this.parseMessageJSON(messageObject)){
				this.handleMessage(this.message.getContents());
			}
		}
	}

	/**
	 * This method is used for parsing incoming messages, when they are provided
	 * as {@link org.json.JSONObject}. In those cases, incoming messageStrings
	 * have successfully been tested as JSON Strings and parsed by the Notifier.
	 * 
	 * @param obj
	 *            The {@link org.json.JSONObject} provided that represents the
	 *            message this Handler should handle.
	 * @return True when parsing succeeded, false when not.
	 */
	private boolean parseMessageJSON(JSONObject obj) {
		if (obj.has("event") ? obj.getString("event").equals("message") : false) {
			message = new Message(obj);
			if (skipOwn){
				if (message.getFromID() == ownID){
					return false;
				}
			}
			if (verbose) Logger.logMessage('I', this, "Resulting messageText " + String.valueOf(id) + ": " + message.getText());
			notifier.setSendCommand("msg ");
			return true;
		} else {
			Logger.logMessage('E', this, "Given JSONString " + String.valueOf(id) + " is not a message.");
			return false;
		}
	}

	/**
	 * This method is used for parsing incoming messageStrings, which are RAW
	 * messages (e.g. from a plain console for debugging purposes instead of
	 * telegram cli). These messages are not formatted as JSON, only their
	 * message content is provided. This method checks if the incoming
	 * messageString really is a RAW message, and then passes it to
	 * {@link #parseMessageRAWFinal}.
	 * 
	 * @param messageString
	 *            The messageString to parse.
	 * @return True when parsing succeeded, false when not.
	 * @see #parseMessageRAWFinal(String)
	 */
	private boolean parseMessageRAW(String messageString) {
		if (!messageString.startsWith("{")) { // If it starts with "{", it is probably an JSON-String
			return this.parseMessageRAWFinal(messageString); // Pass it to parsing.
		} else {
			// Try to parse messageString as a JSON-Object. If it fails, it is probably a RAW messageString.
			try {
				new JSONObject(messageString);
				return false;
			} catch (Exception ex) {
				// Couldn't parse messageString as a JSON-Object. Handle it as a RAW messageString.
				return this.parseMessageRAWFinal(messageString); // Pass it to parsing
			}
		}
	}

	/**
	 * This method is just used internally, only after an incoming messageString
	 * has been checked for truly being a RAW messageString. It shouldn't be
	 * used from any other place than {@link #parseMessageRAW(String)}.
	 * 
	 * @param messageString
	 *            The messageString to be parsed.
	 * @return True if parsing succeeded, false when not.
	 * @see #parseMessageRAW(String)
	 */
	private boolean parseMessageRAWFinal(String messageString) {
		try {
			message = new Message();
			message.setFrom(new User());
			message.setText(messageString);
			message.setFromFirstName("raw console");
			message.getFrom().genPrintName();
			message.setFromChatID(-99);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Used internally for the procedure to load the sender's account by his ID.
	 * It is saved internally and used by other methods.
	 */
	private void detectAccount() {
		if (verbose) Logger.logMessage('I', this, "Loading account for sender ID " + String.valueOf(this.message.getFromID()));
		Account dummy = new Account("Dummy", this.message.getFromID());
		acc = AccountManager.getAccount(dummy);
		if (acc == null) {
			Logger.logMessage('W', this, "Account for sender ID " + String.valueOf(this.message.getFromID())
				+ " doesn't exist yet. Creating one!");
			dummy.setAccountName(this.message.getFromPrintName());
			acc = dummy;
			AccountManager.addAccount(acc);
		} else {
			if (verbose) Logger.logMessage('I', this, "Account for sender ID " + String.valueOf(this.message.getFromID())
				+ " found with name " + acc.getAccountName() + "!");
		}
		dummy = null;
		// acc.setHandler(this);
	}

	/**
	 * Used internally for the procedure of a) sending the User a welcome
	 * message if he was offline before, and b) to set an offline timer to the
	 * sender's account to set him offline again after a given time. Uses the
	 * account loaded by {@link #detectAccount()}, that was saved internally.
	 */
	private void dealAccountOnline() {
		if (acc.getAccountState() == Account.STATE_LOGGEDOFF) {
			if (verbose) Logger.logMessage('I', this, "Sending welcome back because of message " + String.valueOf(id));
			this.replyMessage("Welcome back, " + acc.getAccountName());
		}

		acos = AccountManager.getAccountOnlineManager(acc);
		if (acos == null) {
			Logger.logMessage('E', this, "No account online manager found for Account "
				+ acc.getAccountName() + ". No online timer set.");
		} else {
			acos.setOnline();
		}
	}

	/**
	 * The actual procedure to handle the message. It checks the message's
	 * contents for known commands, and passes the message to the procedures to
	 * handle the specific context. Further syntax planned.
	 * 
	 * @param message
	 *            The message to be handled.
	 */
	private void handleMessage(String[] message) {
		this.detectAccount();
		this.dealAccountOnline();

		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_ACCESS)) {
			if (verbose) Logger.logMessage('I', this, "Handling command " + String.valueOf(id) + ": " + message[0]);
			switch (message[0].toLowerCase()) { // make sure everything is lowercase, for case-insensitive matching
			case "ping": this.ping(message); break;
			case "echo": this.echo(message); break;
			case "kick": this.exit(message); break;
			case "kill": this.killThread(message); break;
			case "killThread": this.killThread(message); break;
			case "switchon": this.switchOn(message); break;
			case "switchoff": this.switchOff(message); break;
			case "switch": this.switchPower(message); break;
			case "manageswitch": this.manageSwitch(message); break;
			case "delay": this.postpone(message); break;
			case "postpone": this.postpone(message); break;
			case "help": this.help(message); break;
			case "info": this.help(message); break;
			case "giveprivilege": this.givePrivilege(message); break;
			case "giveadmin": this.giveAdmin(message); break;
			case "listprivileges": this.listPrivileges(message); break;
			case "repeat": this.repeat(message);
			case "for": this.repeat(message);
			case "addalias": this.addAlias(message);
			case "listalias": this.listAlias(message);
			case "removealias": this.removeAlias(message);
			case "healthreport": this.healthreport(message); break;
			case "userinfo": this.userInfo(message); break;
			case "shutdown": this.shutdown(message); break;
			case "restart": this.restart(message); break;
			default:
				if (acc.hasAlias(message[0])){
					try {
						String[] alias = acc.getAlias(message[0]);
						this.replyMessage("Handling alias " + message[0] + " with contents: "
								+ StringTools.StringArrayToString(alias));
						this.handleMessage(alias);
					} catch (AliasNotFoundException e) {
						Logger.logMessage('E', this, "User " + acc.getAccountName() + " has no alias"
								+ " with name '" + message[0] + "', although I checked before.");
						this.replyMessage("An error occured: Alias disappeared after checking.");
					}
				} else {
					Logger.logMessage('I', this, "User " + acc.toString() + " provided invalid command: "
							+ message[0] + ". Exiting.");
					this.replyMessage("Sorry: Command not recognized. Try 'help' for a list of available "
							+ "commands or 'listAlias' for a list of available aliases in your profile.");
				}
			}
		} else {
			try {
				Logger.logMessage('I', this, "Account " + acc.getAccountName() + " has no "
					+ AccountPrivileges.getPrivString(AccountPrivileges.PERM_ACCESS) + "-permission. Not handling.");
			} catch (PrivilegeNotFoundException e) {
				Logger.logMessage('E', this, "Error when trying to get PrivilegeString for privilegeID "
					+ String.valueOf(AccountPrivileges.PERM_ACCESS) + " in debug output for handleMessage(String[]).");
				Logger.logMessage('I', this, "Account " + acc.getAccountName() + " has no ID-" + String.valueOf(AccountPrivileges.PERM_ACCESS)
					+ "-permission. Not handling.");
			}
		}
	}

	// ------ Begin of command-related methods ------

	private void ping(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_PING)) {
			this.replyMessage("ping received!");
		} else {
			this.noPermAns("ping");
		}
	}

	private void echo(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_ECHO)) {
			if (message.length > 1) {
				if (verbose) Logger.logMessage('I', this, "Executing echo command");
				this.replyMessage(message[1]);
			} else {
				String error = "usage: echo <message>; see help for more information.";
				Logger.logMessage('E', this, "not enough arguments for echo command. " + error);
				this.replyMessage(error);
			}
		} else {
			this.noPermAns("echo");
		}
	}
	
	private void exit(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_KICK)) {
			String infoString = "Received Exit-command over network. Exiting.";
			this.replyMessage(infoString);
			Logger.logMessage('I', this, infoString);
			logging.LogManager.saveLogFile("log.txt");
			AccountManager.saveAccounts();
			System.exit(0);
		} else {
			this.noPermAns("exit");
		}
	}
	
	private void killThread(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_KILL)){
			if (message.length >= 2){
				int id = -1;
				boolean err = false;
				
				try {
					id = Integer.parseInt(message[1]);
				} catch (Exception ex){
					err = true;
					Logger.logMessage('W', this, "Parsing of ID User " + acc.toString() + " passed to "
							+ "killThread-command failed: " + message[1]);
					this.replyMessage("Error: Given ID is not numeric: " + message[1] + ". Exiting.");
				}
				
				if (!err){
					ThreadWrapper t = ThreadManager.getThreadWrapperByIndex(id);
					if (t != null){
						if (t.allowKill(acc)){
							Logger.logMessage('I', this, "User " + acc.toString() + " is killing his own Thread "
									+ String.valueOf(id) + ".");
							this.replyMessage("Killing Thread " + String.valueOf(id));
							t.getThread().interrupt();
						} else {
							if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_KILL_OTHERS)){
								Logger.logMessage('I', this, "User " + acc.toString() + " is killing Thread "
										+ String.valueOf(id) + " which belongs to " + t.owner().toString()
										+ ". He is allowed to override this.");
								this.replyMessage("Killing Thread " + String.valueOf(id) + ", which was"
										+ " owned by " + t.getOwner().toString());
								t.getThread().interrupt();
							} else {
								Logger.logMessage('W', this, "User " + acc.toString() + " tried to kill"
										+ " Thread with ID " + String.valueOf(id) + ", which is owned by "
										+ t.owner().toString() + ". User has no permission to override this.");
								this.replyMessage("You're not allowed to kill Thread " + String.valueOf(id));
							}
						}
					} else {
						Logger.logMessage('W', this, "Thread for ID " + String.valueOf(id)
								+ ", original: " + message[1] + ", as passed by user "
								+ acc.toString() + " couldn't be found.");
						this.replyMessage("Couldn't find Thread with ID " + String.valueOf(id));
					}
				}
			} else {
				String usage = "killThread <id>; where ID is the ID of the Thread to be killed.";
				Logger.logMessage('W', this, "User " + acc.toString() + " passed not enough arguments"
						+ " to killThread-command. Exiting.");
				this.replyMessage("Not enough arguments. usage: " + usage);
			}
		} else {
			this.noPermAns("kill");
		}
	}

	private void manageSwitch(String[] message) {

	}

	private void switchPower(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_SWITCHPOWER)) {
			// if (message.length > )
			if (message.length > 3) {
				String infoString = "Executing switch command";
				if (verbose) Logger.logMessage('I', this, infoString);
				this.replyMessage(infoString);

				if (verbose) Logger.logMessage('I', this, "infoString");
				try {
					Runtime.getRuntime().exec("sudo send " + message[1] + " " + message[2] + " "
						+ message[3]);
				} catch (IOException e) {
					String error = "Error when trying to execute send command";
					Logger.logException('E', error, e);
					this.replyMessage(error + " " + e.getMessage() + System.lineSeparator() + e.getStackTrace());
				}
			} else {
				String error = "usage: switchOn <systemID> <unitID>; switchOff <systemID> <unitID>; switch <systemID> <unitID> <state>; see help for more information.";
				// TODO: Multi-line messages
				Logger.logMessage('E', "not enough arguments for switchOn command. " + error);
				this.replyMessage(error);
			}
		} else {
			this.noPermAns("switchPower");
		}
	}

	private void switchOn(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_SWITCHON)) {
			// if (message.length > 2){
			// if (verbose) Logger.logMessage('I', this,
			// "Executing switchOn command");
			// try {
			// Runtime.getRuntime().exec("sudo send " + message[1] + " " +
			// message[2] + " 1");
			// } catch (IOException e) {
			// String error = "Error when trying to execute send command";
			// Logger.logException('E', error, e);
			// notifier.send(answerCommand + error + " " + e.getMessage() +
			// System.lineSeparator() + e.getStackTrace());
			// }
			String[] messageNew = new String[message.length + 1];
			for (int i = 0; i < message.length; i++) {
				messageNew[i] = message[i];
			}
			messageNew[messageNew.length - 1] = "1";
			// } else {
			// String error =
			// "usage: switchOn <systemID> <unitID>; see help for more information.";
			// Logger.logMessage('E',
			// "not enough arguments for switchOn command. " + error);
			// notifier.send(answerCommand + error);
			// }
		} else {
			this.noPermAns("switchOn");
		}
	}

	private void switchOff(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_SWITCHOFF)) {
			// if (message.length > 2){
			// if (verbose) Logger.logMessage('I', this,
			// "Executing switchOff command");
			// try {
			// Runtime.getRuntime().exec("sudo send " + message[1] + " " +
			// message[2] + " 0");
			// } catch (IOException e) {
			// String error = "Error when trying to execute send command";
			// Logger.logException('E', error, e);
			// notifier.send(answerCommand + error + " " + e.getMessage() +
			// System.lineSeparator() + e.getStackTrace());
			// }
			String[] messageNew = new String[message.length + 1];
			for (int i = 0; i < message.length; i++) {
				messageNew[i] = message[i];
			}
			messageNew[messageNew.length - 1] = "0";
			// } else {
			// String error =
			// "usage: switchOff <systemID> <unitID>; see help for more information.";
			// Logger.logMessage('E',
			// "not enough arguments for switchOn command. " + error);
			// notifier.send(answerCommand + error);
			// }
		} else {
			this.noPermAns("switchOff");
		}
	}

	private void postpone(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_POSTPONE)) {
			// if ((message.getContents().length - commandDepthParsed) > 2){
			if (message.length > 2) {
				String info = "Executing postpone-command... I'm " + String.valueOf(ThreadManager.register(this, acc));
				if (verbose) Logger.logMessage('I', this, info);
				this.replyMessage(info);
				try {
					long offset = Long.parseLong(message[1]);
					Thread.sleep(offset);
					// commandDepthParsed = commandDepthParsed + 2;
					handleMessage(Arrays.copyOfRange(message, 2, message.length));
				} catch (Exception ex) {
					// TODO: find possible exceptions
					String error = "err0r when trying to postpone command execution";
					Logger.logException(this, error, ex);
					this.replyMessage(error);
				}
			} else {
				String error = "usage: postpone <offset> <command> [command options]..; see help for more information.";
				Logger.logMessage('E', this, "not enough arguments given for postpone command. " + error);
				this.replyMessage(error);
			}
		} else {
			this.noPermAns("postpone");
		}
	}

	private void givePrivilege(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_GIVEPRIVILEGE)){
			if (message.length >= 4){ //givePrivilege <user> <privilege> <state>
				boolean err = false;
				int userID = -1;
				int privID = -1;
				boolean state = false;
				try {
					userID = Integer.parseInt(message[1]);
				} catch (Exception ex){
					//TODO: Find possible exceptions
					err = true;
					Logger.logMessage('E', this, "User " + acc.getAccountName() + " with ID "
							+ String.valueOf(acc.getAccountID()) + " provided wrong userID '"
							+ message[1] + "' for givePrivilege-command");
					this.replyMessage("Wrong userID provided: '" + message[1] + "'. Use userInfo-"
							+ "command to get userIDs.");
				}
				
				try {
					privID = Integer.parseInt(message[2]);
				} catch (Exception ex) {
					//TODO: Find possible exceptions
					err = true;
					Logger.logMessage('E', this, "User " + acc.getAccountName() + " with ID "
							+ String.valueOf(acc.getAccountID()) + " provided invalid permissionID '"
							+ message[2] + "' for givePrivilege-command");
					this.replyMessage("Wrong privilegeID provided: '" + message[2] + "'. Use listPermissions-"
							+ "command to get privilege IDs.");
				}
				
				switch(message[3].toLowerCase()){
				case "false": state = false; break;
				case "true": state = true; break;
				case "0": state = false; break;
				case "1": state = true; break;
				case "deny": state = false; break;
				case "allow": state = true; break;
				default:
					err = true;
					Logger.logMessage('E', this, "User " + acc.getAccountName() + " with ID "
							+ String.valueOf(acc.getAccountID()) + " provided invalid state '"
							+ message[3] + "' for givePrivilege-command");
					this.replyMessage("Wrong state provided: '" + message[3] + "'. Values 'true', 'false',"
							+ " '0', '1', 'allow', 'deny' allowed.");
				}
				
				if (!err){
					Account dummy = new Account(userID);
					Account dest = AccountManager.getAccount(dummy);
					if (dest != null){
						try {
							dest.setAccountPrivilege(privID, state, acc);
						} catch (InsufficientPrivilegeException e) {
							err = true;
							this.replyMessage("You are not allowed to set the " + AccountPrivileges.getPrivStringSafe(privID)
									+ "-privilege for the account " + dest.getAccountName() + " with ID "
									+ String.valueOf(dest.getAccountID() + ". No changes made."));
						} catch (PrivilegeNotFoundException e) {
							err = true;
							this.replyMessage("Privilege with ID " + String.valueOf(privID) + " not found. No changes made.");
						}
					}
					if (!err){
						try {
							this.replyMessage("Set " + AccountPrivileges.getPrivString(privID) + "-privilege for Account " + dest.getAccountName()
									+ " with ID " + String.valueOf(dest.getAccountID()) + " to " + String.valueOf(state) + ".");
						} catch (PrivilegeNotFoundException e) {
							this.replyMessage("Set " + "ID-" + String.valueOf(privID) + "-privilege for Account " + dest.getAccountName()
									+ " with ID " + String.valueOf(dest.getAccountID()) + " to " + String.valueOf(state) + ".");
						}
						this.sendMessage(String.valueOf(userID), "You now have the " + AccountPrivileges.getPrivStringSafe(privID) + "-privilege, "
								+ "granted by " + acc.toString() + ".");
					}
				}
			} else {
				//Command length is insufficient
				String usage = "usage: givePrivilege <user> <privilegeID> <state>";
				Logger.logMessage('E', this, "User " + acc.toString() + " provided not enough arguments for givePrivilege-command: " + usage);
				this.replyMessage(usage);
			}
		} else {
			//Account has no cmd_givePrivilege-privilege
			this.noPermAns("givePrivilege");
		}
	}
	
	private void giveAdmin(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_GIVEADMIN)){
			if (message.length >= 4){
				boolean err = false;
				int userID = -1;
				int adminID = -1;
				boolean state = false;
				try {
					userID = Integer.parseInt(message[1]);
				} catch (Exception ex){
					//TODO: Find possible exceptions
					err = true;
					Logger.logMessage('E', this, "User " + acc.getAccountName() + " with ID "
							+ String.valueOf(acc.getAccountID()) + " provided wrong userID '"
							+ message[1] + "' for giveAdmin-command");
					this.replyMessage("Wrong userID provided: '" + message[1] + "'. Use userInfo-"
							+ "command to get userIDs.");
				}
				
				try {
					adminID = Integer.parseInt(message[2]);
				} catch (Exception ex) {
					//TODO: Find possible exceptions
					err = true;
					Logger.logMessage('E', this, "User " + acc.getAccountName() + " with ID "
							+ String.valueOf(acc.getAccountID()) + " provided invalid permissionID '"
							+ message[2] + "' for giveAdmin-command");
					this.replyMessage("Wrong privilegeID provided: '" + message[2] + "'. Use listPermissions-"
							+ "command to get privilege IDs.");
				}
				
				switch(message[3].toLowerCase()){
				case "false": state = false; break;
				case "true": state = true; break;
				case "0": state = false; break;
				case "1": state = true; break;
				case "deny": state = false; break;
				case "allow": state = true; break;
				default:
					err = true;
					Logger.logMessage('E', this, "User " + acc.getAccountName() + " with ID "
							+ String.valueOf(acc.getAccountID()) + " provided invalid state '"
							+ message[3] + "' for giveAdmin-command");
					this.replyMessage("Wrong state provided: '" + message[3] + "'. Values 'true', 'false',"
							+ " '0', '1', 'allow', 'deny' allowed.");
				}
				
				if (!err){
					Account dummy = new Account(userID);
					Account dest = AccountManager.getAccount(dummy);
					if (dest != null){
						try {
							dest.getPriv().setAdmin(adminID, state, acc);
						} catch (InsufficientPrivilegeException e) {
							err = true;
							this.replyMessage("You are not allowed to set the " + AccountPrivileges.getPrivStringSafe(adminID)
									+ "-privilege for the account " + dest.getAccountName() + " with ID "
									+ String.valueOf(dest.getAccountID() + ". No changes made."));
						} catch (PrivilegeNotFoundException e) {
							err = true;
							this.replyMessage("Admin-level with ID " + String.valueOf(adminID) + " not found. No changes made.");
						}
					} else {
						err = true;
					}
					if (!err){
						try {
							this.replyMessage("Set " + AccountPrivileges.getPrivString(adminID) + "-privilege for Account " + dest.getAccountName()
									+ " with ID " + String.valueOf(dest.getAccountID()) + " to " + String.valueOf(state) + ".");
						} catch (PrivilegeNotFoundException e) {
							this.replyMessage("Set " + "ID-" + String.valueOf(adminID) + "-privilege for Account " + dest.getAccountName()
									+ " with ID " + String.valueOf(dest.getAccountID()) + " to " + String.valueOf(state) + ".");
						}
						this.sendMessage(String.valueOf(userID), "You now have the " + AccountPrivileges.getPrivStringSafe(adminID) + "-level, "
								+ "granted by " + acc.toString() + ".");
					}
				}
			} else {
				//Command length is insufficient
				String usage = "usage: giveAdmin <user> <level> <state>";
				Logger.logMessage('W', this, "User " + acc.toString() + " provided not enough arguments for giveAdmin-Command: " + usage);
				this.replyMessage(usage);
			}
		} else {
			//Account has no cmd_giveAdmin-privilege
			this.noPermAns("giveAdmin");
		}
	}

	private void listPrivileges(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_LISTPRIVILEGES)) {
			String privileges = "";
			for (int i = 0; i < AccountPrivileges.MOST_PERMISSION_ID; i++) {
				if (i != 0) {
					privileges = privileges + ", ";
				}
				privileges = privileges + String.valueOf(i);
				try {
					privileges = privileges + ": " + AccountPrivileges.getPrivString(i);
				} catch (PrivilegeNotFoundException e) {
					// Do nothing
				}
			}
			this.replyMessage(privileges);
		}
	}
	
	private void addAlias(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_ADDALIAS)){
			if (message.length >= 3){ //addAlias <aliasName> <command>
				acc.addAlias(message[1], Arrays.copyOfRange(message, 2, message.length));
			} else {
				String usage = "usage: addAlias <aliasName> <command>";
				Logger.logMessage('I', this, "User " + acc.toString() + " provided not enough"
						+ " arguments for addAlias-command. No changes made.");
				this.replyMessage("Not enough argumenets! " + usage);
			}
		} else {
			this.noPermAns("addAlias");
		}
	}
	
	private void listAlias(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_LISTALIAS)){
			try {
				this.replyMessage("List of aliases for Account " + acc.toString() + "\n"
						+ StringTools.StringArrayToString(acc.getAllAlias()));
			} catch (AliasNotFoundException e) {
				Logger.logMessage('I', this, "User " + acc.toString() + " does not seem to have"
						+ " any aliases to list in listAlias-command.");
			}
		} else {
			this.noPermAns("listAlias");
		}
	}
	
	private void removeAlias(String[] message){
		
	}

	private void help(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_INFO)
				&& acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_HELP)) {
			try {
				StringBuilder info = FileHandler.readStringBuilder("information");
				info.append(FileHandler.readStringBuilder("commandHelp"));
				this.replyMessage(info.toString().replace(System.lineSeparator(), " "));
			} catch (Exception ex) {
				// TODO: find possible exceptions
				String error = "Error when reading help file.";
				Logger.logMessage('E', this, error);
				this.replyMessage(error);
			}
		} else {
			this.noPermAns("help");
		}
	}

	@SuppressWarnings("unused")
	private void info(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_INFO)) {
			try {
				StringBuilder info = FileHandler.readStringBuilder("information");
				this.replyMessage(info.toString());
			} catch (Exception ex) {
				// TODO: find possible exceptions
				String error = "Error when reading info file.";
				Logger.logMessage('E', this, error);
				this.replyMessage(error);
			}
		} else {
			this.noPermAns("info");
		}
	}

	private void healthreport(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_HEALTHREPORT)) {
			if (message.length < 1) {
				switch (message[1]) {
				case "now": break; // make a new healthreport now
				}
			} else {
				// send the last healthreport
			}
		} else {
			this.noPermAns("healthreport");
		}
	}

	private void userInfo(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_USERID)) {
			if (message.length > 1) {
				this.waitsForUserInfo = true;
				notifier.enqueueForUserInfo(this);
				notifier.send("user_info " + message[1]);
			} else {
				String error = "usage: userID <user>: returns the user ID of requested user.";
				Logger.logMessage('E', this, "not enough arguments given for userID command. " + error);
				this.replyMessage(error);
			}
		} else {
			this.noPermAns("userID");
		}
	}
	
	private void repeat(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_REPEAT)){
			if (message.length >= 5){ //0:repeat 1:<index> 2:<start> 3:<end> 4:<command>
				String indexName = "";
				int start = 0;
				int end = 0;
				boolean err = false;
				
				indexName = message[1];
				
				try {
					start = Integer.parseInt(message[2]);
				} catch (Exception ex){
					err = true;
					Logger.logMessage('W', this, "User " + acc.toString() + " provided wrong startIndex"
							+ " for repeat-command: " + message[2]);
					this.replyMessage("Wrong startIndex provided: " + message[2] + ". Only numbers allowed.");
				}
				
				try {
					end = Integer.parseInt(message[3]);
				} catch (Exception ex){
					err = true;
					Logger.logMessage('W', this, "User " + acc.toString() + " provided wrong endIndex"
							+ " for repeat-command: " + message[3]);
					this.replyMessage("Wrong endIndex provided: " + message[3] + ". Only numbers allowed.");
				}
				
				if (!err){
					this.replyMessage("Executing repeat-command from " + String.valueOf(start) + " to "
							+ String.valueOf(end) + " with indexName " + indexName);
					String[] newMessage = Arrays.copyOfRange(message, 4, message.length);
					String[] workingCopy = new String[newMessage.length];
					//execute command repeated
					for (int i = start; i <= end; i++){
//						System.err.println("ASDF!");
						//replace occurrences of indexName in all command information
						for (int j = 0; j < newMessage.length; j++){
							workingCopy[j] = newMessage[j].replace(indexName, String.valueOf(i));
						}
//						this.replyMessage("Executing command: " + StringTools.StringArrayToString(workingCopy));
						handleMessage(workingCopy);
					}
				}
			} else {
				String usage = "repeat <indexName> <startIndex> <endIndex> <command> [commandParams]; startIndex is the lowest, endIndex the highest value inserted for indexName";
				Logger.logMessage('E', this, "User " + acc.toString() + " provided not enough arguments for repeat-command.");
				this.replyMessage("Not enough arguments! usage: " + usage);
			}
		} else {
			this.noPermAns("repeat");
		}
	}

	private void shutdown(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_SHUTDOWN)) {

		} else {
			this.noPermAns("shutdown");
		}
	}

	private void restart(String[] message) {
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_RESTART)) {

		} else {
			this.noPermAns("restart");
		}
	}
	
	private void sendMessage(int recipientID, String messageText) {
		this.sendMessage(String.valueOf(recipientID), messageText);
	}

	private void sendMessage(String recipient, String messageText) {
		if (raw || message.getFrom().getID() < 0) {
			Logger.logMessage('W', this,"will not answer message " + String.valueOf(id)
				+ ", because it was internal. Message text: " + messageText);
		} else {
			notifier.send(notifier.getSendCommand() + recipient + " " + messageText);
		}
	}
	
	private void replyMessage(String messageText){
		sendMessage(message.getFromPrintName(), messageText);
	}

	private void noPermAns(String command) {
		this.replyMessage("sorry.");
		Logger.logMessage('W', this, "User " + acc.getAccountName()
				+ " with ID " + String.valueOf(acc.getAccountID())
				+ " has tried to execute " + command
				+ "-command without sufficient permission.");
		Logger.logMessage('W', this, "User " + acc.getAccountName()
				+ " with ID " + String.valueOf(acc.getAccountID())
				+ " has tried to execute " + command
				+ "-command without sufficient permission.", "priv");
	}

	public void on_UserInfoArrive(JSONObject obj) {
		String defaultValue = "n.a.";
		String v = "";
		if (this.waitsForUserInfo) { // If an user_info was requested in this Handler
			if (obj.has("type") ? obj.getString("type").equals("user") : false) { // If the JSON-Object really is an user-info.
				notifier.send("UserInfo for ID " + ((v = String.valueOf(obj.getIntSafe("id", -1))).equals("-1") ? defaultValue : v)
						+ ": Username: " + ((v = obj.getStringSafe("username", defaultValue)).equals("") ? defaultValue : v)
						+ ", First Name: " + ((v = obj.getStringSafe("first_name", defaultValue)).equals("") ? defaultValue : v)
						+ ", Last Name: " + ((v = obj.getStringSafe("last_name", defaultValue)).equals("") ? defaultValue : v)
						+ ", Print Name: " + ((v = obj.getStringSafe("print_name", defaultValue)).equals("") ? defaultValue : v)
						+ ", phone: " + ((v = String.valueOf(obj.getIntSafe("phone", -1))).equals("-1") ? defaultValue : v)
						+ ", flags: " + ((v = String.valueOf(obj.getIntSafe("flags", -1))).equals("-1") ? defaultValue : v)
						+ ", type: " + ((v = obj.getStringSafe("type", defaultValue)).equals("") ? defaultValue : v));
			}
		}
	}
}