package com.manyquiz.android.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.manyquiz.R;
import com.manyquiz.android.db.DatabaseBackedQuizFactory;
import com.manyquiz.android.db.QuizSQLiteOpenHelper;
import com.manyquiz.common.quiz.impl.GameMode;
import com.manyquiz.common.quiz.impl.Level;
import com.manyquiz.common.quiz.impl.QuestionsNumChoice;
import com.manyquiz.common.quiz.impl.ScoreAsYouGoQuiz;
import com.manyquiz.common.quiz.impl.ScoreInTheEndQuiz;
import com.manyquiz.common.quiz.impl.SuddenDeathQuiz;
import com.manyquiz.common.quiz.model.IAnswerControl;
import com.manyquiz.common.quiz.model.IQuestion;
import com.manyquiz.common.quiz.model.IQuestionControl;
import com.manyquiz.common.quiz.model.IQuizControl;
import com.manyquiz.common.quiz.model.IQuizFactory;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends QuizActivityBase {

    private static final String TAG = QuizActivity.class.getSimpleName();

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

    private boolean effectsEnbled = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "++onCreate");
        setContentView(R.layout.quiz_activity);

        checkAndSetupForLiteVersion();

        if (savedInstanceState == null) {
            setHelper(new QuizSQLiteOpenHelper(this));

            Level level = (Level) createLevelChoiceControl().getSelectedItem();
            GameMode mode = (GameMode) createModeChoiceControl().getSelectedItem();
            int preferredQuestionsNum = ((QuestionsNumChoice) createQuestionsNumChoiceControl().getSelectedItem()).num;

            IQuizFactory quiz = new DatabaseBackedQuizFactory(getHelper());
            List<IQuestion> questions = quiz.pickRandomQuestions(preferredQuestionsNum,
                    level.difficulty, createCategoryFilterControl().getSelectedNames());

            if (questions.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_Dialog));
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

            if (mode instanceof GameMode.ScoreAsYouGo) {
                quizControl = new ScoreAsYouGoQuiz(questions);
            } else if (mode instanceof GameMode.ScoreInTheEnd) {
                quizControl = new ScoreInTheEndQuiz(questions);
            } else if (mode instanceof GameMode.SuddenDeath) {
                quizControl = new SuddenDeathQuiz(questions);
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
        if (button != null) {
            BTN_PADDING_TOP = button.getPaddingTop();
            BTN_PADDING_BOTTOM = button.getPaddingBottom();
            BTN_PADDING_LEFT = button.getPaddingLeft();
            BTN_PADDING_RIGHT = button.getPaddingRight();
        }

        updateEffectsEnabledFromSettings();

        replaceCurrentQuestion();
    }

    private void updateEffectsEnabledFromSettings() {
        effectsEnbled = getSharedPreferences().getBoolean(getString(R.string.pref_fadeout), false);
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
            updateCurrentQuestion(true);
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
        updateCurrentQuestion(false);
    }

    private void updateCurrentQuestion(boolean useEffects) {
        boolean canChangeAnswer = quizControl.getCurrentQuestion().canChangeAnswer();

        if (!canChangeAnswer) {
            explanationView.setVisibility(View.VISIBLE);
        }

        for (final Button button : answerButtons) {
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
                if (answer.getAnswer().isCorrect()) {
                    button.setBackgroundResource(R.drawable.btn_correct);
                    button.setPadding(BTN_PADDING_LEFT, BTN_PADDING_TOP, BTN_PADDING_RIGHT, BTN_PADDING_BOTTOM);
                } else if (answer.isSelected()) {
                    button.setBackgroundResource(R.drawable.btn_incorrect);
                    button.setPadding(BTN_PADDING_LEFT, BTN_PADDING_TOP, BTN_PADDING_RIGHT, BTN_PADDING_BOTTOM);
                } else if (effectsEnbled) {
                    if (useEffects) {
                        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadeout);
                        if (animation != null) {
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    button.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }
                            });
                            button.startAnimation(animation);
                        } else {
                            button.setVisibility(View.GONE);
                        }
                    } else {
                        button.setVisibility(View.GONE);
                    }
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
        outState.putSerializable(QUIZ_CONTROL, quizControl);
    }

    @Override
    protected void onResume() {
        updateEffectsEnabledFromSettings();
        super.onResume();
    }
}
