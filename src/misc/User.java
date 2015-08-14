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

import org.json.JSONObject;

import logging.Logger;

public class User extends Chat {
	protected long phone = -1;
	protected String first_name = "User first-name";
	protected static final String default_first_name = "User first-name";
	protected String last_name = "User last-name";
	protected static final String default_last_name = "User last-name";
	
	public User(String first_name, String last_name, String print_name, int id, long phone, int flags){
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.print_name = print_name;
		this.id = id;
		this.phone = phone;
		this.flags = flags;
	}
	
	public User(){
		super();
		this.first_name = User.default_first_name;
		this.last_name = User.default_last_name;
		this.print_name = User.default_print_name;
		this.genPrintName();
		this.id = -1;
		this.phone = -1;
		this.flags = -1;
	}
	
	public User(JSONObject obj){
		super(obj);
		
		if (getType().equals("user")){
			// Chat is an User
			
			this.first_name = obj.getString("first_name");
			this.last_name = obj.getString("last_name");
			// Phone Number is to long for int
			this.phone = obj.getLong("phone");
		} else {
			Logger.logMessage('E', this, "Trying to construct an User chat with a non-Userchat-JSON-String!");
		}
	}
	
	public User(String jsonString){
		this(new JSONObject(jsonString));
	}
	
	// ------ Setter Methods ------
	
	public void setPhone(long phone){
		this.phone = phone;
	}
	
	public void setFirstName(String first_name){
		this.first_name = first_name;
	}
	
	public void setLastName(String last_name){
		this.last_name = last_name;
	}
	
	public void genPrintName(){
		if (!first_name.equals(default_first_name) &&
				!last_name.equals(default_last_name)){
			print_name = first_name + " " + last_name;
		} else if (!first_name.equals(default_first_name) &&
				last_name.equals(default_last_name)){
			print_name = first_name;
		} else if (first_name.equals(default_first_name) &&
				!last_name.equals(default_last_name)){
			print_name = last_name;
		} else {
			Logger.logMessage('E', this, "Couldn't generate print name. Neither first_name nor last_name nor both are set.");
			print_name = default_print_name;
		}
	}
	
	// ------ Getter methods ------
	
	public long getPhone(){
		return phone;
	}
	
	public String getFirstName(){
		return first_name;
	}
	
	public String getLastName(){
		return last_name;
	}
}
