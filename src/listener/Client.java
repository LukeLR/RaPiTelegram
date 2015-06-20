package listener;

import network.NetworkListener;
import logging.Logger;

public class Client implements network.NetworkClient {
	private boolean bidirectional = false;
	private NetworkListener listener = null;
	
	public Client(){
		
	}
	
	public void setListener(NetworkListener listener){
		this.listener = listener;
		bidirectional = true;
	}
	
	public void onNotify(String notifyString) {
		Logger.logMessage('I', this, "passed:" + notifyString);
		if (bidirectional = true && listener != null){
			listener.send(notifyString);
		}
	}
}