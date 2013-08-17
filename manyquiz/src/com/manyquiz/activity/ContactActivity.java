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

        RadioGroup messageTypeChoices = (RadioGroup) findViewById(R.id.message_type_choices);
        String messageType = (String) findViewById(messageTypeChoices.getCheckedRadioButtonId()).getTag();
        String subject = getString(R.string.subject_contact) + " " + messageType;

        EmailTools.send(this, subject, emailBody);
        finish();
    }
}