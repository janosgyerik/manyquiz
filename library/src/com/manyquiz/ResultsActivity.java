package com.manyquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ResultsActivity extends Activity {

	private Level level;
	int score = 0;
	int index = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);

		Bundle bundle = getIntent().getExtras();
		level = (Level)bundle.getSerializable(QuizActivity.PARAM_LEVEL);
		score = bundle.getInt("score");
		index = bundle.getInt("index");
		String resultsDescription = "";
		float percentageScore = (((float) score / (float) QuizActivity.numberOfQuestionsToAsk)*100);

		resultsDescription = getResultsDescription(percentageScore);

		TextView tvResultsScore = (TextView) findViewById(R.id.resultsScore);
		TextView tvResultsReview = (TextView) findViewById(R.id.resultsDescription);

		tvResultsScore.setText(Integer.toString(score) + "/" + Integer.toString(QuizActivity.numberOfQuestionsToAsk));
		tvResultsReview.setText(resultsDescription);

		findViewById(R.id.btnDoneResults).setOnClickListener(
				new DoneClickListener());
	}

	private String getResultsDescription(float percentageScore) {
		String resultsDescription = "";

		if (percentageScore == 0) {
			resultsDescription = getResources().getString(R.string.result_zero_pc);
		}
		else if (percentageScore < 10) {
			resultsDescription = getResources().getString(R.string.result_less_than_ten_pc);
		}
		else if (percentageScore < 40) {
			resultsDescription = getResources().getString(R.string.result_less_than_forty_pc);
		}
		else if (percentageScore < 70) {
			resultsDescription = getResources().getString(R.string.result_less_than_seventy_pc);
		}		
		else if (percentageScore < 90) {
			resultsDescription = getResources().getString(R.string.result_less_than_ninety_pc);
		}	
		else if (percentageScore < 100) {
			resultsDescription = getResources().getString(R.string.result_less_than_one_hundred_pc);
		}			
		else if (percentageScore == 100) {
			resultsDescription = getResources().getString(R.string.result_one_hundren_pc);
		}
		return resultsDescription;
	}
	class DoneClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			finish();
		}
	}

}
