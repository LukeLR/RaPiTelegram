package listener;

import network.NetworkClient;
import logging.Logger;

public class Notifier implements network.NetworkNotifier {
	private boolean bidirectional = false;
	private NetworkClient client = null;
	
	public Notifier(){
		
	}
	
	public void setClient(NetworkClient client){
		this.client = client;
		bidirectional = true;
	}
	
	public void onNotify(String notifyString) {
		Logger.logMessage('I', this, "passed:" + notifyString);
		if (bidirectional = true && client != null){
			client.send(notifyString);
		}
	}
}