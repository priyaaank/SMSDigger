package com.barefoot.smsdigger.workers;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.net.Uri;

import com.barefoot.smsdigger.SMSConstants;
import com.barefoot.smsdigger.data.SMSHolder;

public class SearchAggregator extends SMSRetriever implements SMSConstants {

	private static String INBOX_URI = "content://sms/inbox";
	private static String SENT_URI = "content://sms/sent";
	private static String DRAFT_URI = "content://sms/draft";
	
	private String currentURI = "content://sms/";
	private ContentResolver contentResolver = null;
	
	protected Uri getContentURI() {
		return Uri.parse(currentURI);
	}
	
	public SearchAggregator(final String[] keywords, final ContentResolver contentResolver) {
		super(keywords);
		this.contentResolver = contentResolver;
	}
	
	public ArrayList<SMSHolder> fetchInboxMessages() {
		currentURI = INBOX_URI;
		return fetchMessages(contentResolver, INBOX);
	}

	public ArrayList<SMSHolder> fetchSentMessages() {
		currentURI = SENT_URI;
		return fetchMessages(contentResolver, SENT);
	}
	
	public ArrayList<SMSHolder> fetchDraftMessages() {
		currentURI = DRAFT_URI;
		return fetchMessages(contentResolver, DRAFT);
	}
}
