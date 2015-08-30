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

package misc;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import org.json.JSONObject;

import data.list.ListTools;
import exception.FieldNotFoundException;
import logging.Logger;

public class Message {
	protected Chat from, to;
	protected boolean service = false;
	protected int flags = -1;
	protected String text = "Message text";
//	protected String[] contents = null;
	protected List<String[]> commands = new LinkedList<String[]>();
	public static final String default_text = "Message text";
	protected int id = -1;
	protected int date = -1;
	protected boolean out = false;
	protected boolean unread = true;
	
	private boolean verbose = true;
	
	public Message(Chat from, Chat to, boolean service, int id, int date, boolean out, boolean unread, String text){
		this.from = from;
		this.to = to;
		this.service = service;
		this.id = id;
		this.date = date;
		this.out = out;
		this.unread = unread;
		this.text = text;
		genContents();
	}
	
	public Message(){
		this.from = new Chat();
		this.to = new Chat();
		this.service = false;
		this.flags = -1;
		this.text = "Message text";
		genContents();
		this.id = -1;
		this.date = -1;
		this.out = false;
		this.unread = true;
	}
	
	public Message(JSONObject obj){
		if (verbose) Logger.logMessage('I', this, "Constructing Message by JSON!");
		
		if (obj.getString("event").equals("message")){			
			// reading standard fields
			
			setService(obj.has("service") ? obj.getBoolean("service") : false);
			setFlags(obj.has("flags") ? obj.getInt("flags") : -1);
			setText(obj.has("text") ? obj.getString("text") : "no message");
			genContents();
			setID(obj.has("id") ? obj.getInt("id") : -1);
			setDate(obj.has("date") ? obj.getInt("date") : -1);
			setOutgoing(obj.has("out") ? obj.getBoolean("out") : false);
			setUnread(obj.has("unread") ? obj.getBoolean("unread") : true);
			
			if (obj.has("from")){
				JSONObject fromObject = obj.getJSONObject("from");
				
				//reading sender fields
				
				if (fromObject.getString("type").equals("user")){
					// from-chat is an user
					from = new User(fromObject);
				} else if (fromObject.getString("type").equals("chat")){
					// from-chat is a group chat
					from = new Group(fromObject);
				} else {
					Logger.logMessage('E', this, "From-Object is neither user nor group chat!");
				}
			} else {
				this.from = new Chat();
			}
			
			if (obj.has("to")){
				JSONObject toObject = obj.getJSONObject("to");
				
				// reading receipient fields
				
				if (toObject.getString("type").equals("user")){
					// to-chat is an user
					to = new User(toObject);
				} else if (toObject.getString("type").equals("chat")){
					// to-chat is a group chat
					to = new Group(toObject);
				} else {
					Logger.logMessage('E', this, "To-Object is neither user nor group chat!");
				}
			} else {
				this.to = new Chat();
			}
			
		} else {
			Logger.logMessage('E', this, "Given JSONObject is no message!");
		}
	}
	
	public Message(String jsonString){
		this(new JSONObject(jsonString));
		if (verbose) Logger.logMessage('I', this, "Constructed Message by JSONString.");
	}
	
	// ------ Setter methods -------
	
	public void setFrom(Chat from){
		this.from = from;
	}
	
	public void setTo(Chat to){
		this.to = to;
	}
	
	public void setService(boolean service){
		this.service = service;
	}
	
