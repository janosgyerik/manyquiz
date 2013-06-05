package com.manyquiz;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ResultsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);

		findViewById(R.id.btnDoneResults).setOnClickListener(
				new DoneClickListener());
	}

	class DoneClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			finish();
		}
	}

}
