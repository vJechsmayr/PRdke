package g4dke.app;

import userDatabase.DBValidator;
import userDatabase.SystemUser;

public  class SystemHelper {

	/*
	 * @author Marcel G
	 * A Class with several operations which several classes need like logout, getting current user, etc.
	 * */
	
	public static SystemUser getCurrentUser()
	{
		//TODO:
		SystemUser user = null;
		user = DBValidator.getUser("mgesslde", "1234");
		return user;
	}
	
	public static void logout()
	{
		//TODO:
	}
}
