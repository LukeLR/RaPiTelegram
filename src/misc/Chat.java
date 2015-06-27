package misc;

public class Chat {
	protected int id = -1;
	protected int flags = -1;
	protected String print_name = "Chat print-name";
	protected static final String default_print_name = "Chat print-name";
	protected String type = "none";
	
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
}
