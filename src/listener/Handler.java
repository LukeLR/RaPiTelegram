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
import java.util.logging.LogManager;

import org.json.JSONException;
import org.json.JSONObject;

import util.FileHandler;
import logging.Logger;
import misc.Message;
import network.MessageHandler;

public class Handler extends Thread{
	private String messageString;
	private String answerCommand = "";
	private Notifier notifier;
	private Message message;
	private boolean parsingNeeded = true;
	private boolean raw = false;
	private boolean parsedWell = false;
	private int id = -1;
	private int commandDepthParsed = 0;
	
	private boolean verbose = true;
	private boolean skipOwn = true;
	private int ownID = 54916622;
	
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
			handleMessage(message.getContents()[0]);	
		} else {
			Logger.logMessage('W', this, "Message " + String.valueOf(id) + " was not parsed well. Exiting.");
		}
	}
	
	private void parseMessage(){
		if (verbose) Logger.logMessage('I', this, "Parsing message " + String.valueOf(id) + "...");
		if (raw){
			message = new Message();
			message.setText(messageString);
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
					answerCommand = "msg " + message.getFromPrintName() + " ";
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
	
	private void handleMessage(String command){
		if (verbose) Logger.logMessage('I', this, "Handling command " + String.valueOf(id) + ": " + command);
		switch(command){
		case "ping": this.ping(); break;
		case "PING": this.ping(); break;
		case "Ping": this.ping(); break;
		case "echo": this.echo(); break;
		case "Echo": this.echo(); break;
		case "ECHO": this.echo(); break;
		case "kick": this.exit(); break;
		case "Kick": this.exit(); break;
		case "switchOn": this.switchOn(); break;
		case "switchon": this.switchOn(); break;
		case "SwitchOn": this.switchOn(); break;
		case "Switchon": this.switchOn(); break;
		case "switchOff": this.switchOff(); break;
		case "switchoff": this.switchOff(); break;
		case "SwitchOff": this.switchOff(); break;
		case "Switchoff": this.switchOff(); break;
		case "delay": this.postpone(); break;
		case "postpone": this.postpone(); break;
		case "help": this.help(); break;
		case "info": this.help(); break;
		case "healthreport": this.healthreport(); break;
		case "shutdown": this.shutdown(); break;
		case "restart": this.restart(); break;
		}
	}
	
	//------ Begin of command-related methods ------
	
	private void ping(){
		notifier.send(answerCommand + "ping received!");
	}
	
	private void echo(){
		if (message.getContents().length > 1){
			if (verbose) Logger.logMessage('I', this, "Executing echo command");
			notifier.send(answerCommand + message.getContents()[1]);
		} else {
			String error = "usage: echo <message>; see help for more information.";
			Logger.logMessage('E', this, "not enough arguments for echo command. " + error);
			notifier.send(answerCommand + error);
		}
	}
	
	private void exit(){
		Logger.logMessage('I', this, "Received Exit-command over network. Exiting.");
		logging.LogManager.saveLogFile("log.txt");
		System.exit(0);
	}
	
	private void switchOn(){
		if (message.getContents().length > 2){
			if (verbose) Logger.logMessage('I', this, "Executing switchOn command");
			try {
				Runtime.getRuntime().exec("send " + message.getContents()[1] + " " + message.getContents()[2] + " 1");
			} catch (IOException e) {
				String error = "Error when trying to execute send command";
				Logger.logException('E', error, e);
				notifier.send(answerCommand + error + " " + e.getMessage() + System.lineSeparator() + e.getStackTrace());
			}
		} else {
			String error = "usage: switchOn <systemID> <unitID>; see help for more information.";
			Logger.logMessage('E', "not enough arguments for switchOn command. " + error);
			notifier.send(answerCommand + error);
		}
	}
	
	private void switchOff(){
		if (message.getContents().length > 2){
			if (verbose) Logger.logMessage('I', this, "Executing switchOff command");
			try {
				Runtime.getRuntime().exec("send " + message.getContents()[1] + " " + message.getContents()[2] + " 0");
			} catch (IOException e) {
				String error = "Error when trying to execute send command";
				Logger.logException('E', error, e);
				notifier.send(answerCommand + error + " " + e.getMessage() + System.lineSeparator() + e.getStackTrace());
			}
		} else {
			String error = "usage: switchOff <systemID> <unitID>; see help for more information.";
			Logger.logMessage('E', "not enough arguments for switchOn command. " + error);
			notifier.send(answerCommand + error);
		}
	}
	
	private void postpone(){
		if (message.getContents().length > 2){
			String info = "Executing postpone-command...";
			if (verbose) Logger.logMessage('I', this, info);
			notifier.send(answerCommand + info);
			try {
				long offset = Long.parseLong(message.getContents()[1]);
				Thread.sleep(offset);
				commandDepthParsed = commandDepthParsed + 2;
				handleMessage(message.getContents()[commandDepthParsed]);
			} catch (Exception ex){
				//TODO: find possible exceptions
				String error = "err0r when trying to postpone command execution";
				Logger.logException(this, error, ex);
				notifier.send(answerCommand + error);
			}
		} else {
			String error = "usage: postpone <offset> <command> [command options]..; see help for more information.";
			Logger.logMessage('E', this, "not enough arguments given for postpone command. " + error);
			notifier.send(answerCommand + error);
		}
	}
	
	private void help(){
		try{
			StringBuilder info = FileHandler.readStringBuilder("information");
			info.append(FileHandler.readStringBuilder("commandHelp"));
			notifier.send(answerCommand + info.toString());
		} catch (Exception ex){
			//TODO: find possible exceptions
			String error = "Error when reading help file.";
			Logger.logMessage('E', this, error);
			notifier.send(answerCommand + error);
		}
	}
	
	private void info(){
		try{
			StringBuilder info = FileHandler.readStringBuilder("information");
			notifier.send(answerCommand + info.toString());
		} catch (Exception ex){
			//TODO: find possible exceptions
			String error = "Error when reading info file.";
			Logger.logMessage('E', this, error);
			notifier.send(answerCommand + error);
		}
	}
	
	private void healthreport(){
		if (message.getContents().length < 1){
			switch(message.getContents()[1]){
			case "now": break; //make a new healthreport now
			}
		} else {
			//send the last healthreport
		}
	}
	
	private void shutdown(){
		
	}
	
	private void restart(){
		
	}
}