	public void setFlags(int flags){
		this.flags = flags;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public void setDate(int date){
		this.date = date;
	}
	
	public void setOutgoing(boolean out){
		this.out = out;
	}
	
	public void setUnread(boolean unread){
		this.unread = unread;
	}
	
	public void setText(String text){
		this.text = text;
		genContents();
	}
	
	public void genContents(){
		if (!text.equals(default_text)){
			String[] temp = text.trim().split("\\s");
			List<String> current = new LinkedList<String>();
			commands = new LinkedList<String[]>();
			for (int i = 0; i < temp.length; i++){
				switch (temp[i]){
				case "&":
					if (!current.isEmpty()){
						try{
							commands.add(((String[])ListTools.ListToArray(current)));
						} catch (Exception ex){
							Logger.logMessage('E', this, "Could not parse current command List to String[] in Message.genContents");
						}
					} else {
						Logger.logMessage('I', this, "Command list currently handling is empty. Not enqueuing to commands list.");
					}
					current = new LinkedList<String>();
					current.add(temp[i]);
					break;
				case "&&": //basically the same code as above
					if (!current.isEmpty()){
						try{
							commands.add(((String[])ListTools.ListToArray(current)));
						} catch (Exception ex){
							Logger.logMessage('E', this, "Could not parse current command List to String[] in Message.genContents");
						}
					} else {
						Logger.logMessage('I', this, "Command list currently handling is empty. Not enqueuing to commands list.");
					}
					current = new LinkedList<String>();
					current.add(temp[i]);
					break;
				default:
					current.add(temp[i]);
					break;
				}
			}
			if (!current.isEmpty()){
				try{
					commands.add(((String[])ListTools.ListToArray(current)));
				} catch (Exception ex){
					Logger.logMessage('E', this, "Could not parse current command List to String[] in Message.genContents");
				}
			} else {
				Logger.logMessage('I', this, "Command list currently handling is empty. Not enqueuing to commands list.");
			}
		}
	}
	
	// ------ Advanced setter methods ------
	
	public void setFromChatID(int from_chat_id){
		from.setID(from_chat_id);
	}
	
	public void setToChatID(int to_chat_id){
		to.setID(to_chat_id);
	}
	
	public void setFromChatFlags(int from_chat_flags){
		from.setFlags(from_chat_flags);
	}
	
	public void setToChatFlags(int to_chat_flags){
		to.setFlags(to_chat_flags);
	}
	
	public void setToFirstName(String to_first_name){
		try{
			to.setFirstName(to_first_name);
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when setting receipients' first name: ", ex);
		}
	}
	
	public void setFromFirstName(String from_first_name){
		try{
			from.setFirstName(from_first_name);
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when setting sender's first name: ", ex);
		}
	}
	
	public void setToLastName(String to_last_name){
		try{
			to.setLastName(to_last_name);
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when setting receipients' last name: ", ex);
		}
	}
	
	public void setFromLastName(String from_last_name){
		try{
			from.setLastName(from_last_name);
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when setting sender's last name: ", ex);
		}
	}
	
	public void setToTitle(String to_title){
		try{
			to.setTitle(to_title);
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when setting receipients' chat title: ", ex);
		}
	}
	
	public void setFromTitle(String from_title){
		try{
			from.setTitle(from_title);
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when setting sender's chat title: ", ex);
		}
	}
	
	public void setFromPrintName(String from_print_name){
		from.setPrintName(from_print_name);
	}
	
	public void setToPrintName(String to_print_name){
		to.setPrintName(to_print_name);
	}
	
	public void setToPhone(long to_phone){
		try{
			to.setPhone(to_phone);
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when setting receipients' phone number: ", ex);
		}
	}
	
	public void setFromPhone(long from_phone){
		try{
			from.setPhone(from_phone);
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when setting sender's phone number: ", ex);
		}
	}
	
	public void setToMembersNum(int to_members_num){
		try{
			to.setMembersNum(to_members_num);
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when setting receipients' members num: ", ex);
		}
	}
	
	public void setFromMembersNum(int from_members_num){
		try{
			from.setMembersNum(from_members_num);
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when setting sender's members num: ", ex);
		}
	}
	
	// ------ Getter methods ------
	
	public Chat getFrom(){
		return from;
	}
	
	public Chat getTo(){
		return to;
	}
	
	public boolean getService(){
		return service;
	}
	
	public int getFlags(){
		return flags;
	}
	
	public int getID(){
		return id;
	}
	
	public int getDate(){
		return date;
	}
	
	public boolean getOutgoing(){
		return out;
	}
	
	public boolean getUnread(){
		return unread;
	}
	
	public String getText(){
		return text;
	}
	
//	public String[] getContents(){
//		return contents;
//	}
	
	public String[] getContents(int index) throws IndexOutOfBoundsException{
		if (index < length()){
			return commands.get(index);
		} else {
			throw new IndexOutOfBoundsException("No command with ID '" + String.valueOf(index)
					+ "' present: Only " + String.valueOf(length()) + " commands storde.");
		}
	}
	
	public int length(){
		return commands.size();
	}
	
	// ------ Advanced getter methods ------
	
	public int getFromID(){
		return from.getID();
	}
	
	public int getToID(){
		return to.getID();
	}
	
	public int getFromFlags(){
		return from.getFlags();
	}
	
	public int getToFlags(){
		return to.getFlags();
	}
	
	public String getFromFirstName(){
		try{
			return from.getFirstName();
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when getting sender's first name: ", ex);
			return User.default_first_name;
		}
	}
	
	public String getToFirstName(){
		try{
			return to.getFirstName();
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when getting receipients' first name: ", ex);
			return User.default_first_name;
		}
	}
	
	public String getFromLastName(){
		try{
			return from.getFirstName();
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when getting sender's last name: ", ex);
			return User.default_last_name;
		}
	}
	
	public String getToLastName(){
		try{
			return to.getLastName();
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when getting receipients' last name: ", ex);
			return User.default_last_name;
		}
	}
	
	public String getFromPrintName(){
		return from.getPrintName();
	}
	
	public String getToPrintName(){
		return to.getPrintName();
	}
	
	public String getFromTitle(){
		try{
			return from.getTitle();
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when getting sender's chat title: ", ex);
			return Group.default_title;
		}
	}
	
	public String getToTitle(){
		try{
			return to.getTitle();
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when getting receipients' chat title: ", ex);
			return Group.default_title;
		}
	}
	
	public long getFromPhone(){
		try{
			return from.getPhone();
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when getting sender's phone number: ", ex);
			return -1;
		}
	}
	
	public long getToPhone(){
		try{
			return to.getPhone();
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when getting receipients' phone number: ", ex);
			return -1;
		}
	}
	
	public int getFromMembersNum(){
		try{
			return from.getMembersNum();
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when getting sender's members number: ", ex);
			return -1;
		}
	}
	
	public int getToMembersNum(){
		try{
			return from.getMembersNum();
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when getting sender's members number: ", ex);
			return -1;
		}
	}
}