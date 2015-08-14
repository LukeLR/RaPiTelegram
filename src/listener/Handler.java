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

import util.FileHandler;
import logging.Logger;
import misc.Account;
import misc.AccountManager;
import misc.AccountOnlineManager;
import misc.AccountPrivileges;
import misc.Chat;
import misc.Message;
import misc.User;
import network.MessageHandler;

public class Handler extends Thread{
	private String messageString;
	private Notifier notifier;
	private Message message;
	private boolean parsingNeeded = true;
	private boolean raw = false;
	private boolean parsedWell = false;
	private int id = -1;
//	private int commandDepthParsed = 0;
	
	private boolean verbose = true;
	private boolean skipOwn = true;
	private int ownID = 54916622;
	private Account acc = null;
	private AccountOnlineManager acos = null;
	
	public Handler(String messageString, Notifier notifier, int id){
		this(messageString, false, notifier, id);
	}
	
	public Handler(String messageString, boolean raw, Notifier notifier, int id){
		if (raw){
			if (verbose) Logger.logMessage('I', this, "New MessageHandler " + String.valueOf(id) + " created with messageString. Content is raw.");
		} else {
			if (verbose) Logger.logMessage('I', this, "New MessageHandler " + String.valueOf(id) + " created with messageString. Content is JSON");
		}
		
		this.messageString = messageString;
		this.notifier = notifier;
		parsingNeeded = true;
		this.raw = raw;
		this.id = id;
		this.start();
	}
	
	public Handler(Message message, Notifier notifier, int id){
		if (verbose) Logger.logMessage('I', this, "New MessageHandler created with message object. ID: " + String.valueOf(id));
		this.message = message;
		this.notifier = notifier;
		parsingNeeded = false;
		parsedWell = true;
		this.id = id;
		this.start();
	}
	
	public void run(){
		if (parsingNeeded){
			parseMessage();
		}
		if (parsedWell){
			handleMessage(message.getContents());	
		} else {
			Logger.logMessage('W', this, "Message " + String.valueOf(id) + " was not parsed well. Exiting.");
		}
	}
	
	private void parseMessage(){
		if (verbose) Logger.logMessage('I', this, "Parsing message " + String.valueOf(id) + "...");
		if (raw){
			message = new Message();
			message.setFrom(new User());
			message.setText(messageString);
			message.setFromFirstName("raw console");
			message.getFrom().genPrintName();
			message.setFromChatID(-99);
			parsedWell = true;
		} else {
//			String[] contents = messageString.trim().split("\\s");
//			if (contents[0].equals("ANSWER")){
//				Logger.logMessage('W', this, "Message is ANSWER xxx, skipping parsing & handling");
//				parsedWell = false;
//			if (JSONUtils.mayBeJSON(messageString)){
//				Logger.logMessage('W', this, "Message is no valid JSON, skipping parsing & handling");
//				parsedWell = false;
//			} else {
			try {
				JSONObject obj = new JSONObject (messageString);
				
				if(obj.getString("event").equals("message")){
					message = new Message(obj);
					if (verbose) Logger.logMessage('I', this, "Resulting messageText " + String.valueOf(id)+ ": " + message.getText());
					parsedWell = true;
					notifier.setAnswerCommand("msg " + message.getFromPrintName() + " ");
				} else {
					Logger.logMessage('E', this, "Given JSONString " + String.valueOf(id) + " is not a message. JSONString:\n" + messageString);
					parsedWell = false;
				}
			} catch (JSONException ex){
				Logger.logMessage('E', this, "Parsing of JSON for ID " + String.valueOf(id) + " failed!");
//				ex.printStackTrace();
			} catch (Exception ex){
				Logger.logMessage('E', this, "Parsing of JSON for ID " + String.valueOf(id) + " failed in a general exception!");
//				ex.printStackTrace();
			}
		}
		
		if (skipOwn && parsedWell){
			if (message.getFrom().getID() == ownID){
				if (verbose) Logger.logMessage('I', this, "Message " + String.valueOf(id) + " is mine. Skipping.");
				parsedWell = false;
			}
		}
	}
	
