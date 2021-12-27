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

package misc;

import logging.Logger;

import org.json.JSONObject;

public class Group extends Chat {
	protected String title = "Chat title";
	public static final String default_title = "Chat title";
	protected int members_num = -1;
	
	private boolean verbose = false;
	
	public Group(String title, String print_name, int id, int flags, int members_num){
		super();
		this.title = title;
		this.print_name = print_name;
		this.id = id;
		this.flags = flags;
		this.members_num = members_num;
	}
	
	public Group(JSONObject obj){
		super(obj);
		if (verbose) Logger.logMessage('I', this, "Constructing Group by JSON!");
		
		if(obj.getString("type").equals("user")){
			// Chat is a group chat
			
			this.title = obj.getString("title");
			this.members_num = obj.getInt("members_num");
		} else {
			// Chat is not a group chat
			Logger.logMessage('E', this, "Trying to construct a Group chat with a non-group-chat-JSON-String!");
		}
	}
	
	public Group(String jsonString){
		this(new JSONObject(jsonString));
		if (verbose) Logger.logMessage('I', this, "Constructed Group by JSONString...");
	}
	
	/// ------ Setter methods ------
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setMembersNum (int members_num){
		this.members_num = members_num;
	}
	
	// ------ Getter methods ------
	
	public String getTitle(){
		return title;
	}
	
	public int getMembersNum(){
		return members_num;
	}
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

package misc;

import logging.Logger;

import org.json.JSONObject;

public class Group extends Chat {
	protected String title = "Chat title";
	public static final String default_title = "Chat title";
	protected int members_num = -1;
	
	private boolean verbose = false;
	
	public Group(String title, String print_name, int id, int flags, int members_num){
		super();
		this.title = title;
		this.print_name = print_name;
		this.id = id;
		this.flags = flags;
		this.members_num = members_num;
	}
	
	public Group(JSONObject obj){
		super(obj);
		if (verbose) Logger.logMessage('I', this, "Constructing Group by JSON!");
		
		if(obj.has("type") ? obj.getString("type").equals("chat") : false){
			// Chat is a group chat
			
			this.title = obj.has("title") ? obj.getString("title") : Group.default_title;
			this.members_num = obj.has("members_num") ? obj.getInt("members_num") : -1;
		} else {
			// Chat is not a group chat
			Logger.logMessage('E', this, "Trying to construct a Group chat with a non-group-chat-JSON-String!");
		}
	}
	
	public Group(String jsonString){
		this(new JSONObject(jsonString));
		if (verbose) Logger.logMessage('I', this, "Constructed Group by JSONString...");
	}
	
	/// ------ Setter methods ------
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setMembersNum (int members_num){
		this.members_num = members_num;
	}
	
	// ------ Getter methods ------
	
	public String getTitle(){
		return title;
	}
	
	public int getMembersNum(){
		return members_num;
	}
}
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635
