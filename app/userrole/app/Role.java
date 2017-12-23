package app;

public class Role {

	private String name;
	
	private boolean contextRead;
	private boolean contextNew;
	
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	
	public void setContextRead(boolean val){
		contextRead = val;		
	}
	
	public boolean getContextRead() {
		return contextRead;
	}
	
	public void setContextNew(boolean val) {
		contextNew = val;
	}
	
	public boolean getContextNew() {
		return contextNew;
	}
	
	
}
