package com.barefoot.smsdigger.data;

import com.barefoot.smsdigger.SMSConstants;

import android.app.Activity;


public class SMSHolder implements SMSConstants {

	private String id;

	private String message;

	private String sender;

	private String contactId;
	
	private String score;
	
	private ContactInfo contactInfo;
	
	public SMSHolder(String id, String message, String sender, String contactId, String score) {
		this.id = id;
		this.message = message;
		this.sender = sender;
		this.contactId = contactId;
		this.score = score;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return null == message? "" : message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	
	public String getScore() {
		return this.score;
	}
	
	public void setScore(String score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		StringBuffer smsString = new StringBuffer("");
		smsString.append("[");
		smsString.append(getSender());
		smsString.append("] ");
		smsString.append(getMessage());
		return smsString.toString();
	}
	
	public String getShortenedMessage() {
		int length = getMessage().length() > MSG_LENGTH ? MSG_LENGTH : getMessage().length();
		return (getMessage().substring(0, length) + "..."); 
	}
	
	public ContactInfo getContactDetails(Activity activity) {
		if (contactInfo == null) {
			contactInfo = new ContactDetails(activity.getContentResolver()).getContactInfoFrom(sender);
		}
		return contactInfo;
	}
}
