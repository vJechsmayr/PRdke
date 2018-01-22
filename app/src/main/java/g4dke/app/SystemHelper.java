package g4dke.app;

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
	// Philip D:/Users/Philip/Flora-2/flora2
	//Marcel C:/Users/Overloard/Flora-2/flora2
	

	public static final String PFAD = "D:/Users/Philip/Flora-2/flora2";

	//Rollen
	public static final String REPOSITORY_ADMINISTRATOR = "Repository Administrator";
	public static final String RULE_DEVELOPER = "Rule Developer";
	public static final String DOMAIN_EXPERT ="Domain Expert";
	public static final String USER = "User";
	
	//AtomicOperationen und Rollen
	public static final String CHANGECONTEXT = "Change Context";
	public static final String DELETE_RULE_FROM_CONTEXT = "Delete all Rules from Context";
	public static final String DELETE_CONTEXT ="Delete Context";
	public static final String NEW_CONTEXT = "New Context";
	public static final String DELETE_PARAMETER = "Delete Parameter";
	public static final String NEW_PARAMETER = "New Parameter";
	public static final String DELETE_RULE ="Delete Rule";
	public static final String NEW_RULE = "New Rule";
	public static final String NEW_PARAMETER_VALUE = "New Parameter Value";
	public static final String DELETE_PARAMETERVALUE = "Delete ParameterValue";
	public static final String NEW_PARAMETERVALUE = "New ParameterValue";

	//Composed Operation (Klassen Namen)
	public static final String COM_DELETE_CONTEXT ="DeleteContext";
	public static final String COM_MERGE_CONTEXT = "MergeContext";
	public static final String COM_NEW_CONTEXT = "NewContext";
	public static final String COM_SPLIT_CONTEXT = "SplitContext";
	public static final String COM_DELETE_PARAMETER ="DeleteParameter";
	public static final String COM_NEW_PARAMETER = "NewParameter";
	public static final String COM_MODIFY_RULE = "ModifyRule";
	public static final String COM_NEW_RULE = "NewRule";
	public static final String COM_DELETE_RULE = "DeleteRule";
	public static final String COM_CONTEXTUALIZE_RULE ="ContextualizeRule";
	public static final String COM_DECONTEXTUALIZE_RULE ="DeContextualizeRule";
	public static final String COM_DELETE_PARAMETERVALUE = "DeleteParameterValue";
	public static final String COM_NEW_PARAMETERVALUE = "NewParameterValue";
	
	//Last view for back button in messageservice
	public static String lastPage;
	
	//Current logged in User
	private static SystemUser user;
	
	public static void setCurrentUser(SystemUser currentUser)
	{
		user = currentUser;
	}
	
	public static SystemUser getCurrentUser()
	{
		//TODO:
		if(user==null)
		{
			SystemUser user = null;
			user = DBValidator.getUser("mgesslrd", "1234");
			return user;
		}
		else
			return user;
	}
	
	
	//check if composed Operation is started
	//if yes -> return object
	//if  not -> return null
	/**
	 * 
	 * 
	 * @param nameOfOperation Name of Operation
	 * @param parameter Name of Parameter
	 * @param rule Name of Rule
	 * @param context Name of Context
	 * 
	 * */
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
		user = null;
	}
	
	//To write Messages
	public static void WriteSystemMessage(String receiver,String text, String atomicOperation, String concernedRuleTerm, String containingContext, String concernedParameter, String[] additionalData )
	{
		SystemMessage message  = new SystemMessage();
		message.setReceiver(receiver);
		message.setText(text);
		Date date = new Date();
		message.setTimestamp(date);
		message.setAuthor(getCurrentUser().getName());
		message.setAcknowledged(false);
		message.setAtomicOperation(atomicOperation);
		message.setConcernedRuleTerm(concernedRuleTerm);
		message.setContainingContext(containingContext);
		message.setConcernedParameter(concernedParameter);
		message.setAdditionalData(additionalData);
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

	public static SystemUser getSpecificUser(String role) {
		ArrayList<SystemUser> users = DBValidator.getAllSystemUsers();
		for(SystemUser u : users)
		{
			if(u.getRole().toLowerCase().equals(role.toLowerCase()))
				return u;
		}
		return null;
	}
	
	private static OperationPosition GenerateOp(String role, String composedOperation, String atomicOperation, String rule, String context, String parameter) {
		return GenerateOp(role, composedOperation, atomicOperation, rule, context, parameter, new String[0]);	
	}
	
	private static OperationPosition GenerateOp(String role, String composedOperation, String atomicOperation, String rule, String context, String parameter, String[] additionalData)
	{
		OperationPosition op = null;
		SystemUser user = SystemHelper.getSpecificUser(role);
		SystemHelper.WriteSystemMessage(user.getName(), composedOperation, atomicOperation, rule, context, parameter, additionalData);
		op = new OperationPosition();
		op.setContext(context);
		op.setRule(rule);
		op.setParameter(parameter);
		op.setCurrentPosition(1);
		op.setName(composedOperation);
		DBValidator.saveOperationPosition(op);
		return op;
	}
	
	public static OperationPosition DeleteParameter(String parameter)
	{
		OperationPosition op = null;
		op = GenerateOp(SystemHelper.RULE_DEVELOPER, SystemHelper.COM_DELETE_PARAMETER, 
				SystemHelper.DELETE_RULE_FROM_CONTEXT, "", "", parameter);
		return op;
	}
	
	public static OperationPosition ContextualizeRule(String rule, String context)
	{
		OperationPosition op = null;
		op = GenerateOp(SystemHelper.RULE_DEVELOPER, SystemHelper.COM_CONTEXTUALIZE_RULE, 
				SystemHelper.CHANGECONTEXT, rule, context, "");
		return op;
	}
	
	public static OperationPosition DeContextualizeRule(String rule, String context)
	{
		OperationPosition op = null;
		op = GenerateOp(SystemHelper.RULE_DEVELOPER, SystemHelper.COM_CONTEXTUALIZE_RULE, 
				SystemHelper.CHANGECONTEXT, rule, context, "");
		return op;
	}
	/*
	 * Rule wird nicht benötigt da in einem COntext mehrere Rules sein können
	 * Der RuleDev. muss nur benachrichtigt werden alle Regeln aus einem bestimmten Context zu löschen!
	 * */
	public static OperationPosition DeleteContext(String rule, String context)
	{
		OperationPosition op = null;
		op = GenerateOp(SystemHelper.RULE_DEVELOPER, SystemHelper.COM_DELETE_CONTEXT, 
				SystemHelper.DELETE_RULE_FROM_CONTEXT, rule, context, "");
		return op;
	}
	
	public static OperationPosition DeleteContext(String context)
	{
		OperationPosition op = null;
		op = GenerateOp(SystemHelper.RULE_DEVELOPER, SystemHelper.COM_DELETE_CONTEXT, 
				SystemHelper.DELETE_RULE_FROM_CONTEXT, "", context, "");
		return op;
	}
	
	public static OperationPosition MergeContext(String rule, String context)
	{
		OperationPosition op = null;
		op = GenerateOp(SystemHelper.RULE_DEVELOPER, SystemHelper.COM_MERGE_CONTEXT, 
				SystemHelper.CHANGECONTEXT, rule, context, "");
		return op;
	}
	
	public static OperationPosition ModifyRule(String rule)
	{
		OperationPosition op = null;
		op = GenerateOp(SystemHelper.RULE_DEVELOPER, SystemHelper.COM_MODIFY_RULE, 
				SystemHelper.DELETE_RULE, rule, "", "");
		return op;
	}
	
	/**
	 * @author Viktoria J.
	 * 
	 * 
	 * */
	public static OperationPosition AddRule(String context, String rule)
	{
		OperationPosition op = null;
		op = GenerateOp(SystemHelper.RULE_DEVELOPER, SystemHelper.COM_NEW_RULE, 
				SystemHelper.NEW_RULE, rule, context, "");
		return op;
	}
	
	/**
	 * @author Viktoria J.
	 * 
	 * 
	 * */
	public static OperationPosition DeleteRule(String context, String rule)
	{
		OperationPosition op = null;
		op = GenerateOp(SystemHelper.RULE_DEVELOPER, SystemHelper.COM_DELETE_RULE, 
				SystemHelper.DELETE_RULE, rule, context, "");
		return op;
	}
	
	
	
	//TODO: how is it unique????
	/**
	 * @author Viktoria J.
	 * 
	 * @param context
	 * @param parentContext additionalData[0]
	 * 
	 */
	public static OperationPosition NewContext(String context, String parentContext)
	{
		OperationPosition op = null;
		String[] additionalData = new String[1];
		additionalData[0] = parentContext;
		op = GenerateOp(SystemHelper.REPOSITORY_ADMINISTRATOR, SystemHelper.COM_NEW_CONTEXT, 
				SystemHelper.NEW_PARAMETER_VALUE, "", context, "", additionalData);
		return op;
	}
	
	public static OperationPosition NewParameter(String parameter)
	{
		OperationPosition op = null;
		op = GenerateOp(SystemHelper.REPOSITORY_ADMINISTRATOR, SystemHelper.COM_NEW_PARAMETER, 
				SystemHelper.NEW_PARAMETER, "", "", parameter);
		return op;
	}
	
	/**
	 * @author Viktoria J.
	 * 
	 * @param rootValue additionalData[0]
	 * @param detParam additionalData[1]
	 * @param paramName
	 * 
	 * */
	public static OperationPosition NewParameter(String rootValue, String detParam, String paramName)
	{
		OperationPosition op = null;
		String[] additionalData = new String[2];
		additionalData[0] = rootValue;
		additionalData[1] = detParam;
		op = GenerateOp(SystemHelper.REPOSITORY_ADMINISTRATOR, SystemHelper.COM_NEW_PARAMETER, 
				SystemHelper.NEW_PARAMETER, "", "", paramName, additionalData);
		return op;
	}
	
	/**
	 * @author Viktoria J.
	 * 
	 * @param parameter
	 * @param parameterValue additionalData[0]
	 * 
	 * */
	public static OperationPosition DeleteParameterValue(String parameter, String parameterValue) {
		OperationPosition op = null;
		String[] additionalData = new String[1];
		additionalData[0] = parameterValue;
		op = GenerateOp(SystemHelper.REPOSITORY_ADMINISTRATOR, SystemHelper.COM_DELETE_PARAMETERVALUE, 
				SystemHelper.DELETE_PARAMETERVALUE, "", "", parameter, additionalData);
		return op;
	}
	
	/**
	 * 
	 * */
	public static OperationPosition AddParameterValue(String parameter, String parameterValue, String parent) {
		OperationPosition op = null;
		String[] additionalData = new String[2];
		additionalData[0] = parameterValue;
		additionalData[1] = parent;
		op = GenerateOp(SystemHelper.REPOSITORY_ADMINISTRATOR, SystemHelper.COM_NEW_PARAMETERVALUE, 
				SystemHelper.NEW_PARAMETERVALUE, "", "", parameter, additionalData);
		return op;
	}


	public static OperationPosition SplitContext(String context)
	{
		OperationPosition op = null;
		ArrayList<SystemUser> users = DBValidator.getAllSystemUsers();
		for(SystemUser u : users)
		{
			SystemHelper.WriteSystemMessage(u.getName(), SystemHelper.COM_SPLIT_CONTEXT, "", "", context, "", new String[0]);
		}
		

		return op;
	}
}
