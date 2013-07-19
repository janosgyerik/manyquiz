package com.manyquiz.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.manyquiz.R;

public class ContactActivity extends Activity {

    private static final String TAG = ContactActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);

        findViewById(R.id.btn_sendmessage).setOnClickListener(
                new SubmitButtonOnClickListener());
    }

    class SubmitButtonOnClickListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            sendFaultReport();
        }
    }

    public void sendFaultReport() {
        EditText message = (EditText) findViewById(R.id.message);
        String emailBody = message.getText().toString();

        String pkg = getApplicationContext().getPackageName();
        PackageManager manager = getApplicationContext().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(pkg, 0);
            emailBody += String.format("  [Version: %d/%s]", info.versionCode, info.versionName);
        } catch (NameNotFoundException e) {
            Log.e(TAG, "Could not get package info", e);
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{
                getResources().getString(R.string.email_address)});
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getResources().getString(R.string.subject_contact_prefix));
        intent.putExtra(Intent.EXTRA_TEXT, emailBody);

        try {
            startActivity(Intent.createChooser(intent, getResources().getString(R.string.no_email_client)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactActivity.this,
                    getResources().getString(R.string.no_email_client), Toast.LENGTH_SHORT)
                    .show();
        }
    }
}