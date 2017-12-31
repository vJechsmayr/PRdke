package userDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;

import com.vaadin.server.VaadinService;
/*
 * @author Marcel G.
 * MainDev: Marcel
 * Path Failure Correction: Viktoria
 * */
public class DBValidator {
	
	 //TODO: Check paths
 static String path = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

	private static final String MESSAGE_CSV = path + "/csvFiles/Message.csv";
	private static final String MESSAGE_CSV_Header="ID,Timestamp,Author,Receiver,Text";
	
	private static final String SYSTEMMESSAGE_CSV = path + "/csvFiles/SystemMessages.csv";
	private static final String SYSTEMMESSAGE_CSV_Header="ID,Timestamp,Author,Receiver,Text,AtomicOperation,ConcernedRuleTerm,ContainingContext";
	
	private static final String SYSTEMUSER_CSV = path + "/csvFiles/SystemUsers.csv";
	private static final String SYSTEMUSER_CSV_Header="ID,Name,Password,Role";
	private static final String csvSplitBy =";";
	
	
	
	public static ArrayList<Message> getAllMessages()
	{
		ArrayList<Message> list = new ArrayList<Message>();
		BufferedReader br = null;
		String line ="";
		try {
			br = new BufferedReader(new FileReader(MESSAGE_CSV));
			br.readLine();
			while((line=br.readLine())!=null)
			{
				String[]m = line.split(csvSplitBy);
				Message message = new Message();
				
				message.setId(Integer.parseInt(m[0]));
				SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
				Date help = formater.parse(m[1]);
				message.setTimestamp(help);
				message.setAuthor(m[2]);
				message.setReceiver(m[3]);
				message.setText(m[4]);
				list.add(message);
			}
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	public static ArrayList<SystemMessage> getAllSystemMessages()
	{
		ArrayList<SystemMessage> list = new ArrayList<SystemMessage>();
		BufferedReader br = null;
		String line ="";
		try {
			br = new BufferedReader(new FileReader(SYSTEMMESSAGE_CSV));
			br.readLine();
			while((line=br.readLine())!=null)
			{
				String[]m = line.split(csvSplitBy);
				SystemMessage message = new SystemMessage();
				
				message.setId(Integer.parseInt(m[0]));
				SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
				Date help = formater.parse(m[1]);
				message.setTimestamp(help);
				message.setAuthor(m[2]);
				message.setReceiver(m[3]);
				message.setText(m[4]);
				message.setAtomicOperation(m[5]);
				message.setConcernedRuleTerm(m[6]);
				message.setContainingContext(m[7]);
				
				list.add(message);
			}
			br.close();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	public static ArrayList<SystemUser> getAllSystemUsers()
	{
		ArrayList<SystemUser> list = new ArrayList<SystemUser>();
		BufferedReader br = null;
		String line ="";
		try {
			br = new BufferedReader(new FileReader(SYSTEMUSER_CSV));
			//Throw first Line away
			br.readLine();
			while((line=br.readLine())!=null)
			{
				String[]m = line.split(csvSplitBy);
				SystemUser user = new SystemUser();
				user.setId(Integer.parseInt(m[0]));
				user.setName(m[1]);
				user.setPassword(m[2]);
				user.setRole(m[3]);
				list.add(user);
			}
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return list;

	}

	public static boolean saveMessages(ArrayList<Message> messages)
	{
		FileWriter fileWriter = null;
		boolean successfull = true;
		try
		{
			fileWriter = new FileWriter(MESSAGE_CSV);
			fileWriter.append(MESSAGE_CSV_Header);
			fileWriter.append(System.lineSeparator());
			
			for(Message m : messages)
			{
				fileWriter.append(String.valueOf(m.getId()));
				fileWriter.append(csvSplitBy);
				fileWriter.append(m.getTimestamp().toString());
				fileWriter.append(csvSplitBy);
				fileWriter.append(String.valueOf(m.getAuthor()));
				fileWriter.append(csvSplitBy);
				fileWriter.append(String.valueOf(m.getReceiver()));
				fileWriter.append(csvSplitBy);
				fileWriter.append(m.getText());
				fileWriter.append(System.lineSeparator());
			}
		}
		catch (Exception e){
			e.printStackTrace();
			successfull=  false;
		}
		finally {
			try {
			fileWriter.flush();
			fileWriter.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return successfull;
	}
	
	public static boolean saveSystemMessages(ArrayList<SystemMessage> messages)
	{
		FileWriter fileWriter = null;
		boolean successfull = true;
		try
		{
			fileWriter = new FileWriter(SYSTEMMESSAGE_CSV);
			fileWriter.append(SYSTEMMESSAGE_CSV_Header);
			fileWriter.append(System.lineSeparator());
			
			for(SystemMessage m : messages)
			{
				fileWriter.append(String.valueOf(m.getId()));
				fileWriter.append(csvSplitBy);
				fileWriter.append(m.getTimestamp().toString());
				fileWriter.append(csvSplitBy);
				fileWriter.append(String.valueOf(m.getAuthor()));
				fileWriter.append(csvSplitBy);
				fileWriter.append(String.valueOf(m.getReceiver()));
				fileWriter.append(csvSplitBy);
				fileWriter.append(m.getText());
				fileWriter.append(csvSplitBy);
				fileWriter.append(m.getAtomicOperation());
				fileWriter.append(csvSplitBy);
				fileWriter.append(m.getConcernedRuleTerm());
				fileWriter.append(csvSplitBy);
				fileWriter.append(m.getContainingContext());
				fileWriter.append(System.lineSeparator());
			}
		}
		catch (Exception e){
			e.printStackTrace();
			successfull=  false;
		}
		finally {
			try {
			fileWriter.flush();
			fileWriter.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return successfull;
	}
	
	public static boolean saveSystemUsers(ArrayList<SystemUser> users)
	{
		FileWriter fileWriter = null;
		boolean successfull = true;
		try
		{
			fileWriter = new FileWriter(SYSTEMUSER_CSV);
			fileWriter.append(SYSTEMUSER_CSV_Header);
			fileWriter.append(System.lineSeparator());
			
			for(SystemUser user : users)
			{
				fileWriter.append(String.valueOf(user.getId()));
				fileWriter.append(csvSplitBy);
				fileWriter.append(user.getName());
				fileWriter.append(csvSplitBy);
				fileWriter.append(user.getPassword());
				fileWriter.append(csvSplitBy);
				fileWriter.append(user.getRole());
		
				fileWriter.append(System.lineSeparator());
			}
		}
		catch (Exception e){
			e.printStackTrace();
			successfull=  false;
		}
		finally {
			try {
			fileWriter.flush();
			fileWriter.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return successfull;
	}
	
	public static SystemUser getUser(String userName, String password)
	{
		ArrayList<SystemUser> users = DBValidator.getAllSystemUsers();
		for(SystemUser user : users)
		{
			if(user.getName().toUpperCase().equals(userName.toUpperCase()) && user.getPassword().equals(password))
			{
				return user;
			}
		}
		
		return null;
	}
	
	public static ArrayList<Message> getInboxMessagesOfUser(SystemUser user)
	{
		ArrayList<Message> messages = DBValidator.getAllMessages();
		ArrayList<Message> userMessages = new ArrayList<Message>();
		for(Message m : messages)
		{
			if(m.getReceiver().toUpperCase().equals(user.getName().toUpperCase()))
				userMessages.add(m);
		}
		
		return userMessages;
	}
	
	public static ArrayList<SystemMessage> getInboxSystemMessagesOfUser(SystemUser user)
	{
		ArrayList<SystemMessage> messages = DBValidator.getAllSystemMessages();
		ArrayList<SystemMessage> userMessages = new ArrayList<SystemMessage>();
		for(SystemMessage m : messages)
		{
			if(m.getReceiver().toUpperCase().equals(user.getName().toUpperCase()))
				userMessages.add(m);
		}
		
		return userMessages;
	}

	public static ArrayList<Message> getOUtboxMessagesOfUser(SystemUser user)
	{
		ArrayList<Message> messages = DBValidator.getAllMessages();
		ArrayList<Message> userMessages = new ArrayList<Message>();
		for(Message m : messages)
		{
			if(m.getAuthor().toUpperCase().equals(user.getName().toUpperCase()));
				userMessages.add(m);
		}
		
		return userMessages;
	}
	
	public static ArrayList<SystemMessage> getOutboxSystemMessagesOfUser(SystemUser user)
	{
		ArrayList<SystemMessage> messages = DBValidator.getAllSystemMessages();
		ArrayList<SystemMessage> userMessages = new ArrayList<SystemMessage>();
		for(SystemMessage m : messages)
		{
			if(m.getAuthor().toUpperCase().equals(user.getName().toUpperCase()))
				userMessages.add(m);
		}
		
		return userMessages;
	}
}
