package main;

import listener.Notifier;
import network.NetworkListener;
import network.NetworkNotifier;

public class Main {
	public static void main(String[] args){
		Notifier n = new Notifier();
		NetworkListener nl = new NetworkListener(n, 1234);
		nl.start();
		n.setListener(nl);
	}
}