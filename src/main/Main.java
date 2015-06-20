package main;

import listener.Client;
import network.NetworkListener;
import network.NetworkClient;

public class Main {
	public static void main(String[] args){
		Client c = new Client();
		NetworkListener nl = new NetworkListener(n, 1234);
		nl.start();
		n.setListener(nl); //gets executed, because NetworkListener is a Thread
	}
}