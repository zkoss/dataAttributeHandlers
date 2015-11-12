package org.zkoss.keyfeature1;

public class Message {
	public User author;
	public String content;

	public User getAuthor() {
		return author;
	}
	public String getContent() {
		return content;
	}
	
	public Message(User author, String content) {
		super();
		this.author = author;
		this.content = content;
	}
}
