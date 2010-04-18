package com.barefoot.smsdigger.views;

import android.app.Activity;
import android.app.Dialog;
import android.widget.ImageView;
import android.widget.TextView;

import com.barefoot.smsdigger.SMSConstants;
import com.barefoot.smsdigger.data.SMSHolder;


public class SMSView implements SMSConstants {
	
	Dialog customDialog;
	SMSHolder message;
	Activity activity;
	
	public SMSView(final Activity activity, final SMSHolder message) {
		customDialog = new Dialog(activity);
		this.activity = activity;
		this.message = message;
	}
	
	public Dialog dialog() {
		customDialog.setContentView(R.layout.smsview);
		
		customDialog.setTitle(getTitleForMessage());
		TextView text = (TextView) customDialog.findViewById(R.id.smsText);
		text.setText(message.getMessage());
		
		ImageView imageView = (ImageView) customDialog.findViewById(R.id.smsImage);
		if(message.getContactDetails(activity).getContactPhoto() == null )
		{
			imageView.setImageResource(R.drawable.icon);
		}
		else
		{
			imageView.setImageBitmap(message.getContactDetails(activity).getContactPhoto());			
		}	
		return customDialog;
	}

	private CharSequence getTitleForMessage() {
		String title = activity.getString(message.getSourceFolder());	
		if(message.getSourceFolder() != DRAFT) {
			title = title + " "+ message.getContactDetails(activity).getContactName();
		}
		return title;
	}
}
