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
	private int sourceValueCombination = 1;

	protected Uri getContentURI() {
		return Uri.parse(currentURI);
	}

	public SearchAggregator(final String[] keywords,
			final ContentResolver contentResolver, final int sourceValueCombination) {
		super(keywords);
		this.contentResolver = contentResolver;
		this.sourceValueCombination = sourceValueCombination == 0 ? 1 : sourceValueCombination;
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

	public ArrayList<SMSHolder> fetchMessagesFromSourcesHaving() {
		ArrayList<SMSHolder> messages = new ArrayList<SMSHolder>();
		switch(sourceValueCombination) {
		case 1:
			messages.addAll(fetchInboxMessages());
			break;
		case 3:
			messages.addAll(fetchDraftMessages());
			break;
		case 4: 
			messages.addAll(fetchInboxMessages());
			messages.addAll(fetchDraftMessages());
			break;
		case 5:
			messages.addAll(fetchSentMessages());
			break;
		case 6:
			messages.addAll(fetchInboxMessages());
			messages.addAll(fetchSentMessages());
			break;
		case 8:
			messages.addAll(fetchSentMessages());
			messages.addAll(fetchDraftMessages());
			break;
		case 9:
			messages.addAll(fetchInboxMessages());
			messages.addAll(fetchSentMessages());
			messages.addAll(fetchDraftMessages());
			break;
		}
		return messages;
	}
}
