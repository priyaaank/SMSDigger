package com.barefoot.smsdigger.workers;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.barefoot.smsdigger.SMSConstants;
import com.barefoot.smsdigger.data.SMSHolder;

public class SMSRetriever implements SMSConstants {

	private static String SMS_URI = "content://sms/";

	protected static String _ID = "_id";
	protected static String THREAD_ID = "thread_id";
	protected static String ADDRESS = "address";
	protected static String PERSON = "person";
	protected static String BODY = "body";
	protected static String DATE = "date";

	private TextParser textParser;

	public SMSRetriever(String[] keywords) {
		this.textParser = new TextParser(keywords);
	}

	public SMSRetriever(TextParser parser) {
		this.textParser = parser;
	}

	protected Uri getContentURI() {
		return Uri.parse(SMS_URI);
	}

	protected String[] fieldsToFetch() {
		return new String[] { _ID, THREAD_ID, ADDRESS, PERSON, BODY };
	}

	protected String whereClause() {
		return " body is not null ";
	}

	protected String sortOrder() {
		return null;
	}

	protected String[] selectionArguments() {
		return null;
	}
	
	protected TextParser getTextParser() {
		return textParser;
	}

	public ArrayList<SMSHolder> fetchMessages(ContentResolver contentResolver, int messageSource) {
		Cursor messages = null;
		try {
			messages = contentResolver.query(getContentURI(), fieldsToFetch(),
					whereClause(), selectionArguments(), sortOrder());

			if (messages != null && messages.moveToFirst()) {
				return extractFieldsFrom(messages, messageSource);
			}
		} finally {
			messages.close();
		}

		return new ArrayList<SMSHolder>();
	}

	protected ArrayList<SMSHolder> extractFieldsFrom(Cursor messages, int sourceFolder) {
		int idIndex = messages.getColumnIndex(_ID);
		int bodyIndex = messages.getColumnIndex(BODY);
		int senderIndex = messages.getColumnIndex(ADDRESS);
		int contactIdIndex = messages.getColumnIndex(PERSON);
		ArrayList<SMSHolder> smsHolders = new ArrayList<SMSHolder>();
		SMSHolder matchingMessage;

		do {
			matchingMessage = matches(messages.getString(bodyIndex));
			if (null != matchingMessage) {
				matchingMessage.setId(Integer
						.toString(messages.getInt(idIndex)));
				matchingMessage.setSender(messages.getString(senderIndex));
				matchingMessage
						.setContactId(messages.getString(contactIdIndex));
				matchingMessage.setSourceFolder(sourceFolder);
				smsHolders.add(matchingMessage);
			}
		} while (messages.moveToNext());

		return smsHolders;
	}

	protected SMSHolder matches(String message) {
		int matchScore = getTextParser().matchCountFor(message);
		return matchScore == 0 ? null : new SMSHolder("0", message, null, null,
				Integer.toString(matchScore), INBOX);
	}
}
