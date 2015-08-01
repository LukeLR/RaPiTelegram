package misc;

import java.io.Serializable;

import logging.Logger;

public class Account implements Serializable{
	private String name = "null";
	private int id = -1;
	
	public Account(String name, int id){
		this.name = name;
		this.id = id;
	}
	
	public Account(int id){
		this.name = "null";
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public String getName(){
		if (name.equals("null")){
			return null;
		} else {
			return name;
		}
	}
	
	public int getID(){
		return id;
	}
	
	public boolean equals(Object compare){
		try{
			return ((Account) compare).getID() == getID();
		} catch (Exception ex){
			Logger.logMessage('W', this, "Comparing failed! Is the comparison object of type 'Account'?");
			return false;
		}
	}
}
