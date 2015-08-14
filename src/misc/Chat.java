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

public class Chat {
	protected int id = -1;
	protected int flags = -1;
	protected String print_name = "Chat print-name";
	protected static final String default_print_name = "Chat print-name";
	protected String type = "none";
	protected static final String default_type = "none";
	
	private boolean sendAdditionalErrorMessages = false;
	private boolean warnJSONConstructor = true;
	
	public Chat(){
		this.id = -1;
		this.flags = -1;
		this.print_name = default_print_name;
		this.type = "none";
	}
	
	public Chat(int id, int flags, String print_name, String type){
		this.id = id;
		this.flags = flags;
		this.print_name = print_name;
		this.type = type;
	}
	
	public Chat(String please, String use, int the, int subclasses, int this_wont_work){
		Logger.logMessage('E', this, "This Chat object is not a Group!");
	}
	
	public Chat(String please, String use, String the, int subclasses, int this_, int wont_work){
		Logger.logMessage('E', this, "This Chat obejct is not a User!");
	}
	
	public Chat(JSONObject obj){
		if (warnJSONConstructor) Logger.logMessage('W', this, "Trying to construct a Chat object with a JSON Object.\n"
				+ "This should not be called directly, only subclasses should call this superclass constructor.\n"
				+ "Please use the constructors of the subclasses *User* and *Group*!");
		
		setID(obj.has ("id") ? obj.getInt("id") : -1);
		setFlags(obj.has("flags") ? obj.getInt("flags") : -1);
		setPrintName(obj.has("print_name") ? obj.getString("print_name") : Chat.default_print_name);
		setType(obj.has("type") ? obj.getString("type") : Chat.default_type);
	}
	
	public Chat(String jsonString){
		this(new JSONObject(jsonString));
//		if (warnJSONConstructor) Logger.logMessage('W', this, "Trying to construct a Chat object with a JSON String.\n"
//				+ "This should not be called directly, only subclasses should call this superclass constructor.\n"
//				+ "Please use the constructors of the subclasses *User* and *Group*!");
	}
	
	// ------ Setter methods ------
	
	public void setID(int id){
		this.id = id;
	}
	
	public void setFlags(int flags){
		this.flags = flags;
	}
	
	public void setPrintName(String print_name){
		this.print_name = print_name;
	}
	
	public void genPrintName(){
		print_name = "chat#" + String.valueOf(id);
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	// ------ Getter methods ------
	
	public int getID(){
		return id;
	}
	
	public int getFlags(){
		return flags;
	}
	
	public String getPrintName(){
		return print_name;
	}
	
	public String getType(){
		return type;
	}
	
	// ------ Empty setter methods (so Chat class has methods of subclasses) ------
	
	public void setPhone(long phone) throws FieldNotFoundException{
		if (sendAdditionalErrorMessages) Logger.logMessage('E', this, "This object doesn't save phone numbers!");
		throw new FieldNotFoundException("This object doesn't save phone numbers!");
	}
	
	public void setFirstName(String first_name) throws FieldNotFoundException{
		if (sendAdditionalErrorMessages) Logger.logMessage('E', this, "This object doesn't save first names!");
		throw new FieldNotFoundException("This object doesn't save first names!");
	}
	
	public void setLastName(String last_name) throws FieldNotFoundException{
		if (sendAdditionalErrorMessages) Logger.logMessage('E', this, "This object doesn't save last names!");
		throw new FieldNotFoundException("This object doesn't save last names!");
	}
	
	public void setTitle(String title) throws FieldNotFoundException {
		if (sendAdditionalErrorMessages) Logger.logMessage('E', this, "This object doesn't save chat titles!");
		throw new FieldNotFoundException("This object doesn't save chat titles!");
	}
	
	public void setMembersNum(int members_num) throws FieldNotFoundException {
		if (sendAdditionalErrorMessages) Logger.logMessage('E', this, "This object doesn't save member numbers!");
		throw new FieldNotFoundException("This object doesn't save member numbers!");
	}
	
	// ------ Empty getter methods (so Chat class has methods of subclasses) ------
	
	public long getPhone() throws FieldNotFoundException{
		if (sendAdditionalErrorMessages) Logger.logMessage('E', this, "This object has no phone number!");
		throw new FieldNotFoundException("This object has no phone number!");
	}
	
	public String getFirstName() throws FieldNotFoundException {
		if (sendAdditionalErrorMessages) Logger.logMessage('E', this, "This object has no first name!");
		throw new FieldNotFoundException("This object has no first name!");
	}
	
	public String getLastName() throws FieldNotFoundException {
		if (sendAdditionalErrorMessages) Logger.logMessage('E', this, "This object has no last name!");
		throw new FieldNotFoundException("This object has no last name!");
	}
	
	public String getTitle() throws FieldNotFoundException {
		if (sendAdditionalErrorMessages) Logger.logMessage('E', this, "This object has no chat title!");
		throw new FieldNotFoundException("This object has no chat title!");
	}
	
	public int getMembersNum() throws FieldNotFoundException {
		if (sendAdditionalErrorMessages) Logger.logMessage('E', this, "This object has no member number!");
		throw new FieldNotFoundException("This object has no member number!");
	}
}
