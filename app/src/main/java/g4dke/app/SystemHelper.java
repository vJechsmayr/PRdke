package g4dke.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import userDatabase.DBValidator;
import userDatabase.Message;
import userDatabase.OperationPosition;
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

	public static final String PFAD = "C:/Users/vikto/Flora-2/flora2";
	
	//AtomicOperationen und Rollen
	public static final String CHANGECONTEXT = "Change Context";
	public static final String RULE_DEVELOPER = "Rule Developer";
	public static final String DELETE_RULE_FROM_CONTEXT = "Delete Rule from Context";
	public static final String DELETE_CONTEXT ="Delete Context";
	public static final String REPOSITORY_ADMINISTRATOR = "Repository Administrator";
	public static final String DELETE_PARAMETER = "Delete Parameter";
	public static final String DELETE_RULE ="Delete Rule";
	public static final String NEW_RULE = "New Rule";
	public static final String NEW_PARAMETER_VALUE = "New Parameter Value";
	public static final String NEW_CONTEXT = "New Context";
	public static final String NEW_PARAMETER = "New Parameter";
	
	//Composed Operation (Klassen Namen)
	public static final String COM_CONTEXTUALIZE_RULE ="ContextualizeRule";
	public static final String COM_DECONTEXTUALIZE_RULE ="DeContextualizeRule";
	public static final String COM_DELETE_CONTEXT ="DeleteContext";
	public static final String COM_DELETE_Parameter ="DeleteParameter";
	public static final String COM_MERGE_CONTEXT = "MergeContext";
	public static final String COM_MODIFY_RULE = "ModifyRule";
	public static final String COM_NEW_CONTEXT = "NewContext";
	public static final String COM_NEW_PARAMETER = "NewParameter";
	
	
	public static SystemUser getCurrentUser()
	{
		//TODO:
		SystemUser user = null;
		user = DBValidator.getUser("mgesslde", "1234");
		return user;
	}
	
	
	//check if composed Operation is started
	//if yes -> return object
	//if  not -> return null
	public static OperationPosition isComposedOperationsStarted(String nameOfOperation, String parameter, String rule, String context)
	{
		
		for(OperationPosition op : DBValidator.getAllOperationPositions())
		{
			if(op.getName().toLowerCase().equals(nameOfOperation.toLowerCase()))
			{
				if(!parameter.equals("") && op.getParameter().toLowerCase().equals(parameter.toLowerCase()))
				{
					return op;
				}
				if(!rule.equals("") && op.getRule().toLowerCase().equals(rule.toLowerCase())) {
					return op;
				}
				if(!context.equals("") && op.getContext().toLowerCase().equals(context.toLowerCase()))
				{
					return op;
				}
			}
		}
		
		return null;
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
