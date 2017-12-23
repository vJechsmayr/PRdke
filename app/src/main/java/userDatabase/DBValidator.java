package userDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBValidator {
	
	private static final String MESSAGE_CSV ="";
	private static final String SYSTEMMESSAGE_CSV ="";
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
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public static boolean saveMessage(Message m)
	{
		
		return true;
	}

}
