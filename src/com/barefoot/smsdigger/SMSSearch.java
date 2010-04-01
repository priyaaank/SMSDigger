package com.barefoot.smsdigger;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.barefoot.smsdigger.retrieve.SMSHolder;
import com.barefoot.smsdigger.retrieve.SMSRetriever;

public class SMSSearch extends Activity implements SMSConstants {
	
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
		public void onClick(View v) {
			EditText textBox = (EditText) findViewById(R.id.keywordBox);
			String searchBoxText = textBox.getText().toString();
			ArrayList<SMSHolder> list = new SMSRetriever(fetchKeywordsFrom(searchBoxText)).fetchMessages(getContentResolver());
			createNewActivityToListMessagesFrom(createBundleWith(prepareSMSListingFrom(list)));
		}
    };
    
    private String[] fetchKeywordsFrom(String keywords) {
    	if(null != keywords && keywords.trim().length() > 0) {
    		return keywords.split(" ");
    	}
    	return new String[] {};
    }
    
    private Bundle createBundleWith(String[] messages) {
    	Bundle savedInstance = new Bundle();
    	savedInstance.putStringArray(MESSAGES, messages);
    	return savedInstance;
	}
    
    private void createNewActivityToListMessagesFrom(Bundle savedInstance){
    	Intent listingActivity = new Intent(SMSSearch.this, SMSListing.class);
    	listingActivity.putExtras(savedInstance);
    	startActivity(listingActivity);
    }

	private String[] prepareSMSListingFrom(ArrayList<SMSHolder> smsList) {
    	int index = 0;
    	String[] smsArray = new String[smsList.size()];
    	for (SMSHolder eachSMS : smsList) {
    		smsArray[index++] = eachSMS.toJson();
    	}
    	return smsArray;
    }    
}