package main;

import listener.Notifier;
import network.NetworkClient;
import network.NetworkNotifier;

public class Main {
	public static void main(String[] args){
		Notifier n = new Notifier();
		NetworkClient nc = new NetworkClient(n, 1234);
		nc.start();
		n.setClient(nc); //gets executed, because NetworkListener is a Thread
	}
}