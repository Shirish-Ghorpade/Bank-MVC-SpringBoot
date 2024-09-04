package com.aurionpro.model.entity;

public class EmailData {

	private String from;
	private String to;
	private String subject;
	private String textBody;

	public EmailData(String from, String to, String subject, String textBody) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.textBody = textBody;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTextBody() {
		return textBody;
	}

	public void setTextBody(String textBody) {
		this.textBody = textBody;
	}

}
