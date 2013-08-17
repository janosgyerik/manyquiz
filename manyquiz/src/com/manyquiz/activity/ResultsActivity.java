package com.manyquiz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.manyquiz.R;
import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuestionControl;
import com.manyquiz.quiz.model.IQuizControl;
import com.manyquiz.tools.EmailTools;

public class ResultsActivity extends Activity {

    protected static final String PARAM_QUIZ_CONTROL = "QUIZ_CONTROL";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);

        Bundle bundle = getIntent().getExtras();
        int correctAnswers = 0;
        int totalQuestions = 1;
        String markedQuestionsText = null;
        if (bundle != null) {
            IQuizControl quizControl = (IQuizControl) bundle.getSerializable(PARAM_QUIZ_CONTROL);
            if (quizControl != null) {
                totalQuestions = quizControl.getQuestionsNum();
                correctAnswers = quizControl.getScore();
                StringBuilder builder = new StringBuilder();
                for (IQuestionControl questionControl : quizControl.getQuestionControls()) {
                    if (questionControl.isMarked()) {
                        IQuestion question = questionControl.getQuestion();
                        builder.append("Q: ");
                        builder.append(question.getText());
                        builder.append("\n\n");
                    }
                }
                if (builder.length() > 0) {
                    markedQuestionsText = builder.toString();
                }
            }
        }
        int correctPercent = 100 * correctAnswers / totalQuestions;

        String score = String.format(getString(R.string.result_score_format),
                correctAnswers, totalQuestions, correctPercent);
        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText(score);

        String message;

        switch (correctPercent / 10) {
            case 0:
                if (correctAnswers == 0) {
                    message = getString(R.string.result_0);
                } else {
                    message = getString(R.string.result_less_than_10);
                }
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

        if (markedQuestionsText != null) {
            Button sendMarkedQuestionsButton = (Button) findViewById(R.id.btn_send_marked);
            sendMarkedQuestionsButton.setOnClickListener(new SendMarkedQuestionsClickListener(markedQuestionsText));
            sendMarkedQuestionsButton.setVisibility(View.VISIBLE);
        }
    }

    class SendMarkedQuestionsClickListener implements View.OnClickListener {
        private final String message;

        SendMarkedQuestionsClickListener(String message) {
            this.message = message;
        }

        @Override
        public void onClick(View view) {
            sendMarkedQuestions(message);
        }
    }

    private void sendMarkedQuestions(String message) {
        EmailTools.send(this, R.string.subject_marked_questions, message);
    }

}
