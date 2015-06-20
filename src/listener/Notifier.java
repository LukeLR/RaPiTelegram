package listener;

import network.NetworkListener;
import logging.Logger;

public class Notifier implements network.NetworkNotifier {
	private boolean bidirectional = false;
	private NetworkListener listener = null;
	
	public Notifier(){
		
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