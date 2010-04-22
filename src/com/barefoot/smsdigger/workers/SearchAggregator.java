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
	private boolean inbox_fetch = true;
	private boolean sent_fetch = false;
	private boolean draft_fetch = false;

	protected Uri getContentURI() {
		return Uri.parse(currentURI);
	}

	public SearchAggregator(final String[] keywords,
			final ContentResolver contentResolver, final boolean inboxFetch,
			final boolean sentFetch, final boolean draftFetch) {
		super(keywords);
		this.contentResolver = contentResolver;
		this.sent_fetch = sentFetch;
		this.inbox_fetch = inboxFetch;
		this.draft_fetch = draftFetch;
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

	public ArrayList<SMSHolder> fetchMessagesFromSources() {
		ArrayList<SMSHolder> messages = new ArrayList<SMSHolder>();

		if (inbox_fetch)
			messages.addAll(fetchInboxMessages());
		if (sent_fetch)
			messages.addAll(fetchSentMessages());
		if (draft_fetch)
			messages.addAll(fetchDraftMessages());

		return messages;
	}
}
