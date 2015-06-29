package misc;

import logging.Logger;

public class User extends Chat {
	protected int phone = -1;
	protected String first_name = "User first-name";
	protected static final String default_first_name = "User first-name";
	protected String last_name = "User last-name";
	protected static final String default_last_name = "User last-name";
	
	public User(String first_name, String last_name, String print_name, int id, int phone, int flags){
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.print_name = print_name;
		this.id = id;
		this.phone = phone;
		this.flags = flags;
	}
	
	// ------ Setter Methods ------
	
	public void setPhone(int phone){
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
	
	public int getPhone(){
		return phone;
	}
	
	public String getFirstName(){
		return first_name;
	}
	
	public String getLastName(){
		return last_name;
	}
}
