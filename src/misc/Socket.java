package misc;

public class Socket {
	private String name = "null";
	private int unitID = -1;
	private int socketID = -1;
	private boolean currentState = false;
	
	public Socket(String name, int unitID, int socketID){
		this.name = name;
		this.unitID = unitID;
		this.socketID = socketID;
		this.currentState = false;
	}
	
	public Socket(String name, int unitID, int socketID, boolean currentState){
		this.name = name;
		this.unitID = unitID;
		this.socketID = socketID;
		this.currentState = currentState;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setUnitID(int unitID){
		this.unitID = unitID;
	}
	
	public void setSocketID(int socketID){
		this.socketID = socketID;
	}
	
	public void setState(boolean state){
		this.currentState = state;
	}
	
	public void switchState(){
		currentState = !currentState;
	}
	
	public String getName(){
		return name;
	}
	
	public int getUnitID(){
		return unitID;
	}
	
	public int getSocketID(){
		return socketID;
	}
	
	public boolean getState(){
		return currentState;
	}
}
