package org.zkoss.keyfeature3;

public class SchedulerEvent {
	private String id;
	private String start_date;
	private String end_date;
	private String subject;
	private String text;

	public SchedulerEvent(String id, String start_date, String end_date, String subject, String text) {
		this.id = id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.subject = subject;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
