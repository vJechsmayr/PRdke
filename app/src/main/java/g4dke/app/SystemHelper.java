package g4dke.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import userDatabase.DBValidator;
import userDatabase.Message;
import userDatabase.SystemMessage;
import userDatabase.SystemUser;

public  class SystemHelper {

	/*
	 * @author Marcel G
	 * A Class with several operations which several classes need like logout, getting current user, etc.
	 * */
	
	
	//Viktoria C:/Users/vikto/Flora-2/flora2
	// Philip D:\Users\Philip\Flora-2\flora2
	//Marcel C:/Users/Overloard/Flora-2/flora2

	
	public static final String PFAD = "C:/Users/Overloard/Flora-2/flora2";

	
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
	
	//To write Messages
	public static void WriteSystemMessage(String receiver,String text, String atomicOperation, String concernedRuleTerm, String containingContext )
	{
		SystemMessage message = (SystemMessage) BuildMessage(receiver, text);
		message.setAcknowledged(false);
		message.setAtomicOperation(atomicOperation);
		message.setConcernedRuleTerm(concernedRuleTerm);
		message.setContainingContext(containingContext);
		
		DBValidator.SaveSystemMessage(message);
	}
	
	public static void WriteMessage(String receiver,String text)
	{
		Message message = BuildMessage(receiver, text);
		DBValidator.SaveMessage(message);
	}
	
	private static Message BuildMessage(String receiver,String text)
	{
		Message message = new Message();
		message.setReceiver(receiver);
		message.setText(text);
		Date date = new Date();
		message.setTimestamp(date);
		message.setAuthor(getCurrentUser().getName());
		return message;
	}
}
