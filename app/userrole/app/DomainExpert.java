package app;

public class DomainExpert extends Role{
	public DomainExpert(String name) {
		super.setName(name);
		
		// Beispiel Init
		super.setContextNew(true);
		super.setContextRead(true);
		
	}
	
	public String getName(){
		return super.getName();
	}
}
