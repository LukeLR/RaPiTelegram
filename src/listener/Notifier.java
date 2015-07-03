package listener;

import network.NetworkClient;
import network.NetworkServer;
import logging.Logger;

public class Notifier implements network.NetworkNotifier {
	private boolean bidirectional = false;
	private NetworkServer server = null;
	private NetworkClient client = null;
	
	private boolean verbose = true;
	
	public Notifier(){
		
	}
	
	public void setServer(NetworkServer server){
		this.server = server;
		bidirectional = true;
	}
	
	public void setClient(NetworkClient client){
		this.client = client;
		bidirectional = true;
	}
	
	public void send(String message){
		if (client != null){
			if (verbose) Logger.logMessage('I', this, "Sending message over client (auto)");
			client.send(message);
		} else if (server != null){
			if (verbose) Logger.logMessage('I', this, "Sending message over server (auto)");
			server.send(message);
		} else {
			Logger.logMessage('E', this, "Error when trying to send message: Neither client nor server are set.");
		}
	}
	
	public void sendByServer(String message){
		if (server != null){
			if (verbose) Logger.logMessage('I', this, "Sending message over server (forced)");
			server.send (message);
		} else {
			Logger.logMessage('E', this, "Error when trying to send message over server: Server not set!");
		}
	}
	
	public void sendByClient(String message){
		if (client != null){
			if (verbose) Logger.logMessage('I', this, "Sending message over client (forced)");
			client.send(message);
		} else {
			Logger.logMessage('E', this, "Error when trying to send message over client: Client not set!");
		}
	}
	
	public void onNotify(String notifyString) {
		Logger.logMessage('I', this, "got notifyString: " + notifyString);
//		if (bidirectional = true && server != null){
//			server.send(notifyString);
//		}
		
		new Handler(notifyString, this);
	}
}