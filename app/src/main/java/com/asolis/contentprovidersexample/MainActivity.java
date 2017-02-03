package com.asolis.contentprovidersexample;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.btn_contacts);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Cursor cursor = getContacts();
                StringBuilder stringBuilder = new StringBuilder();
                while(cursor.moveToNext())
                {
                    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract
                            .Contacts.NAME_RAW_CONTACT_ID));
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract
                            .Data.DISPLAY_NAME));
                    Cursor cursorNumbers = getContentResolver().query(ContactsContract
                            .CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.Data
                            .CONTACT_ID + "=" + contactId, null, null);
                    if(cursorNumbers != null)
                    {
                        while(cursorNumbers.moveToNext())
                        {
                            String phoneNumber = cursorNumbers.getString(cursorNumbers
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                            stringBuilder.append(getString(R.string.name));
                            if(displayName != null)
                            {
                                stringBuilder.append(displayName + " - ");
                            }

                            stringBuilder.append(getString(R.string.phone_number));
                            if(phoneNumber != null)
                            {
                                stringBuilder.append(phoneNumber);
                            }
                            stringBuilder.append("\n");
                        }
                    }
                }
                cursor.close();
                DetailsActivity.launch(MainActivity.this, stringBuilder.toString());
            }
        });
    }

    private Cursor getContacts()
    {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        // setup projection for contact id, display name
        String[] projection = new String[]{ContactsContract.Contacts.NAME_RAW_CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME};
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
                + ("1") + "'";
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";
        return getContentResolver().query(uri, projection, selection,
                selectionArgs, sortOrder);
    }
}
