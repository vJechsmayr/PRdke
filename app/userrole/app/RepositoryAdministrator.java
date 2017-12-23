package app;

public class RepositoryAdministrator extends Role{
	public RepositoryAdministrator(String name) {
		super.setName(name);
		
		// Beispiel Init
		super.setContextNew(true);
		super.setContextRead(true);
		
	}
	
	public String getName(){
		return super.getName();
	}
}
