package userDatabase;

import java.util.Date;

public class Message {

	private int id;
	private Date timestamp;
	//private User author;
	//private User receiver;
	private String text;
	
	public Message()
	{
		
	}

	public Message(int id, Date timestamp, String text) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.text = text;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
