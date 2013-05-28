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
		setContentView(R.layout.activity_submit_question);

		findViewById(R.id.btnSubmitQuestion).setOnClickListener(
				new SubmitButtonOnClickListener());
	}

	class SubmitButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			sendQuestionSuggestion();
		}
	}

	public void sendQuestionSuggestion() {
		EditText etSuggestedQuestion = (EditText) findViewById(R.id.etSuggestedQuestion);
		String emailBody = 	"Question: " + etSuggestedQuestion.getText();

		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{getResources().getString(R.string.question_email_address)});
		i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.question_email_title));
		i.putExtra(Intent.EXTRA_TEXT   , emailBody);

		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(QuestionSuggestionActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}