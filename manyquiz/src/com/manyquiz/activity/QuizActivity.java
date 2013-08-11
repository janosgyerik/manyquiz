package com.manyquiz.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.manyquiz.db.DatabaseBackedQuizFactory;
import com.manyquiz.quiz.impl.ScoreInTheEndQuiz;
import com.manyquiz.quiz.model.IAnswerControl;
import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuestionControl;
import com.manyquiz.quiz.model.IQuizFactory;
import com.manyquiz.quiz.model.IQuizControl;
import com.manyquiz.quiz.impl.Level;
import com.manyquiz.db.QuizSQLiteOpenHelper;
import com.manyquiz.R;
import com.manyquiz.quiz.impl.ScoreAsYouGoQuiz;
import com.manyquiz.quiz.impl.SuddenDeathQuiz;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends QuizBaseActivity {

    private static final String TAG = QuizActivity.class.getSimpleName();

    public static final String PARAM_LEVEL = "LEVEL";
    public static final String PARAM_MODE = "MODE";

    private static final String QUIZ_CONTROL = "QUIZ_CONTROL";

    private QuizSQLiteOpenHelper helper;

    private TextView questionIndexView;

    private TextView questionView;
    private TextView explanationView;
    private LinearLayout choicesView;

    private ImageButton prevButton;
    private ImageButton nextButton;
    private ImageButton finishButton;

    private IQuizControl quizControl;
    private List<Button> answerButtons = new ArrayList<Button>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "++onCreate");
        setContentView(R.layout.quiz_activity);

        checkAndSetupForLiteVersion();

        if (savedInstanceState == null) {
            Level level;
            String mode;
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                level = (Level) bundle.getSerializable(PARAM_LEVEL);
                mode = bundle.getString(PARAM_MODE);
            }
            else {
                level = new Level("1", null, 1);
                mode = getString(R.string.const_score_in_the_end);
            }
            int preferredQuestionsNum = getPreferredQuestionsNum(mode);
            preferredQuestionsNum = 3;

            helper = new QuizSQLiteOpenHelper(this);

            IQuizFactory quiz = new DatabaseBackedQuizFactory(getHelper());
            List<IQuestion> questions = quiz.pickRandomQuestions(preferredQuestionsNum,
                    level.getLevel());

            if (mode.equals(getString(R.string.const_score_as_you_go))) {
                quizControl = new ScoreAsYouGoQuiz(questions);
            }
            else if (mode.equals(getString(R.string.const_suddendeath))) {
                quizControl = new SuddenDeathQuiz(questions);
            }
            else {
                quizControl = new ScoreInTheEndQuiz(questions);
            }
        }
        else {
            Log.d(TAG, "restoring from savedInstanceState");
            quizControl = (IQuizControl) savedInstanceState.getSerializable(QUIZ_CONTROL);
        }

        questionView = (TextView) findViewById(R.id.question);
        explanationView = (TextView) findViewById(R.id.explanation);
        choicesView = (LinearLayout) findViewById(R.id.choices);
        questionIndexView = (TextView) findViewById(R.id.question_i);

        TextView questionsNumView = (TextView) findViewById(R.id.questions_num);
        questionsNumView.setText(Integer.toString(quizControl.getQuestionsNum()));

        prevButton = (ImageButton) findViewById(R.id.btn_prev);
        prevButton.setOnClickListener(new PrevClickListener());
        nextButton = (ImageButton) findViewById(R.id.btn_next);
        nextButton.setOnClickListener(new NextClickListener());
        finishButton = (ImageButton) findViewById(R.id.btn_finish);
        finishButton.setOnClickListener(new FinishClickListener());

        replaceCurrentQuestion();
    }

    class NextClickListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            quizControl.gotoNextQuestion();
            replaceCurrentQuestion();
        }
    }

    class PrevClickListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            quizControl.gotoPrevQuestion();
            replaceCurrentQuestion();
        }
    }

    class FinishClickListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            if (! quizControl.isGameOver()) {
                quizControl.end();
                updateCurrentQuestion();
            }
            finishGame();
        }
    }

    class AnswerClickListener implements OnClickListener {
        private IAnswerControl answer;

        AnswerClickListener(IAnswerControl answer) {
            this.answer = answer;
        }

        @Override
        public void onClick(View arg0) {
            answer.select();
            updateCurrentQuestion();
        }
    }

    void replaceCurrentQuestion() {
        IQuestionControl question = quizControl.getCurrentQuestion();

        questionView.setText(question.getQuestion().getText());

        choicesView.removeAllViews();

        explanationView.setVisibility(View.GONE);
        explanationView.setText(question.getQuestion().getExplanation());

        answerButtons.clear();

        for (IAnswerControl answer : question.getAnswerControls()) {
            final LayoutInflater inflater = LayoutInflater.from(this);
            Button button = (Button) inflater.inflate(R.layout.answer_button, choicesView, false);
            if (button != null) {
                button.setText(answer.getAnswer().getText());
                button.setTag(answer);
                choicesView.addView(button);
                answerButtons.add(button);
            }
        }

        if (question.isOpen()) {
            for (Button button : answerButtons) {
                IAnswerControl answer = (IAnswerControl)button.getTag();
                button.setOnClickListener(new AnswerClickListener(answer));
            }
        } else {
            explanationView.setVisibility(View.VISIBLE);
        }

        updateCurrentQuestion();
    }

    void updateCurrentQuestion() {
        for (Button button : answerButtons) {
            // weird. but, need to save the paddings and reapply them after
            // changing the background resource, otherwise the padding disappears
            int paddingTop = button.getPaddingTop();
            int paddingBottom = button.getPaddingBottom();
            int paddingLeft = button.getPaddingLeft();
            int paddingRight = button.getPaddingRight();

            IAnswerControl answer = (IAnswerControl) button.getTag();
            if (quizControl.getCurrentQuestion().isOpen()) {
                if (answer.isSelected()) {
                    button.setBackgroundResource(R.drawable.btn_default_pressed);
                } else {
                    button.setBackgroundResource(R.drawable.btn_default);
                }
                button.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            } else {
                if (answer.getAnswer().isCorrect()) {
                    button.setBackgroundResource(R.drawable.btn_correct);
                    button.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                } else if (answer.isSelected()) {
                    button.setBackgroundResource(R.drawable.btn_incorrect);
                    button.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                }
                button.setEnabled(false);
                explanationView.setVisibility(View.VISIBLE);
            }
        }
        updateQuestionCounter();
        updateNavigationButtons();
    }

    private void updateQuestionCounter() {
        questionIndexView.setText(Integer.toString(quizControl.getCurrentQuestionIndex() + 1));
    }

    private void updateNavigationButtons() {
        IQuestionControl question = quizControl.getCurrentQuestion();

        if (quizControl.hasNextQuestion() && question.isReadyForNext()) {
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setEnabled(true);
        } else if (quizControl.canNavigateBack()) {
            nextButton.setVisibility(View.INVISIBLE);
            nextButton.setEnabled(false);
        } else {
            nextButton.setVisibility(View.GONE);
            nextButton.setEnabled(false);
        }

        if (quizControl.hasPrevQuestion() && question.isReadyForPrevious()) {
            prevButton.setVisibility(View.VISIBLE);
            prevButton.setEnabled(true);
        } else if (quizControl.canNavigateBack()) {
            prevButton.setVisibility(View.INVISIBLE);
            prevButton.setEnabled(false);
        } else {
            prevButton.setVisibility(View.GONE);
            prevButton.setEnabled(false);
        }

        if (quizControl.readyToEnd()) {
            finishButton.setVisibility(View.VISIBLE);
            finishButton.setEnabled(true);
        } else {
            finishButton.setVisibility(View.GONE);
            finishButton.setEnabled(false);
        }
    }

    private void finishGame() {
        Bundle bundle = new Bundle();
        bundle.putInt(ResultsActivity.PARAM_TOTAL_QUESTIONS_NUM, quizControl.getQuestionsNum());
        bundle.putInt(ResultsActivity.PARAM_CORRECT_ANSWERS_NUM, quizControl.getScore());
        Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public int getPreferredQuestionsNum(String mode) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);

        if (mode.equals(getString(R.string.const_suddendeath))) {
            String key = getString(R.string.key_max_questions_suddendeath);
            return Integer.parseInt(settings.getString(key, null));
        } else {
            String key = getString(R.string.key_max_questions_normal);
            return Integer.parseInt(settings.getString(key, null));
        }
    }

    protected QuizSQLiteOpenHelper getHelper() {
        return helper;
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "--onDestroy");
        super.onDestroy();
        if (helper != null) {
            helper.close();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "--onSaveInstanceState");
        outState.putSerializable(QUIZ_CONTROL, quizControl);
    }
}
