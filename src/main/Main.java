package main;

import listener.Notifier;
import network.NetworkServer;
import network.NetworkClient;
import network.NetworkNotifier;

public class Main {
	public static void main(String[] args){
		
		if (args.length != 2) {
            System.err.println(
                "Usage: java <application> <host name> <port number>");
            System.exit(1);
        }
		
		Notifier n = new Notifier();
//		NetworkServer ns = new NetworkServer(n, 1234);
		NetworkClient nc = new NetworkClient(n, args[0], Integer.parseInt(args[1]));
		nc.send("main_session");
//		n.setServer(ns);
		n.setClient(nc);
//		ns.start();
//		n.setServer(ns); //gets executed, because NetworkListener is a Thread
//		nc.send("Test!");
//		nc.send("Asdf!");
	}
}