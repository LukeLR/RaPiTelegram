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

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.json.JSONException;
import org.json.JSONObject;

import network.NetworkClient;
import network.NetworkServer;
import logging.Logger;

public class Notifier implements network.NetworkNotifier {
	@SuppressWarnings("unused")
	private boolean bidirectional = false;
	private NetworkServer server = null;
	private NetworkClient client = null;
	private Queue userInfoQueue = new LinkedList<Handler>();
	
	private int lastID = 0;
	
	private String answerCommand = "";
	
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
	
//	public void onNotify(String notifyString) {
//		if (verbose) Logger.logMessage('I', this, "got notifyString: (" + String.valueOf(lastID) + ") " + notifyString);
//		Logger.logMessage('<', notifyString, "socket");
////		if (bidirectional = true && server != null){
////			server.send(notifyString);
////		}
//		if (notifyString.equals("raw") || notifyString.equals("Raw") || notifyString.equals("RAW")){
//			raw = !raw;
//			send("Raw mode is now " + String.valueOf(raw));
//		}
//		new Handler(notifyString, raw, this, lastID);
//		lastID++;
//	}
	
	public void onNotify(JSONObject object){
		onNotify(object, false);
	}
	
	public void onNotify(JSONObject object, boolean raw){
		Logger.logMessage('<', "JSONObject: " + object.toString(), "socket");
		new Handler(object, this, lastID, raw);
	}
	
	public void onNotify(String notifyString){
		onNotify(notifyString, false);
	}
	
	public void onNotify(String notifyString, boolean raw){
		if (verbose) Logger.logMessage('I', this, "Got notifyString: (" + String.valueOf(lastID) + ") " + notifyString);
		Logger.logMessage('<', notifyString, "socket"); //Log every incoming message to channel 'socket'.
		if (notifyString.equals("raw") || notifyString.equals("Raw") || notifyString.equals("RAW")){ //Check if notifyString is the command to switch RAW-Mode.
			raw = !raw; //Switch RAW-Mode if it does.
		} else {
			if (!raw){ //If RAW-Mode is not enabled, try to treat notifyString as a JSON String.
				if (notifyString.startsWith("{")){ //Check if String is a JSON String. All JSON String start with '{'.
					try{
						JSONObject obj = new JSONObject(notifyString); //Try to pares JSON-String.
						if (obj.has("event")){ //Check if incoming JSON has an "event".
							if (obj.getString("event").equals("message")){ //Check if incoming JSON is a message
								new Handler(obj, this, lastID); //Handle message if it does.
							}
						} else {
							if (obj.has("type")){ //Check if incoming JSON-String has a "type".
								if (obj.getString("type").equals("user")){ //Check if incoming JSON-String is an user-info.
									//Notify first Handler in Queue waiting for an user_info that an user_info has arrived.
									if (!this.userInfoQueue.isEmpty()){ //Check if there are Handlers waiting
										((Handler)this.userInfoQueue.poll()).on_UserInfoArrive(obj); //Notify waiting Handler.
									}
								}
							}
						}
					} catch (JSONException ex){ //Incoming notifyString probably was no correct JSON.
						Logger.logMessage('E', this, "Error when parsing JSON for ID "
								+ String.valueOf(lastID) + " in Notifier.");
					} catch (Exception ex){ //Some strange other Exception appeared.
						Logger.logMessage('E', this, "Error when parsing JSON for ID "
								+ String.valueOf(lastID) + " in Notifier in an general exception.");
					}
				}
			} else {
				new Handler(notifyString, this, lastID); //Handle RAW-Message
			}
		}
		lastID ++; //Count Message ID one up.
	}
	
	public void setAnswerCommand (String answerCommand){
		this.answerCommand = answerCommand;
	}
	
	public String getAnswerCommand (){
		return answerCommand;
	}
	
	public void enqueueForUserInfo(Handler h){
		if (h != null){
			if (this.userInfoQueue != null){
				this.userInfoQueue.add(h);
			} else {
				userInfoQueue = new LinkedList<Handler>();
			}
		}
	}
}