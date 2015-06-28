package misc;

public class Message {
	protected Chat from, to;
	protected boolean service = false;
	protected String text = "Message text";
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
	
	public void setFromPrintName(String from_print_name){
		from.setPrintName(from_print_name);
	}
	
	public void setToPrintName(String to_print_name){
		to.setPrintName(to_print_name);
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
}