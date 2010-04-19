package com.barefoot.smsdigger.views;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.barefoot.smsdigger.SMSConstants;
import com.barefoot.smsdigger.data.SMSHolder;
import com.barefoot.smsdigger.workers.SearchAggregator;

public class Listing extends ListActivity implements SMSConstants {

	private SearchAggregator aggregator;
	private ArrayList<SMSHolder> messages = new ArrayList<SMSHolder>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.listing);
        setup();
        updateListingContentsWith();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//Passing this because if I extract application context, then it's running too fast and before it is actually created. 
		//Hence null was being passed in
		SMSView newSMS = new SMSView(this, messages.get(position));
		newSMS.dialog().show();
	}
	
	private void updateListingContentsWith() {
		ListAdapter messageListAdapter = new ArrayAdapter<String>(this, R.layout.sms, getShortenedMessageList());
		setListAdapter(messageListAdapter);
	}

	private void setup() {
		String[] keywords = getIntent().getStringArrayExtra(KEYWORDS);
		int sourceValueCombination = getIntent().getIntExtra(SRC_VALUE_CODE, 1);
		aggregator = new SearchAggregator(keywords, getContentResolver(), sourceValueCombination);
		messages.addAll(aggregator.fetchMessagesFromSourcesHaving());
	}
	
	private String[] getShortenedMessageList() {
		String[] messageList = new String[messages.size()];
		int index = 0;
		for (SMSHolder eachMessage : messages) {
			messageList[index++] = eachMessage.getShortenedMessage();
		}
		return messageList;
	}

}
