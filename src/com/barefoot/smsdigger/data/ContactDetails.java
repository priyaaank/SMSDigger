package com.barefoot.smsdigger.data;

import java.io.ByteArrayInputStream;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.Contacts.Photos;

public class ContactDetails {

	ContentResolver contentResolver;

	public ContactDetails(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}

	public ContactInfo getContactInfoFrom(String phoneNumber) {
		Uri phoneUri = Uri.withAppendedPath(
				Contacts.Phones.CONTENT_FILTER_URL, Uri
						.encode(phoneNumber));
		if (phoneUri != null) {
			Cursor phoneCursor = null;
			try {
				phoneCursor = contentResolver.query(phoneUri, new String[] {
						Contacts.Phones._ID,
						Contacts.Phones.PERSON_ID,
						Contacts.Phones.DISPLAY_NAME }, null, null,
						null);
				if (phoneCursor.moveToFirst()) {
					return new ContactInfo(getPhoto(phoneCursor.getLong(1)),
							phoneCursor.getString(2));
				}
			}
			finally{
				phoneCursor.close();
			}
		}
		return new ContactInfo(null, phoneNumber);
	}

	private Bitmap getPhoto(Long contactId) {
		Uri photoUri = photoUri = Uri.withAppendedPath(Uri.withAppendedPath(Contacts.People.CONTENT_URI, Long.toString(contactId)),Contacts.Photos.CONTENT_DIRECTORY);

	    Cursor cursor = contentResolver.query(photoUri, new String[] {Photos.DATA}, null, null, null);
	    try {
	          if (cursor.moveToFirst()) {
	            byte[] data = cursor.getBlob(0);
	            if (data != null) {
	              return BitmapFactory.decodeStream(new ByteArrayInputStream(data), null, null);
	            }
	          }
	        } finally {
	          cursor.close();
	        }
	      return null;
	}
}