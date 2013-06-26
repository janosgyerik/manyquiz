package com.manyquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class QuestionSuggestionActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendmessage);

		findViewById(R.id.btn_sendmessage).setOnClickListener(
				new SubmitButtonOnClickListener());
	}

	class SubmitButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			sendQuestionSuggestion();
		}
	}

	public void sendQuestionSuggestion() {
		EditText message = (EditText) findViewById(R.id.message);
		String emailBody = message.getText().toString();

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("message/rfc822");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] {
				getResources().getString(R.string.email_address) });
		intent.putExtra(Intent.EXTRA_SUBJECT,
				getResources().getString(R.string.subject_sendmessage_prefix));
		intent.putExtra(Intent.EXTRA_TEXT, emailBody);

		try {
			startActivity(Intent.createChooser(intent, getResources().getString(R.string.no_email_client)));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(QuestionSuggestionActivity.this,
					getResources().getString(R.string.no_email_client), Toast.LENGTH_SHORT)
					.show();
		}
	}
}