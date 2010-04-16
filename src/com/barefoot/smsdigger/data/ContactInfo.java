package com.barefoot.smsdigger.data;

import android.graphics.Bitmap;

public class ContactInfo {

	Bitmap contactPhoto;
	
	String contactName;
	
	public ContactInfo(Bitmap photo, String name) {
		this.contactName = name;
		this.contactPhoto = photo;
	}
	
	public Bitmap getContactPhoto() {
		return contactPhoto;
	}
	
	public void setContactPhoto(Bitmap photo) {
		this.contactPhoto = photo;
	}
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String name) {
		this.contactName = name;
	}
}