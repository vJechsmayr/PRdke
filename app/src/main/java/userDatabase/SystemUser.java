package userDatabase;
/*
 * @author Marcel G.
 * */
public class SystemUser {

	private int id;
	private String name;
	private String password;
	private String role;
	
	public SystemUser()
	{
		
	}
	
	public SystemUser(int id, String name, String password, String role)
	{
		this.id = id;
		this.name = name;
		this.password = password;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
