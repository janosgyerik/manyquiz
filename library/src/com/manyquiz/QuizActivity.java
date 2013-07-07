package com.manyquiz;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuizActivity extends QuizBaseActivity {

	private static final String TAG = QuizActivity.class.getSimpleName();

	public static final String PARAM_LEVEL = "LEVEL";

	private static final int BTN_PADDING_LEFT = 10;
	private static final int BTN_PADDING_TOP = 15;
	private static final int BTN_PADDING_RIGHT = 10;
	private static final int BTN_PADDING_BOTTOM = 15;

	private QuizSQLiteOpenHelper helper;
	private List<IQuestion> questions;
	private IQuestion currentQuestion;
	private int currentQuestionIndex = 0;
	private int score = 0;
	private Level level;
	private int numberOfAnsweredQuestions = 0;
	private TextView questionView;
	private TextView explanationView;
	private LinearLayout choicesView;
	private ImageButton prevButton;
	private ImageButton nextButton;
	private ImageButton finishButton;
	private TextView questions_i;

	private boolean gameOver;

	private int numberOfQuestionsToAsk;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "++onCreate");
		setContentView(R.layout.main_activity);

		checkAndSetupForLiteVersion();

		Bundle bundle = getIntent().getExtras();
		level = (Level) bundle.getSerializable(PARAM_LEVEL);
		Log.d(TAG, "use level = " + level);
		numberOfQuestionsToAsk = getNumberOfQuestionsToAsk();

		helper = new QuizSQLiteOpenHelper(this);

		IQuiz quiz = new DatabaseBackedQuiz(getHelper());
		questions = quiz.pickRandomQuestions(numberOfQuestionsToAsk,
				level.getLevel());
		questionView = (TextView) findViewById(R.id.question);
		explanationView = (TextView) findViewById(R.id.explanation);
		choicesView = (LinearLayout) findViewById(R.id.choices);

		prevButton = (ImageButton) findViewById(R.id.btn_prev);
		prevButton.setOnClickListener(new PrevNextClickListener(-1));
		nextButton = (ImageButton) findViewById(R.id.btn_next);
		nextButton.setOnClickListener(new PrevNextClickListener(1));
		finishButton = (ImageButton) findViewById(R.id.btn_finish);
		finishButton.setOnClickListener(new FinishClickListener());
		finishButton.setEnabled(false);
		questions_i = (TextView) findViewById(R.id.questions_i);

		TextView questions_n = (TextView) findViewById(R.id.questions_n);
		questions_n.setText(Integer.toString(questions.size()));

		int index = 0;
		navigateToQuestion(index);
	}

	class PrevNextClickListener implements OnClickListener {

		private int offset;

		PrevNextClickListener(int offset) {
			this.offset = offset;
		}

		@Override
		public void onClick(View arg0) {
			navigateToQuestion(currentQuestionIndex + offset);
		}
	}

	class FinishClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			finishGame();
		}
	}

	class ChoiceClickListener implements OnClickListener {
		private String answer;

		ChoiceClickListener(String answer) {
			this.answer = answer;
		}

		@Override
		public void onClick(View arg0) {
			if (!currentQuestion.wasAnswered()) {
				setSelectedAnswer(answer);
				explanationView.setVisibility(View.VISIBLE);
				updateScore();
			}
		}
	}

	private void updateCurrentQuestion(int index) {
		if (index < 0) {
			currentQuestionIndex = 0;
		} else if (index >= questions.size()) {
			currentQuestionIndex = questions.size() - 1;
		} else {
			currentQuestionIndex = index;
		}
		currentQuestion = questions.get(currentQuestionIndex);
	}

	private void updateScoreDisplay(int index) {
		questions_i.setText(Integer.toString(index + 1));
	}

	private void updatePrevNext() {
		if (!(level instanceof SuddenDeathLevel)) {
			if (currentQuestionIndex == 0) {
				prevButton.setVisibility(View.INVISIBLE);
				nextButton.setVisibility(View.VISIBLE);
				prevButton.setEnabled(false);
				nextButton.setEnabled(true);
			} else if (currentQuestionIndex == questions.size() - 1) {
				prevButton.setVisibility(View.VISIBLE);
				nextButton.setVisibility(View.INVISIBLE);
				prevButton.setEnabled(true);
				nextButton.setEnabled(false);
			} else {
				prevButton.setVisibility(View.VISIBLE);
				nextButton.setVisibility(View.VISIBLE);
				prevButton.setEnabled(true);
				nextButton.setEnabled(true);
			}
		} else {
			prevButton.setVisibility(View.GONE);
			prevButton.setEnabled(false);
		}
	}

	private void updateQuestion() {
		IQuestion question = questions.get(currentQuestionIndex);

		questionView.setText(question.getText());
		explanationView.setText(question.getExplanation());

		choicesView.removeAllViews();
		boolean wasAnswered = question.wasAnswered();
		for (String choice : question.getChoices()) {
			Button button = new Button(this);
			button.setText(choice);
			button.setPadding(BTN_PADDING_LEFT, BTN_PADDING_TOP,
					BTN_PADDING_RIGHT, BTN_PADDING_BOTTOM);
			if (!wasAnswered) {
				button.setOnClickListener(new ChoiceClickListener(choice));
			}
			choicesView.addView(button);
		}
		if (wasAnswered) {
			setSelectedAnswer(question.getSelectedAnswer());
			explanationView.setVisibility(View.VISIBLE);
		}
		else {
			explanationView.setVisibility(View.GONE);
		}
	}

	private void setSelectedAnswer(String answer) {
		currentQuestion.setSelectedAnswer(answer);
		
		String correctAnswer = currentQuestion.getCorrectAnswer();
		for (int i = 0; i < choicesView.getChildCount(); ++i) {
			Button button = (Button) choicesView.getChildAt(i);
			if (button.getText().equals(correctAnswer)) {
				button.setBackgroundResource(R.drawable.btn_correct);
			} else if (button.getText().equals(answer)) {
				button.setBackgroundResource(R.drawable.btn_incorrect);
			}

			button.setPadding(BTN_PADDING_LEFT, BTN_PADDING_TOP,
					BTN_PADDING_RIGHT, BTN_PADDING_BOTTOM);
			button.setEnabled(false);
		}
	}
	
	private void updateScore() {
		if (currentQuestion.wasCorrectlyAnswered()) {
			++score;
		}
		else {
			if (level instanceof SuddenDeathLevel) {
				gameOver = true;
				nextButton.setVisibility(View.GONE);
			}
		}
		
		++numberOfAnsweredQuestions;
		if (numberOfQuestionsToAsk == numberOfAnsweredQuestions) {
			gameOver = true;
		}
		
		if (gameOver) {
			finishButton.setVisibility(View.VISIBLE);
			finishButton.setEnabled(true);
            if (level instanceof  SuddenDeathLevel) {
                nextButton.setVisibility(View.GONE);
            }
		}
	}

	private void finishGame() {
		finish();
		Bundle bundle = new Bundle();
        bundle.putInt(ResultsActivity.PARAM_TOTAL_QUESTIONS_NUM, numberOfQuestionsToAsk);
        bundle.putInt(ResultsActivity.PARAM_CORRECT_ANSWERS_NUM, score);
		Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public int getNumberOfQuestionsToAsk() {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);

		if (level instanceof SuddenDeathLevel) {
			String key = getString(R.string.key_max_questions_suddendeath);
			return Integer.parseInt(settings.getString(key, null));
		} else {
			String key = getString(R.string.key_max_questions_normal);
			return Integer.parseInt(settings.getString(key, null));
		}
	}

	private void navigateToQuestion(int index) {
		updateCurrentQuestion(index);
		updatePrevNext();
		updateQuestion();
		updateScoreDisplay(index);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	protected QuizSQLiteOpenHelper getHelper() {
		return helper;
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "++onDestroy");
		super.onDestroy();
		helper.close();
	}

} // end of activity class
