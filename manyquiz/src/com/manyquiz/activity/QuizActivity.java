package com.manyquiz.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.manyquiz.R;
import com.manyquiz.db.DatabaseBackedQuizFactory;
import com.manyquiz.db.QuizSQLiteOpenHelper;
import com.manyquiz.quiz.impl.Level;
import com.manyquiz.quiz.impl.ScoreAsYouGoQuiz;
import com.manyquiz.quiz.impl.ScoreInTheEndQuiz;
import com.manyquiz.quiz.impl.SuddenDeathQuiz;
import com.manyquiz.quiz.model.IAnswerControl;
import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuestionControl;
import com.manyquiz.quiz.model.IQuizControl;
import com.manyquiz.quiz.model.IQuizFactory;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends QuizActivityBase {

    private static final String TAG = QuizActivity.class.getSimpleName();

    public static final String PARAM_LEVEL = "LEVEL";
    public static final String PARAM_MODE = "MODE";

    private static final String QUIZ_CONTROL = "QUIZ_CONTROL";

    private TextView questionIndexView;

    private TextView questionView;
    private TextView explanationView;
    private LinearLayout choicesView;

    private ImageButton prevButton;
    private ImageButton nextButton;
    private ImageButton finishButton;

    private IQuizControl quizControl;
    private List<Button> answerButtons = new ArrayList<Button>();

    private int BTN_PADDING_TOP;
    private int BTN_PADDING_BOTTOM;
    private int BTN_PADDING_LEFT;
    private int BTN_PADDING_RIGHT;

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
            } else {
                level = new Level("1", null, 1);
                mode = getString(R.string.const_score_as_you_go);
            }
            int preferredQuestionsNum = getPreferredQuestionsNum(mode);

            setHelper(new QuizSQLiteOpenHelper(this));

            IQuizFactory quiz = new DatabaseBackedQuizFactory(getHelper());
            List<IQuestion> questions = quiz.pickRandomQuestions(preferredQuestionsNum,
                    level.getLevel(), getCategoryFilterControl().getSelectedItems());

            if (questions.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
                builder.setTitle(R.string.title_no_matching_questions)
                        .setMessage(R.string.msg_no_matching_questions)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false)
                        .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .show();
                return;
            }

            if (mode.equals(getString(R.string.const_score_as_you_go))) {
                quizControl = new ScoreAsYouGoQuiz(questions);
            } else if (mode.equals(getString(R.string.const_suddendeath))) {
                quizControl = new SuddenDeathQuiz(questions);
            } else if (mode.equals(getString(R.string.const_score_in_the_end))) {
                quizControl = new ScoreInTheEndQuiz(questions);
            } else {
                quizControl = new ScoreAsYouGoQuiz(questions);
            }
        } else {
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

        // saving button paddings.
        // it's weird, but we need this.
        // after changing the background resource of a button,
        // the padding is reset, so we must add back manually
        final LayoutInflater inflater = LayoutInflater.from(this);
        Button button = (Button) inflater.inflate(R.layout.answer_button, choicesView, false);
        BTN_PADDING_TOP = button.getPaddingTop();
        BTN_PADDING_BOTTOM = button.getPaddingBottom();
        BTN_PADDING_LEFT = button.getPaddingLeft();
        BTN_PADDING_RIGHT = button.getPaddingRight();

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
            if (!quizControl.isGameOver()) {
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

        explanationView.setVisibility(View.GONE);
        explanationView.setText(question.getQuestion().getExplanation());

        choicesView.removeAllViews();
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

        if (question.canChangeAnswer()) {
            for (Button button : answerButtons) {
                IAnswerControl answer = (IAnswerControl) button.getTag();
                button.setOnClickListener(new AnswerClickListener(answer));
            }
        } else {
            explanationView.setVisibility(View.VISIBLE);
        }

        updateCurrentQuestion();
    }

    private void updateCurrentQuestion() {
        boolean canChangeAnswer = quizControl.getCurrentQuestion().canChangeAnswer();

        for (Button button : answerButtons) {
            IAnswerControl answer = (IAnswerControl) button.getTag();
            if (canChangeAnswer) {
                if (answer.isSelected()) {
                    button.setBackgroundResource(R.drawable.btn_default_pressed);
                } else {
                    button.setBackgroundResource(R.drawable.btn_default);
                }
                button.setPadding(BTN_PADDING_LEFT, BTN_PADDING_TOP, BTN_PADDING_RIGHT, BTN_PADDING_BOTTOM);
            } else {
                button.setEnabled(false);
                explanationView.setVisibility(View.VISIBLE);
                if (answer.getAnswer().isCorrect()) {
                    button.setBackgroundResource(R.drawable.btn_correct);
                    button.setPadding(BTN_PADDING_LEFT, BTN_PADDING_TOP, BTN_PADDING_RIGHT, BTN_PADDING_BOTTOM);
                } else if (answer.isSelected()) {
                    button.setBackgroundResource(R.drawable.btn_incorrect);
                    button.setPadding(BTN_PADDING_LEFT, BTN_PADDING_TOP, BTN_PADDING_RIGHT, BTN_PADDING_BOTTOM);
                }
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

        if (quizControl.hasNextQuestion() && question.canNavigateForward()) {
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setEnabled(true);
        } else if (quizControl.canNavigateBackward()) {
            nextButton.setVisibility(View.INVISIBLE);
            nextButton.setEnabled(false);
        } else {
            nextButton.setVisibility(View.GONE);
            nextButton.setEnabled(false);
        }

        if (quizControl.hasPrevQuestion() && question.canNavigateBackward()) {
            prevButton.setVisibility(View.VISIBLE);
            prevButton.setEnabled(true);
        } else if (quizControl.canNavigateBackward()) {
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
        bundle.putSerializable(ResultsActivity.PARAM_QUIZ_CONTROL, quizControl);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (! super.onOptionsItemSelected(item)) {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_mark) {
                quizControl.getCurrentQuestion().mark();
                Toast.makeText(this, R.string.msg_question_marked, Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "--onSaveInstanceState");
        outState.putSerializable(QUIZ_CONTROL, quizControl);
    }
}
