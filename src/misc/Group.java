package misc;

public class Group extends Chat {
	protected String title = "Chat title";
	public static final String default_title = "Chat title";
	protected int members_num = -1;
	
	public Group(String title, String print_name, int id, int flags, int members_num){
		super();
		this.title = title;
		this.print_name = print_name;
		this.id = id;
		this.flags = flags;
		this.members_num = members_num;
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
