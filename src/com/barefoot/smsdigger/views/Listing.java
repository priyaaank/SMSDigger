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
		boolean inboxFetch = getIntent().getBooleanExtra(INBOX_FETCH, true);
		boolean sentFetch = getIntent().getBooleanExtra(SENT_FETCH, false);
		boolean draftFetch = getIntent().getBooleanExtra(DRAFT_FETCH, false);
		aggregator = new SearchAggregator(keywords, getContentResolver(), inboxFetch, sentFetch, draftFetch);
		messages.addAll(aggregator.fetchMessagesFromSources());
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

/*class MyAdapter<T> extends ArrayAdapter<T> {
	
	private String[] obj = null;

	public MyAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public MyAdapter(Context context, int textViewResourceId, T[] objects) {
		super(context, textViewResourceId, objects);
		obj = (String[])objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.sms, null);
        }
        
        TextView t = (TextView) v;
		t.setText(obj[position]);
		
		Log.d("Called get view ", " the position pass is " + position + "");
		return v;
	}
	
}*/
