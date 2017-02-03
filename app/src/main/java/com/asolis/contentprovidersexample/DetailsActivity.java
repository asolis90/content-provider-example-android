package com.asolis.contentprovidersexample;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity
{

    final static String BUNDLE_DATA = "data";
    public static void launch(Activity activity, String data)
    {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_DATA, data);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString(BUNDLE_DATA);

        TextView contactView = (TextView) findViewById(R.id.contact_tv);
        contactView.setText(data);
    }
}
