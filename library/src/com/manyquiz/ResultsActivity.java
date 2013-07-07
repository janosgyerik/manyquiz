package com.manyquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ResultsActivity extends Activity {

    protected static final String PARAM_TOTAL_QUESTIONS_NUM = "TOTAL_QUESTIONS_NUM";
    protected static final String PARAM_CORRECT_ANSWERS_NUM = "CORRECT_ANSWERS_NUM";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);

        Bundle bundle = getIntent().getExtras();
        int correctAnswers = 0;
        int totalQuestions = 1;
        if (bundle != null) {
            totalQuestions = bundle.getInt(PARAM_TOTAL_QUESTIONS_NUM);
            correctAnswers = bundle.getInt(PARAM_CORRECT_ANSWERS_NUM);
        }
        int correctPercent = 100 * correctAnswers / totalQuestions;

        String score = String.format(getString(R.string.result_score_format),
                correctAnswers, totalQuestions, correctPercent);
        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText(score);

        String message;

        switch (correctPercent / 10) {
            case 0:
                message = getString(R.string.result_0);
                break;
            case 1:
            case 2:
            case 3:
                message = getString(R.string.result_less_than_40);
                break;
            case 4:
            case 5:
            case 6:
                message = getString(R.string.result_less_than_70);
                break;
            case 7:
            case 8:
                message = getString(R.string.result_less_than_90);
                break;
            case 9:
                message = getString(R.string.result_less_than_100);
                break;
            case 10:
                message = getString(R.string.result_100);
                break;
            default:
                message = getString(R.string.result_bug);
                break;
        }

        TextView messageView = (TextView) findViewById(R.id.message);
        messageView.setText(message);

        findViewById(R.id.btn_done).setOnClickListener(new DoneClickListener());
    }

    class DoneClickListener implements OnClickListener {

        @Override
        public void onClick(View arg0) {
            finish();
        }
    }

}
