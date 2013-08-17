package com.manyquiz.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.manyquiz.R;
import com.manyquiz.tools.EmailTools;

public class ContactActivity extends Activity {

    private static final String TAG = ContactActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);

        findViewById(R.id.btn_sendmessage).setOnClickListener(
                new SendMessageButtonOnClickListener());
    }

    class SendMessageButtonOnClickListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            sendMessage();
        }
    }

    public void sendMessage() {
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

        RadioGroup messageTypeChoices = (RadioGroup) findViewById(R.id.message_type_choices);
        String tag = (String) findViewById(messageTypeChoices.getCheckedRadioButtonId()).getTag();
        String subject = String.format(getString(R.string.subject_contact_prefix), tag);

        EmailTools.send(this, subject, emailBody);
        finish();
    }
}