package com.barefoot.smsdigger;

import java.util.ArrayList;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.barefoot.smsdigger.retrieve.SMSHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SMSListing extends ListActivity implements SMSConstants {
	
	private String[] jsonMessageList;
	private ArrayList<SMSHolder> messages;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.listing);
        loadMessageList();
        updateListingContentsWith();
	}

	private void loadMessageList() {
		Bundle bundleValues = getIntent().getExtras();
		this.jsonMessageList = bundleValues.getStringArray(MESSAGES);
		messages = new ArrayList<SMSHolder>(this.jsonMessageList.length);
		Gson gson = new Gson();
		for (String eachJsonMessage : jsonMessageList ){
			messages.add(SMSHolder.createInstanceFrom((Map<String, String>)gson.fromJson(eachJsonMessage, new TypeToken<Map<String, String>>() {}.getType())));
		}
	}

	private void updateListingContentsWith() {
		ListAdapter messageListAdapter = new ArrayAdapter<String>(this, R.layout.sms, getShortenedMessages());
		setListAdapter(messageListAdapter);
	}
	
	private String[] getShortenedMessages() {
		String[] shortMessages = new String[this.jsonMessageList.length];
		int index = 0;
		int length = 0;
		for(SMSHolder eachMessage : this.messages) {
			length = eachMessage.getMessage().length() > MSG_LENGTH ? MSG_LENGTH : eachMessage.getMessage().length();
			shortMessages[index++] = (eachMessage.getMessage().substring(0, length) + "..."); 
		}
		return shortMessages;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//Passing this because if I extract application context, then it's running too fast and before it is actually created. 
		//Hence null was being passed in
		SMSView newSMS = new SMSView(getSelectedMessage(position), this);
		newSMS.dialog().show();
	}

	private String getSelectedMessage(int position) {
		return messages.get(position).getMessage();
	}
}
