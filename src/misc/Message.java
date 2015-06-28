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