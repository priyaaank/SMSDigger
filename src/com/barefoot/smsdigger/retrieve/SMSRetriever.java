package com.barefoot.smsdigger.retrieve;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.barefoot.smsdigger.parser.TextParser;

public class SMSRetriever {

	public static String SMS_URI = "content://sms/";

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

	public Uri getContentURI() {
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

	public ArrayList<SMSHolder> fetchMessages(ContentResolver contentResolver) {
		Cursor messages = null;
		try {
			messages = contentResolver.query(getContentURI(), fieldsToFetch(),
					whereClause(), selectionArguments(), sortOrder());

			if (messages != null && messages.moveToFirst()) {
				return extractFieldsFrom(messages);
			}
		} finally {
			messages.close();
		}

		return null;
	}

	protected ArrayList<SMSHolder> extractFieldsFrom(Cursor messages) {
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
				smsHolders.add(matchingMessage);
			}
		} while (messages.moveToNext());

		return smsHolders;
	}

	protected SMSHolder matches(String message) {
		int matchScore = textParser.matchCountFor(message);
		return matchScore == 0 ? null : new SMSHolder("0", message, null, null,
				Integer.toString(matchScore));
	}
}
