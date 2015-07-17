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

import java.util.logging.LogManager;

import org.json.JSONException;
import org.json.JSONObject;

import logging.Logger;
import misc.Message;
import network.MessageHandler;

public class Handler extends Thread{
	private String messageString;
	private Notifier notifier;
	private Message message;
	private boolean parsingNeeded = true;
	private boolean raw = false;
	private boolean parsedWell = false;
	private int id = -1;
	
	private boolean verbose = true;
	
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
		handleMessage();
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
	}
	
	private void handleMessage(){
		if (parsedWell){
			if (verbose) Logger.logMessage('I', this, "Handling command " + String.valueOf(id) + ": " + message.getContents()[0]);
			switch(message.getContents()[0]){
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
		} else {
			Logger.logMessage('W', this, "Message " + String.valueOf(id) + " was not parsed well. Exiting.");
		}
	}
	
	//------ Begin of command-related methods ------
	
	private void ping(){
		notifier.send("msg " + message.getFromPrintName() + " ping received!");
	}
	
	private void echo(){
		
	}
	
	private void exit(){
		Logger.logMessage('I', this, "Received Exit-command over network. Exiting.");
		logging.LogManager.saveLogFile("log.txt");
		System.exit(0);
	}
	
	private void switchOn(){
		
	}
	
	private void switchOff(){
		
	}
	
	private void postpone(){
		
	}
	
	private void help(){
		
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