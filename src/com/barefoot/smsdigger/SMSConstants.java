package com.barefoot.smsdigger;

import com.barefoot.smsdigger.views.R;

public interface SMSConstants {
	
	public static final String MESSAGES = "messages";
	public static final String KEYWORDS = "keywords";
	public static final String SMS = "sms";
	public static final int MSG_LENGTH = 28;
	public static final int INBOX = R.string.received_title;
	public static final int SENT = R.string.sent_title;
	public static final int DRAFT = R.string.draft_title;
	public static final String SRC_VALUE_CODE = "src_value_code";
	public static final int MSG_PER_FETCH = 20;
	public static final int MAX_MESSAGE_IN_MEMORY = 100;
	public static final int NEXT_MSG_FETCH_THRESHOLD = 5;
	
	public static final String INBOX_FETCH = "inboxfetch";
	public static final String SENT_FETCH = "sentfetch";
	public static final String DRAFT_FETCH = "draftfetch";
}
