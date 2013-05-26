package com.manyquiz; 

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class IntroActivity extends QuizBaseActivity {

	private static final String TAG = IntroActivity.class.getSimpleName();
	
	private RadioGroup levelChoices;
	private Button btnStartQuiz;
	
	private QuizSQLiteOpenHelper helper;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "++onCreate");
		setContentView(R.layout.activity_intro);
		
		checkAndSetupForLiteVersion();

		helper = new QuizSQLiteOpenHelper(this);

		levelChoices = (RadioGroup) findViewById(R.id.level_choices);
		RadioButton first = null;
		for (Level level : helper.getLevels()) {
			RadioButton levelOption = new RadioButton(this);
			levelOption.setText(level.getName());
			levelOption.setTag(level);
			if (first == null) {
				first = levelOption;
			}
			levelChoices.addView(levelOption);
		}
		RadioButton suddenDeathOption = new RadioButton(this);
		suddenDeathOption.setText(getString(R.string.option_sudden_death));
		suddenDeathOption.setTag(new SuddenDeathLevel());
		levelChoices.addView(suddenDeathOption);
		levelChoices.check(first.getId());
		
		btnStartQuiz = (Button) findViewById(R.id.btn_startQuiz);
		btnStartQuiz.setOnClickListener(new StartQuizClickListener());
	}

	class ExitClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			finish();	
		}
	}

	class StartQuizClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			View selectedOption = findViewById(levelChoices.getCheckedRadioButtonId());
			Level level = (Level)selectedOption.getTag();
			Log.d(TAG, "selected level = " + level);

			Bundle bundle = new Bundle();
			bundle.putSerializable(QuizActivity.PARAM_LEVEL, level);

			Intent intent = new Intent(IntroActivity.this, QuizActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override  
	protected void onDestroy() {
		Log.d(TAG, "++onDestroy");
		super.onDestroy();
		helper.close();
	}

} //end of activity class