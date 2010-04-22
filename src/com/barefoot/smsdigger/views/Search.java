package com.barefoot.smsdigger.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.barefoot.smsdigger.SMSConstants;
import com.barefoot.smsdigger.workers.KeywordExtractor;

public class Search extends Activity implements SMSConstants {
	
	private KeywordExtractor extractor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setUIBindings();
	}

	private void setUIBindings() {
		Button button = (Button) findViewById(R.id.searchButton);
		button.setOnClickListener(searchButtonClickListener);
	}

	private OnClickListener searchButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View searchView) {
			EditText textBox = (EditText) findViewById(R.id.keywordBox);
			String searchBoxText = textBox.getText().toString();
			extractor = new KeywordExtractor(searchBoxText);
			createNewActivityToListMessagesFrom(extractor.extractKeywords());
		}
	};
	
	private void createNewActivityToListMessagesFrom(String[] keywords){
    	Intent listingActivity = new Intent(Search.this, Listing.class);
    	listingActivity.putExtra(KEYWORDS, keywords);
    	listingActivity.putExtra(INBOX_FETCH, inboxSelected());
    	listingActivity.putExtra(SENT_FETCH, sentSelected());
    	listingActivity.putExtra(DRAFT_FETCH, draftSelected());
    	startActivity(listingActivity);
    }
	
	private boolean inboxSelected() {
		CheckBox inboxCheck = (CheckBox)findViewById(R.id.inboxCheck);
		return inboxCheck.isChecked();
	}

	private boolean sentSelected() {
		CheckBox sentCheck = (CheckBox)findViewById(R.id.sentCheck);
		return sentCheck.isChecked();
	}
	
	private boolean draftSelected() {
		CheckBox draftCheck = (CheckBox)findViewById(R.id.draftCheck);
		return draftCheck.isChecked();
	}
}
