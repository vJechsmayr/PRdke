package userDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBValidator {
	
	private static final String MESSAGE_CSV ="";
	private static final String MESSAGE_CSV_Header="ID,Timestamp,Author,Receiver,Text";
	private static final String SYSTEMMESSAGE_CSV ="";
	private static final String SYSTEMMESSAGE_CSV_Header="ID,Timestamp,Author,Receiver,Text,AtomicOperation,ConcernedRuleTerm,ContainingContext";
	private static final String csvSplitBy =",";
	
	
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
				SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
				Date help = formater.parse(m[1]);
				message.setTimestamp(help);
				message.setAuthorID(Integer.parseInt(m[2]));
				message.setReceiverID(Integer.parseInt(m[3]));
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
				message.setAuthorID(Integer.parseInt(m[2]));
				message.setReceiverID(Integer.parseInt(m[3]));
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
				fileWriter.append(String.valueOf(m.getAuthorID()));
				fileWriter.append(csvSplitBy);
				fileWriter.append(String.valueOf(m.getReceiverID()));
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
				fileWriter.append(String.valueOf(m.getAuthorID()));
				fileWriter.append(csvSplitBy);
				fileWriter.append(String.valueOf(m.getReceiverID()));
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

}
