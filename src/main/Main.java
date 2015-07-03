package main;

import listener.Notifier;
import network.NetworkServer;
import network.NetworkClient;
import network.NetworkNotifier;

public class Main {
	public static void main(String[] args){
		Notifier n = new Notifier();
//		NetworkServer ns = new NetworkServer(n, 1234);
		NetworkClient nc = new NetworkClient(n, "localhost", 1234);
//		ns.start();
//		n.setServer(ns); //gets executed, because NetworkListener is a Thread
//		nc.send("Test!");
//		nc.send("Asdf!");
	}
}