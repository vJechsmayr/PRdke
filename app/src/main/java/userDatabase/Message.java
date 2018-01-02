package userDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
 * @author Marcel G.
 * */
public class Message {

	private int id;
	private Date timestamp;
	private String author;
	private String receiver;
	private String text;
	
	public Message()
	{
		
	}

	public Message(int id, Date timestamp, String author, String receiver, String text) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.text = text;
		this.author = author;
		this.receiver = receiver;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getTimestampAsString()
	{
		DateFormat df = new SimpleDateFormat("dd.mm.yyyy");
		return df.format(this.getTimestamp());
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	
	
}
