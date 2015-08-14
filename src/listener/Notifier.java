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

package listener;

import network.NetworkClient;
import network.NetworkServer;
import logging.Logger;

public class Notifier implements network.NetworkNotifier {
	@SuppressWarnings("unused")
	private boolean bidirectional = false;
	private NetworkServer server = null;
	private NetworkClient client = null;
	
	private int lastID = 0;
	
	private boolean verbose = false;
	private boolean raw = false;
	
	public Notifier(){
		
	}
	
	public void setServer(NetworkServer server){
		this.server = server;
		bidirectional = true;
	}
	
	public void setClient(NetworkClient client){
		this.client = client;
		bidirectional = true;
	}
	
	public void send(String message){
		if (client != null){
			if (verbose) Logger.logMessage('I', this, "Sending message over client (auto)");
			client.send(message);
			Logger.logMessage(">c", message, "socket");
		} else if (server != null){
			if (verbose) Logger.logMessage('I', this, "Sending message over server (auto)");
			server.send(message);
			Logger.logMessage(">s", message, "socket");
		} else {
			Logger.logMessage('E', this, "Error when trying to send message: Neither client nor server are set.");
			Logger.logMessage(">!", message, "socket");
		}
	}
	
	public void sendByServer(String message){
		if (server != null){
			if (verbose) Logger.logMessage('I', this, "Sending message over server (forced)");
			server.send (message);
			Logger.logMessage(">s!", message, "socket");
		} else {
			Logger.logMessage('E', this, "Error when trying to send message over server: Server not set!");
		}
	}
	
	public void sendByClient(String message){
		if (client != null){
			if (verbose) Logger.logMessage('I', this, "Sending message over client (forced)");
			client.send(message);
			Logger.logMessage(">c!", message, "socket");
		} else {
			Logger.logMessage('E', this, "Error when trying to send message over client: Client not set!");
		}
	}
	
	public void onNotify(String notifyString) {
		if (verbose) Logger.logMessage('I', this, "got notifyString: (" + String.valueOf(lastID) + ") " + notifyString);
		Logger.logMessage('<', notifyString, "socket");
//		if (bidirectional = true && server != null){
//			server.send(notifyString);
//		}
		if (notifyString.equals("raw") || notifyString.equals("Raw") || notifyString.equals("RAW")){
			raw = !raw;
			send("Raw mode is now " + String.valueOf(raw));
		}
		new Handler(notifyString, raw, this, lastID);
		lastID++;
	}
}