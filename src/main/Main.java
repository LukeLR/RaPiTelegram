<<<<<<< HEAD
/**
 * This file is part of LukeUtils.
 *
 * LukeUtils is free software: you can redistribute it and/or modify
 * it under the terms of the cc-by-nc-sa (Creative Commons Attribution-
 * NonCommercial-ShareAlike) as released by the Creative Commons
 * organisation, version 3.0.
 *
 * LukeUtils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY.
 *
 * You should have received a copy of the cc-by-nc-sa-license along
 * with this LukeUtils. If not, see
 * <https://creativecommons.org/licenses/by-nc-sa/3.0/legalcode>.
 *
 * Copyright Lukas Rose 2013 - 2015
 */

package main;

import listener.Notifier;
import misc.AccountManager;
import network.NetworkClient;

public class Main {
	public static void main(String[] args){
		if (args.length != 2) {
			System.err.println("Usage: java <application> <host name> <port number>");
			System.exit(1);
		}
		
		AccountManager.loadAccounts();
		
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
=======
/**
 * This file is part of LukeUtils.
 *
 * LukeUtils is free software: you can redistribute it and/or modify
 * it under the terms of the cc-by-nc-sa (Creative Commons Attribution-
 * NonCommercial-ShareAlike) as released by the Creative Commons
 * organisation, version 3.0.
 *
 * LukeUtils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY.
 *
 * You should have received a copy of the cc-by-nc-sa-license along
 * with this LukeUtils. If not, see
 * <https://creativecommons.org/licenses/by-nc-sa/3.0/legalcode>.
 *
 * Copyright Lukas Rose 2013 - 2015
 */

package main;

import listener.Notifier;
import misc.AccountManager;
import misc.InputReader;
import misc.NotifierManager;
import network.NetworkClient;

public class Main {
	public static void main(String[] args){
		if (args.length != 2) {
			System.err.println("Usage: java <application> <host name> <port number>");
			System.exit(1);
		}
		
		AccountManager.loadAccounts();
		
		Notifier n = new Notifier();
		NotifierManager.setNotifier(n);
//		NetworkServer ns = new NetworkServer(n, 1234);
		NetworkClient nc = new NetworkClient(n, args[0], Integer.parseInt(args[1]));
		nc.send("main_session");
//		n.setServer(ns);
		n.setClient(nc);
//		ns.start();
//		n.setServer(ns); //gets executed, because NetworkListener is a Thread
//		nc.send("Test!");
//		nc.send("Asdf!");
		InputReader in = new InputReader();
		in.start();
	}
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635
}