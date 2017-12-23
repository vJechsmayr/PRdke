package app;

public class User extends Role{

	public User(String name) {
		super.setName(name);
		
		// Beispiel Init
		super.setContextNew(false);
		super.setContextRead(true);
	}
	
}
