package listener;

import network.NetworkServer;
import logging.Logger;

public class Notifier implements network.NetworkNotifier {
	private boolean bidirectional = false;
	private NetworkServer server = null;
	
	public Notifier(){
		
	}
	
	public void setServer(NetworkServer server){
		this.server = server;
		bidirectional = true;
	}
	
	public void onNotify(String notifyString) {
		Logger.logMessage('I', this, "got notifyString: " + notifyString);
		if (bidirectional = true && server != null){
			server.send(notifyString);
		}
	}
}