	private void handleMessage(String[] message){
		if (verbose) Logger.logMessage('I', this, "Loading account for sender ID " + String.valueOf(this.message.getFromID()));
		Account dummy = new Account("Dummy", this.message.getFromID());
		acc = AccountManager.getAccount(dummy);
		if (acc == null){
			Logger.logMessage('W', this, "Account for sender ID " + String.valueOf(this.message.getFromID()) + " doesn't exist yet. Creating one!");
			dummy.setAccountName(this.message.getFromPrintName());
			acc = dummy;
			AccountManager.addAccount(acc);
		} else {
			if (verbose) Logger.logMessage('I', this, "Account for sender ID " + String.valueOf(this.message.getFromID()) + " found with name " + acc.getAccountName() + "!");
		}
		dummy = null;
//		acc.setHandler(this);
		
		if (acc.getAccountState() == Account.STATE_LOGGEDOFF){
			if (verbose) Logger.logMessage('I', this, "Sending welcome back because of message " + String.valueOf(id));
			this.sendMessage("Welcome back, " + acc.getAccountName());
		}
		
		acos = AccountManager.getAccountOnlineManager(acc);
		if (acos == null){
			Logger.logMessage('E', this, "No account online manager found for Account " + acc.getAccountName() + ". No online timer set.");
		} else {
			acos.setOnline();
		}
		
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_ACCESS)){
			if (verbose) Logger.logMessage('I', this, "Handling command " + String.valueOf(id) + ": " + message[0]);
			switch(message[0]){
			case "ping": this.ping(message); break;
			case "PING": this.ping(message); break;
			case "Ping": this.ping(message); break;
			case "echo": this.echo(message); break;
			case "Echo": this.echo(message); break;
			case "ECHO": this.echo(message); break;
			case "kick": this.exit(message); break;
			case "Kick": this.exit(message); break;
			case "switchOn": this.switchOn(message); break;
			case "switchon": this.switchOn(message); break;
			case "SwitchOn": this.switchOn(message); break;
			case "Switchon": this.switchOn(message); break;
			case "switchOff": this.switchOff(message); break;
			case "switchoff": this.switchOff(message); break;
			case "SwitchOff": this.switchOff(message); break;
			case "Switchoff": this.switchOff(message); break;
			case "switch": this.switchPower(message); break;
			case "Switch": this.switchPower(message); break;
			case "manageSwitch": this.manageSwitch(message); break;
			case "delay": this.postpone(message); break;
			case "postpone": this.postpone(message); break;
			case "help": this.help(message); break;
			case "info": this.help(message); break;
			case "healthreport": this.healthreport(message); break;
			case "userID": this.userID(message); break;
			case "shutdown": this.shutdown(message); break;
			case "restart": this.restart(message); break;
			}
		} else {
			Logger.logMessage('I', this, "Account " + acc.getAccountName() + " has no " + AccountPrivileges.getPrivString(AccountPrivileges.PERM_ACCESS) + "-permission. Not handling.");
		}
	}
	
	//------ Begin of command-related methods ------
	
	private void ping(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_PING)){
			this.sendMessage("ping received!");
		} else {
			this.noPermAns("ping");
		}
	}
	
	private void echo(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_ECHO)){
			if (message.length > 1){
				if (verbose) Logger.logMessage('I', this, "Executing echo command");
				this.sendMessage(message[1]);
			} else {
				String error = "usage: echo <message>; see help for more information.";
				Logger.logMessage('E', this, "not enough arguments for echo command. " + error);
				this.sendMessage(error);
			}
		} else {
			this.noPermAns("echo");
		}
	}
	
	private void exit(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_KICK)){
			String infoString = "Received Exit-command over network. Exiting.";
			this.sendMessage(infoString);
			Logger.logMessage('I', this, infoString);
			logging.LogManager.saveLogFile("log.txt");
			AccountManager.saveAccounts();
			System.exit(0);
		} else {
			this.noPermAns("exit");
		}
	}
	
	private void manageSwitch(String[] message){
		
	}
	
	private void switchPower(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_SWITCHPOWER)){
//			if (message.length > )
			if (message.length > 3){
				String infoString = "Executing switch command";
				if (verbose) Logger.logMessage('I', this, infoString);
				this.sendMessage(infoString);
				
				if (verbose) Logger.logMessage('I', this, "infoString");
				try {
					Runtime.getRuntime().exec("sudo send " + message[1] + " " + message[2] + " " + message[3]);
				} catch (IOException e) {
					String error = "Error when trying to execute send command";
					Logger.logException('E', error, e);
					this.sendMessage(error + " " + e.getMessage() + System.lineSeparator() + e.getStackTrace());
				}
			} else {
				String error = "usage: switchOn <systemID> <unitID>; switchOff <systemID> <unitID>; switch <systemID> <unitID> <state>; see help for more information."; //TODO: Multi-line messages
				Logger.logMessage('E', "not enough arguments for switchOn command. " + error);
				this.sendMessage(error);
			}
		} else {
			this.noPermAns("switchPower");
		}
	}
	
	private void switchOn(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_SWITCHON)){
//			if (message.length > 2){
//			if (verbose) Logger.logMessage('I', this, "Executing switchOn command");
//			try {
//				Runtime.getRuntime().exec("sudo send " + message[1] + " " + message[2] + " 1");
//			} catch (IOException e) {
//				String error = "Error when trying to execute send command";
//				Logger.logException('E', error, e);
//				notifier.send(answerCommand + error + " " + e.getMessage() + System.lineSeparator() + e.getStackTrace());
//			}
			String[] messageNew = new String[message.length + 1];
			for (int i = 0; i < message.length; i++){
				messageNew[i] = message[i];
			}
			messageNew[messageNew.length - 1] = "1";
//			} else {
//				String error = "usage: switchOn <systemID> <unitID>; see help for more information.";
//				Logger.logMessage('E', "not enough arguments for switchOn command. " + error);
//				notifier.send(answerCommand + error);
//			}
		} else {
			this.noPermAns("switchOn");
		}
	}
	
	private void switchOff(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_SWITCHOFF)){
//			if (message.length > 2){
//			if (verbose) Logger.logMessage('I', this, "Executing switchOff command");
//			try {
//				Runtime.getRuntime().exec("sudo send " + message[1] + " " + message[2] + " 0");
//			} catch (IOException e) {
//				String error = "Error when trying to execute send command";
//				Logger.logException('E', error, e);
//				notifier.send(answerCommand + error + " " + e.getMessage() + System.lineSeparator() + e.getStackTrace());
//			}
			String[] messageNew = new String[message.length + 1];
			for (int i = 0; i < message.length; i++){
				messageNew[i] = message[i];
			}
			messageNew[messageNew.length - 1] = "0";
	//		} else {
	//			String error = "usage: switchOff <systemID> <unitID>; see help for more information.";
	//			Logger.logMessage('E', "not enough arguments for switchOn command. " + error);
	//			notifier.send(answerCommand + error);
	//		}
		} else {
			this.noPermAns("switchOff");
		}
	}
	
	private void postpone(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_POSTPONE)){
//			if ((message.getContents().length - commandDepthParsed) > 2){
			if (message.length > 2){
				String info = "Executing postpone-command...";
				if (verbose) Logger.logMessage('I', this, info);
				this.sendMessage(info);
				try {
					long offset = Long.parseLong(message[1]);
					Thread.sleep(offset);
//					commandDepthParsed = commandDepthParsed + 2;
					handleMessage(Arrays.copyOfRange(message, 2, message.length));
				} catch (Exception ex){
					//TODO: find possible exceptions
					String error = "err0r when trying to postpone command execution";
					Logger.logException(this, error, ex);
					this.sendMessage(error);
				}
			} else {
				String error = "usage: postpone <offset> <command> [command options]..; see help for more information.";
				Logger.logMessage('E', this, "not enough arguments given for postpone command. " + error);
				this.sendMessage(error);
			}
		} else {
			this.noPermAns("postpone");
		}
	}
	
	private void help(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_INFO) && acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_HELP)){
			try{
				StringBuilder info = FileHandler.readStringBuilder("information");
				info.append(FileHandler.readStringBuilder("commandHelp"));
				this.sendMessage(info.toString());
			} catch (Exception ex){
				//TODO: find possible exceptions
				String error = "Error when reading help file.";
				Logger.logMessage('E', this, error);
				this.sendMessage(error);
			}
		} else {
			this.noPermAns("help");
		}
	}
	
	@SuppressWarnings("unused")
	private void info(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_INFO)){
			try{
				StringBuilder info = FileHandler.readStringBuilder("information");
				this.sendMessage(info.toString());
			} catch (Exception ex){
				//TODO: find possible exceptions
				String error = "Error when reading info file.";
				Logger.logMessage('E', this, error);
				this.sendMessage(error);
			}
		} else {
			this.noPermAns("info");
		}
	}
	
	private void healthreport(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_HEALTHREPORT)){
			if (message.length < 1){
				switch(message[1]){
				case "now": break; //make a new healthreport now
				}
			} else {
				//send the last healthreport
			}
		} else {
			this.noPermAns("healthreport");
		}
	}
	
	private void userID(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_USERID)){
			if (message.length > 1){
				notifier.send("user_info " + message[1]);
			} else {
				String error = "usage: userID <user>: returns the user ID of requested user.";
				Logger.logMessage('E', this, "not enough arguments given for userID command. " + error);
				this.sendMessage(error);
			}
		} else {
			this.noPermAns("userID");
		}
	}
	
	private void shutdown(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_SHUTDOWN)){
			
		} else {
			this.noPermAns("shutdown");
		}
	}
	
	private void restart(String[] message){
		if (acc.hasAccountPrivilege(AccountPrivileges.PERM_CMD_RESTART)){
			
		} else {
			this.noPermAns("restart");
		}
	}
	
	private void sendMessage(String messageText){
		if (!raw && message.getFrom().getID() < 0){
			Logger.logMessage('W', this, "will not answer message " + String.valueOf(id) + ", because it was internal. Message text: " + messageText);
		} else {
			notifier.send(notifier.getAnswerCommand() + messageText);
		}
	}
	
	private void noPermAns(String command){
		this.sendMessage("sorry.");
		Logger.logMessage('W', this, "User " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID())
				+ " has tried to execute " + command + "-command without sufficient permission.");
		Logger.logMessage('W', this, "User " + acc.getAccountName() + " with ID " + String.valueOf(acc.getAccountID())
				+ " has tried to execute " + command + "-command without sufficient permission.", "priv");
	}
}