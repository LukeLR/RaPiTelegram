package listener;

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
	
	private boolean verbose = true;
	
	public Handler(String messageString, Notifier notifier){
		this(messageString, false, notifier);
	}
	
	public Handler(String messageString, boolean raw, Notifier notifier){
		if (raw){
			if (verbose) Logger.logMessage('I', this, "New MessageHandler created with messageString. Content is raw.");
		} else {
			if (verbose) Logger.logMessage('I', this, "New MessageHandler created with messageString. Content is JSON");
		}
		
		this.messageString = messageString;
		this.notifier = notifier;
		parsingNeeded = true;
		this.raw = raw;
		this.start();
	}
	
	public Handler(Message message, Notifier notifier){
		if (verbose) Logger.logMessage('I', this, "New MessageHandler created with message object.");
		this.message = message;
		this.notifier = notifier;
		parsingNeeded = false;
		parsedWell = true;
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
		if (raw){
			message = new Message();
			message.setText(messageString);
		} else {
			String[] contents = messageString.trim().split("\\s");
			if (contents[0].equals("ANSWER")){
				Logger.logMessage('W', this, "Message is ANSWER xxx, skipping parsing & handling");
				parsedWell = false;
			} else {
				JSONObject obj = new JSONObject (messageString);
				
				if(obj.getString("event").equals("message")){
					message = new Message(obj);			
				} else {
					Logger.logMessage('E', this, "Given JSONString is not a message. JSONString:\n" + messageString);
				}
				if (verbose) Logger.logMessage('I', this, "Resulting messageText: " + message.getText());
				parsedWell = true;
			}
		}
	}
	
	private void handleMessage(){
		if (parsedWell){
			if (verbose) Logger.logMessage('I', this, "Handling command: " + message.getContents()[0]);
			switch(message.getContents()[0]){
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
		} else {
			Logger.logMessage('W', this, "Message was not parsed well. Exiting.");
		}
	}
	
	//------ Begin of command-related methods ------
	
	private void ping(){
		notifier.send("received ping!");
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