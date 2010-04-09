package com.barefoot.smsdigger.retrieve;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

import com.google.gson.Gson;


public class SMSHolder {

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
	
	public String toJson() {
		HashMap<String, Object> smsHolderMapRepresentation = new HashMap<String, Object>();
		smsHolderMapRepresentation.put("id", Integer.valueOf(id));
		smsHolderMapRepresentation.put("score", Integer.valueOf(score));
		smsHolderMapRepresentation.put("contactId", contactId);
		smsHolderMapRepresentation.put("message", message);
		smsHolderMapRepresentation.put("sender", sender);
		
		return new Gson().toJson(smsHolderMapRepresentation);
	}

	public static SMSHolder createInstanceFrom(Map<String, String> fromJson) {
		String id = (String)fromJson.get("id");
		String score = (String)fromJson.get("score");
		String msg = (String) fromJson.get("message");
		String sender = null == fromJson.get("sender") ? null : (String)fromJson.get("sender");
		String contact = null == fromJson.get("contact") ? null : (String)fromJson.get("contact");
		
		return new SMSHolder(id, msg, sender, contact, score);
	}
	
	public ContactInfo getContactDetails(Activity activity) {
		
		if (contactInfo == null) {
			contactInfo = new ContactDetails(activity.getContentResolver()).getContactInfoFrom(sender);
		}
		return contactInfo;
	}
}
