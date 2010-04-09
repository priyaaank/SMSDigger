package com.barefoot.smsdigger;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;


public class SMSView implements SMSConstants {
	
	Context applicationContext;
	Dialog customDialog;
	
	public SMSView(String message, Context applicationContext, String smsTitle) {
		this(message, applicationContext, smsTitle, null);
	}
	
	public SMSView(String message, Context applicationContext, String smsTitle, Bitmap image) {
		customDialog = new Dialog(applicationContext);
		customDialog.setContentView(R.layout.smsview);
		customDialog.setTitle(smsTitle);
		
		TextView text = (TextView) customDialog.findViewById(R.id.smsText);
		text.setText(message);
		ImageView imageView = (ImageView) customDialog.findViewById(R.id.smsImage);
		if(image == null )
		{
			imageView.setImageResource(R.drawable.icon);
		}
		else
		{
			imageView.setImageBitmap(image);			
		}	
	}
	
	public Dialog dialog() {
		return customDialog;
	}
}
