package com.barefoot.smsdigger;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;


public class SMSView implements SMSConstants {
	
	Context applicationContext;
	Dialog customDialog;
	
	public SMSView(String message, Context applicationContext) {
		this(message, R.drawable.icon, applicationContext);
	}
	
	public SMSView(String message, int imagePath, Context applicationContext) {
		customDialog = new Dialog(applicationContext);
		customDialog.setContentView(R.layout.smsview);
		customDialog.setTitle(SMS_TITLE);
		
		TextView text = (TextView) customDialog.findViewById(R.id.smsText);
		text.setText(message);
		ImageView image = (ImageView) customDialog.findViewById(R.id.smsImage);
		image.setImageResource(imagePath);
	}
	
	public Dialog dialog() {
		return customDialog;
	}
}
