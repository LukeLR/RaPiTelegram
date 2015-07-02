package listener;

import org.json.JSONObject;

import logging.Logger;
import misc.Message;
import network.MessageHandler;

public class Handler extends Thread{
	private String messageString;
	private Message message;
	private boolean verbose = true;
	private boolean parsingNeeded = true;
	
	public Handler(String messageString){
		if (verbose) Logger.logMessage('I', this, "New MessageHandler created with messageString.");
		this.messageString = messageString;
		parsingNeeded = true;
		this.start();
	}
	
	public Handler(Message message){
		if (verbose) Logger.logMessage('I', this, "New MessageHandler created with message object.");
		this.message = message;
		parsingNeeded = false;
		this.start();
	}
	
	public void run(){
		if (parsingNeeded){
			parseMessage();
		}
		handleMessage();
	}
	
	private void parseMessage(){
		if (verbose) Logger.logMessage('I', this, "Parsing message...");
		JSONObject obj = new JSONObject (messageString);
		JSONObject fromObj = obj.getJSONObject("from");
		JSONObject toObj   = obj.getJSONObject("to");
		
		if(obj.getString("event").equals("message")){
			message.setService(obj.getBoolean("service"));
			
		} else {
			Logger.logMessage('E', this, "Given JSONString is not a message. JSONString:\n" + messageString);
		}
		if (verbose) Logger.logMessage('I', this, "Resulting messageText: " + messageText);
	}
	
	private void handleMessage(){
		if (verbose) Logger.logMessage('I', this, "Handling command: " + messageContents[0]);
		switch(messageContents[0]){
		case "ping": this.ping(); break;
		case "PING": this.ping(); break;
		case "Ping": this.ping(); break;
		case "echo": this.echo(); break;
		case "Echo": this.echo(); break;
		case "ECHO": this.echo(); break;
		case "exit": this.exit(); break;
		case "Exit": this.exit(); break;
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
		
	}
	
	private void echo(){
		
	}
	
	private void exit(){
		Logger.logMessage('I', this, "Received Exit-command over network. Exiting.");
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
		if (messageContents.length < 1){
			switch(messageContents[1]){
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