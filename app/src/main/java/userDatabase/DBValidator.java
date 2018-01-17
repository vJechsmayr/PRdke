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

	// TODO: Check paths
	static String path = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

	private static final String MESSAGE_CSV = path + "/csvFiles/Message.csv";
	private static final String MESSAGE_CSV_Header = "ID;Timestamp;Author;Receiver;Text";

	private static final String SYSTEMMESSAGE_CSV = path + "/csvFiles/SystemMessages.csv";
	private static final String SYSTEMMESSAGE_CSV_Header = "ID;Timestamp;Author;Receiver;Text;AtomicOperation;ConcernedRuleTerm;ContainingContext;ConcernedParameter;Acknowledged";

	private static final String SYSTEMUSER_CSV = path + "/csvFiles/SystemUsers.csv";
	private static final String SYSTEMUSER_CSV_Header = "ID;Name;Password;Role";
	
	private static final String OPERATION_CSV = path + "/csvFiles/Operations.csv";
	private static final String OPERATION_CSV_HEADER = "Name;CurrentPosition;Parameter;Rule;Context";
	
	private static final String csvSplitBy = ";";

	public static ArrayList<OperationPosition> getAllOperationPositions()
	{
		ArrayList<OperationPosition> list = new ArrayList<OperationPosition>();
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(OPERATION_CSV));
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] m = line.split(csvSplitBy);
				OperationPosition op = new OperationPosition();

				op.setName(m[0]);
				op.setCurrentPosition(Integer.parseInt(m[1]));
				if (!m[2].equals("-"))
					op.setParameter(m[2]);
				if (!m[3].equals("-"))
					op.setRule(m[3]);
				if (!m[4].equals("-"))
					op.setContext(m[4]);
				list.add(op);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	public static ArrayList<Message> getAllMessages() {
		ArrayList<Message> list = new ArrayList<Message>();
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(MESSAGE_CSV));
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] m = line.split(csvSplitBy);
				Message message = new Message();

				message.setId(Integer.parseInt(m[0]));
				SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
				Date help = formater.parse(m[1]);
				message.setTimestamp(help);
				message.setAuthor(m[2]);
				message.setReceiver(m[3]);
				if (!m[4].equals("-"))
					message.setText(m[4]);
				list.add(message);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public static ArrayList<SystemMessage> getAllSystemMessages() {
		ArrayList<SystemMessage> list = new ArrayList<SystemMessage>();
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(SYSTEMMESSAGE_CSV));
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] m = line.split(csvSplitBy);
				SystemMessage message = new SystemMessage();

				message.setId(Integer.parseInt(m[0]));
				SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
				Date help = formater.parse(m[1]);
				message.setTimestamp(help);
				message.setAuthor(m[2]);
				message.setReceiver(m[3]);
				if (!m[4].equals("-"))
					message.setText(m[4]);
				if (!m[5].equals("-"))
					message.setAtomicOperation(m[5]);
				if (!m[6].equals("-"))
					message.setConcernedRuleTerm(m[6]);
				if (!m[7].equals("-"))
					message.setContainingContext(m[7]);

				if(!m[8].equals("-"))
					message.setConcernedParameter(m[8]);
				message.setAcknowledged(Boolean.getBoolean(m[9]));
				list.add(message);
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public static ArrayList<SystemUser> getAllSystemUsers() {
		ArrayList<SystemUser> list = new ArrayList<SystemUser>();
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(SYSTEMUSER_CSV));
			// Throw first Line away
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] m = line.split(csvSplitBy);
				SystemUser user = new SystemUser();
				user.setId(Integer.parseInt(m[0]));
				user.setName(m[1]);
				user.setPassword(m[2]);
				user.setRole(m[3]);
				list.add(user);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;

	}
	
	public static boolean saveOperationPositions(ArrayList<OperationPosition> ops)
	{
		FileWriter fileWriter = null;
		boolean successfull = true;
		try {
			fileWriter = new FileWriter(OPERATION_CSV);
			fileWriter.append(OPERATION_CSV_HEADER);
			fileWriter.append(System.lineSeparator());

			for (OperationPosition o : ops) {
				fileWriter.append(o.getName());
				fileWriter.append(csvSplitBy);
				fileWriter.append(String.valueOf(o.getCurrentPosition()));
				fileWriter.append(csvSplitBy);
				if(o.getParameter().equals(""))
					fileWriter.append("-");
				else
					fileWriter.append(o.getParameter());
				fileWriter.append(csvSplitBy);
				if(o.getRule().equals(""))
					fileWriter.append("-");
				else
					fileWriter.append(o.getRule());
				fileWriter.append(csvSplitBy);
				if(o.getContext().equals(""))
					fileWriter.append("-");
				else
					fileWriter.append(o.getContext());
				fileWriter.append(csvSplitBy);
				fileWriter.append(System.lineSeparator());
			}
		} catch (Exception e) {
			e.printStackTrace();
			successfull = false;
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return successfull;
	}

	public static boolean saveMessages(ArrayList<Message> messages) {
		FileWriter fileWriter = null;
		boolean successfull = true;
		try {
			fileWriter = new FileWriter(MESSAGE_CSV);
			fileWriter.append(MESSAGE_CSV_Header);
			fileWriter.append(System.lineSeparator());

			for (Message m : messages) {
				fileWriter.append(String.valueOf(m.getId()));
				fileWriter.append(csvSplitBy);
				String help = m.getTimestampAsString();
				fileWriter.append(m.getTimestampAsString());
				fileWriter.append(csvSplitBy);
				fileWriter.append(String.valueOf(m.getAuthor()));
				fileWriter.append(csvSplitBy);
				fileWriter.append(String.valueOf(m.getReceiver()));
				fileWriter.append(csvSplitBy);
				if (m.getText().equals(""))
					fileWriter.append("-");
				else
					fileWriter.append(m.getText());
				fileWriter.append(System.lineSeparator());
			}
		} catch (Exception e) {
			e.printStackTrace();
			successfull = false;
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return successfull;
	}

	public static boolean saveSystemMessages(ArrayList<SystemMessage> messages) {
		FileWriter fileWriter = null;
		boolean successfull = true;
		try {
			fileWriter = new FileWriter(SYSTEMMESSAGE_CSV);
			fileWriter.append(SYSTEMMESSAGE_CSV_Header);
			fileWriter.append(System.lineSeparator());

			for (SystemMessage m : messages) {
				fileWriter.append(String.valueOf(m.getId()));
				fileWriter.append(csvSplitBy);
				fileWriter.append(m.getTimestampAsString());
				fileWriter.append(csvSplitBy);
				fileWriter.append(String.valueOf(m.getAuthor()));
				fileWriter.append(csvSplitBy);
				fileWriter.append(String.valueOf(m.getReceiver()));
				fileWriter.append(csvSplitBy);
				if (m.getText().equals(""))
					fileWriter.append("-");
				else
					fileWriter.append(m.getText());
				fileWriter.append(csvSplitBy);
				if (m.getAtomicOperation().equals(""))
					fileWriter.append("-");
				else
					fileWriter.append(m.getAtomicOperation());
				fileWriter.append(csvSplitBy);
				if (m.getConcernedRuleTerm().equals(""))
					fileWriter.append("-");
				else
					fileWriter.append(m.getConcernedRuleTerm());
				fileWriter.append(csvSplitBy);
				if (m.getContainingContext().equals(""))
					fileWriter.append("-");
				else
					fileWriter.append(m.getContainingContext());
				fileWriter.append(csvSplitBy);
				if (m.getConcernedParameter().equals(""))
					fileWriter.append("-");
				else
					fileWriter.append(m.getConcernedParameter());
				fileWriter.append(csvSplitBy);
				fileWriter.append(Boolean.toString(m.isAcknowledged()));
				fileWriter.append(System.lineSeparator());
			}
		} catch (Exception e) {
			e.printStackTrace();
			successfull = false;
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return successfull;
	}

	public static boolean saveSystemUsers(ArrayList<SystemUser> users) {
		FileWriter fileWriter = null;
		boolean successfull = true;
		try {
			fileWriter = new FileWriter(SYSTEMUSER_CSV);
			fileWriter.append(SYSTEMUSER_CSV_Header);
			fileWriter.append(System.lineSeparator());

			for (SystemUser user : users) {
				fileWriter.append(String.valueOf(user.getId()));
				fileWriter.append(csvSplitBy);
				fileWriter.append(user.getName());
				fileWriter.append(csvSplitBy);
				fileWriter.append(user.getPassword());
				fileWriter.append(csvSplitBy);
				fileWriter.append(user.getRole());

				fileWriter.append(System.lineSeparator());
			}
		} catch (Exception e) {
			e.printStackTrace();
			successfull = false;
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return successfull;
	}

	public static SystemUser getUser(String userName, String password) {
		ArrayList<SystemUser> users = DBValidator.getAllSystemUsers();
		for (SystemUser user : users) {
			if (user.getName().toUpperCase().equals(userName.toUpperCase()) && user.getPassword().equals(password)) {
				return user;
			}
		}

		return null;
	}

	public static ArrayList<Message> getInboxMessagesOfUser(SystemUser user) {
		ArrayList<Message> messages = DBValidator.getAllMessages();
		ArrayList<Message> userMessages = new ArrayList<Message>();
		for (Message m : messages) {
			if (m.getReceiver().toUpperCase().equals(user.getName().toUpperCase()))
				userMessages.add(m);
		}

		return userMessages;
	}

	public static ArrayList<SystemMessage> getInboxSystemMessagesOfUser(SystemUser user) {
		ArrayList<SystemMessage> messages = DBValidator.getAllSystemMessages();
		ArrayList<SystemMessage> userMessages = new ArrayList<SystemMessage>();
		for (SystemMessage m : messages) {
			if (m.getReceiver().toUpperCase().equals(user.getName().toUpperCase()))
				userMessages.add(m);
		}

		return userMessages;
	}

	public static ArrayList<Message> getOutboxMessagesOfUser(SystemUser user) {
		ArrayList<Message> messages = DBValidator.getAllMessages();
		ArrayList<Message> userMessages = new ArrayList<Message>();
		for (Message m : messages) {
			if (m.getAuthor().toUpperCase().equals(user.getName().toUpperCase()))
				;
			userMessages.add(m);
		}

		return userMessages;
	}

	public static ArrayList<SystemMessage> getOutboxSystemMessagesOfUser(SystemUser user) {
		ArrayList<SystemMessage> messages = DBValidator.getAllSystemMessages();
		ArrayList<SystemMessage> userMessages = new ArrayList<SystemMessage>();
		for (SystemMessage m : messages) {
			if (m.getAuthor().toUpperCase().equals(user.getName().toUpperCase()))
				userMessages.add(m);
		}

		return userMessages;
	}
	
	public static void SaveMessage(Message message)
	{
		//test
		ArrayList<Message> messages = DBValidator.getAllMessages();
		int id =0;
		for(Message m:messages)
		{
			if(m.getId()> id)
				id = m.getId();
		}
		message.setId(id);
		messages.add(message);
		DBValidator.saveMessages(messages);
	}
	
	public static void SaveSystemMessage(SystemMessage message)
	{
		ArrayList<SystemMessage> messages = DBValidator.getAllSystemMessages();
		int id =0;
		for(SystemMessage m:messages)
		{
			if(m.getId()> id)
				id = m.getId();
		}
		message.setId(id);
		messages.add(message);
		DBValidator.saveSystemMessages(messages);
	}
	
	public static void RemoveMessage(Message message)
	{
		ArrayList<Message> messages = DBValidator.getAllMessages();
		messages.remove(message);
		DBValidator.saveMessages(messages);
	}
	
	public static void RemoveSystemMessage(SystemMessage message)
	{
		ArrayList<SystemMessage> messages = DBValidator.getAllSystemMessages();
		messages.remove(message);
		DBValidator.saveSystemMessages(messages);
	}
	
	public static void saveOperationPosition(OperationPosition op)
	{
		ArrayList<OperationPosition> ops = DBValidator.getAllOperationPositions();
		ops.add(op);
		DBValidator.saveOperationPositions(ops);
	}

	public static void updateOperationPosition(OperationPosition op)
	{
		ArrayList<OperationPosition> ops = DBValidator.getAllOperationPositions();
		for(OperationPosition o : ops)
		{
			if(o.getName().equals(op.getName()))
			{
				if(o.getContext().equals(op.getContext()) && o.getRule().equals(op.getRule()) && o.getParameter().equals(op.getParameter()))
				{
					o.setCurrentPosition(op.getCurrentPosition());
				}
			}
		}
		DBValidator.saveOperationPositions(ops);
	}
}
