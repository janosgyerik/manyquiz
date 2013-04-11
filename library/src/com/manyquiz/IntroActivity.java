package com.manyquiz; 

import android.app.Activity;
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
import android.widget.Toast;

public class IntroActivity extends Activity {

	private static final String TAG = IntroActivity.class.getSimpleName();
	
	private int gameMode = 0; //difficulty/mode

	private Button btnStartQuiz;
	private Button btnExit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "++onCreate");
		setContentView(R.layout.activity_intro);

		if (((QuizApplication)this.getApplication()).isLiteVersion()) {
			findViewById(R.id.lite_watermark).setVisibility(View.VISIBLE);

			// TODO
			// ... impose limitations of the lite version ...
		}

		btnStartQuiz = (Button) findViewById(R.id.btn_startQuiz);
		btnStartQuiz.setOnClickListener(new NextClickListener());
	}

	class NextClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			gameMode = getSelectedDifficulty();

			Bundle bundle = new Bundle();
			bundle.putInt(QuizActivity.GAME_MODE, gameMode);

			Intent intent = new Intent(IntroActivity.this, QuizActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

	private int getSelectedDifficulty() {
		RadioButton option2 = (RadioButton) findViewById(R.id.radio_difficulty2);
		RadioButton option3 = (RadioButton) findViewById(R.id.radio_difficulty3);
		RadioButton option4 = (RadioButton) findViewById(R.id.radio_difficulty4);
		RadioButton option5 = (RadioButton) findViewById(R.id.radio_difficulty5);

		//Check which difficulty radio button was selected
		if (option2.isChecked() == true) {
			gameMode = 2;
		}
		else if (option3.isChecked() == true) {
			gameMode = 3;
		}
		else if (option4.isChecked() == true) {
			gameMode = 4;
		}
		else if (option5.isChecked() == true) {
			gameMode = 5;
		}
		return gameMode;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.menu_about) {
			Toast.makeText(getBaseContext(), "Coming soon...", Toast.LENGTH_LONG).show();
			return true;
		}
		if (itemId == R.id.menu_settings) {
			Toast.makeText(getBaseContext(), "Coming soon...", Toast.LENGTH_LONG).show();
			return true;
		}
		if (itemId == R.id.menu_quit) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.msg_quit)
			.setCancelable(true)
			.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					finish();
				}
			})
			.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			}).show();
			return true;
		}
		return false;
	}

	@Override  
	protected void onDestroy() {
		Log.d(TAG, "++onDestroy");
		super.onDestroy();
	}

} //end of activity class
