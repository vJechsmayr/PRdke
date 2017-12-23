package app;

public class test {

	public static void main(String[] args) {

		RuleDeveloper marcel = new RuleDeveloper("Marcel");
		User viktoria = new User("Viktoria");
		RepositoryAdministrator thomas = new RepositoryAdministrator("Thomas");
		DomainExpert philip = new DomainExpert("Philip");
		
		
		showRights(marcel);
		showRights(viktoria);
		showRights(thomas);
		showRights(philip);
		
	}
	
	public static void showRights(Role x) {
		System.out.println("Name: " + x.getName());
		System.out.println("ContextRead: " + x.isContextClassRead());
		System.out.println("ContextNew: " + x.isContextClassUpdate());
		System.out.println("--------------------------------");
	}
}
