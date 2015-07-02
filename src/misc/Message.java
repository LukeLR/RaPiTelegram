package misc;

import org.json.JSONObject;

import logging.Logger;

public class Message {
	protected Chat from, to;
	protected boolean service = false;
	protected int flags = -1;
	protected String text = "Message text";
	protected String[] contents = null;
	public static final String default_text = "Message text";
	protected int id = -1;
	protected int date = -1;
	protected boolean out = false;
	protected boolean unread = true;
	
	public Message(Chat from, Chat to, boolean service, int id, int date, boolean out, boolean unread, String text){
		this.from = from;
		this.to = to;
		this.service = service;
		this.id = id;
		this.date = date;
		this.out = out;
		this.unread = unread;
		this.text = text;
	}
	
	public Message(JSONObject obj){
		if (obj.getString("event").equals("message")){
			JSONObject fromObject = obj.getJSONObject("from");
			JSONObject toObject = obj.getJSONObject("to");
			
			// reading standard fields
			setService(obj.getBoolean("service"));
			setFlags(obj.getInt("flags"));
			setText(obj.getString("text"));
			genContents();
			setID(obj.getInt("id"));
			setDate(obj.getInt("date"));
			setOutgoing(obj.getBoolean("out"));
			setUnread(obj.getBoolean("unread"));
			
			//reading sender fields
			
		} else {
			Logger.logMessage('E', this, "Given JSONObject is no message!");
		}
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
	}
	
	public void genContents(){
		if (!text.equals(default_text)){
			contents = text.trim().split("\\s");
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
	
	public void setToPhone(int to_phone){
		try{
			to.setPhone(to_phone);
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when setting receipients' phone number: ", ex);
		}
	}
	
	public void setFromPhone(int from_phone){
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
	
	public String[] getContents(){
		return contents;
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
	
	public int getFromPhone(){
		try{
			return from.getPhone();
		} catch (FieldNotFoundException ex){
			Logger.logException(this, "Error when getting sender's phone number: ", ex);
			return -1;
		}
	}
	
	public int getToPhone(){
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