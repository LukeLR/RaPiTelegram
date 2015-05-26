package listener;

import logging.Logger;

public class Notifier implements network.NetworkNotifier {
	public void onNotify(String notifyString) {
		Logger.logMessage('I', this, notifyString);
	}
}