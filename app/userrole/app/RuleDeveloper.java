package app;

public class RuleDeveloper extends Role{

	public RuleDeveloper(String name) {
		super.setName(name);
		
		// Beispiel Init
		super.setContextNew(true);
		super.setContextRead(true);
		
	}
	
	public String getName(){
		return super.getName();
	}
	
}
