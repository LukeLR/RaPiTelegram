package listener;

import logging.Logger;
import network.MessageHandler;

public class Handler extends Thread{
	private String message;
	public Handler(String message){
		this.message = message;
		this.start();
	}
	
	public void run(){
		parseMessage();
		handleMessage();
	}
	
	private void parseMessage(){
		
	}
	
	private void handleMessage(){
		switch(message){
		case "exit": Logger.logMessage('I', this, "Received Exit-command over network. Exiting."); System.exit(0); break;
		}
	}
}