package userDatabase;

import java.util.Date;

public class Message {

	private int id;
	private Date timestamp;
	private int authorID;
	private int receiverID;
	private String text;
	
	public Message()
	{
		
	}

	public Message(int id, Date timestamp, int authorId, int receiverId, String text) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.text = text;
		this.authorID = authorId;
		this.receiverID = receiverId;
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

	public int getAuthorID() {
		return authorID;
	}

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	public int getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(int receiverID) {
		this.receiverID = receiverID;
	}
	
	
}
