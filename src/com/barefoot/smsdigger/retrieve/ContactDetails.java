package com.barefoot.smsdigger.retrieve;

import java.io.InputStream;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactDetails {
	
	ContentResolver contentResolver;
	
	public ContactDetails(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}
	
	public ContactInfo getContactInfoFrom(String phoneNumber) {
		Uri phoneUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
		if (phoneUri != null) {
		  Cursor phoneCursor = contentResolver.query(phoneUri, new String[] {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.LOOKUP_KEY, ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
		  if (phoneCursor.moveToFirst()) {
			return new ContactInfo(getPhoto(phoneCursor.getLong(1)),phoneCursor.getString(2));
		  }
		}
		return new ContactInfo(null, phoneNumber);
	}
	
	private Bitmap getPhoto(Long contactId) {
	    Uri contactPhotoUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
	    
	    InputStream photoDataStream = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver,contactPhotoUri);
	    Bitmap photo = BitmapFactory.decodeStream(photoDataStream);
	    return photo;
	}
}