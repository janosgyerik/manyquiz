package com.manyquiz;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
    private static final String TAG = MainActivity.class.getSimpleName();

	private List<IQuestion> questions;
	private IQuestion currentQuestion;
	private int currentQuestionIndex = 0;

	private TextView questionView;
	private LinearLayout choicesView;
	private ImageButton prevButton;
	private ImageButton nextButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "++onCreate");
		setContentView(R.layout.activity_main);

		IQuiz quiz = new ComputerQuiz();
		questions = quiz.pickRandomQuestions(15);

		questionView = (TextView) findViewById(R.id.question);
		choicesView = (LinearLayout) findViewById(R.id.choices);

		prevButton = (ImageButton) findViewById(R.id.btn_prev);
		prevButton.setOnClickListener(new PrevNextClickListener(-1));
		nextButton = (ImageButton) findViewById(R.id.btn_next);
		nextButton.setOnClickListener(new PrevNextClickListener(1));

		// TODO handle next and previous

		navigateToQuestion(0);
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

	class ChoiceClickListener implements OnClickListener {

		private String answer;

		ChoiceClickListener(String answer) {
			this.answer = answer;
		}

		@Override
		public void onClick(View arg0) {
			setSelectedAnswer(answer);
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

	private void updatePrevNext() {
		if (currentQuestionIndex == 0) {
			prevButton.setVisibility(View.GONE);
			nextButton.setVisibility(View.VISIBLE);
			prevButton.setEnabled(false);
			nextButton.setEnabled(true);
		} else if (currentQuestionIndex == questions.size() - 1) {
			prevButton.setVisibility(View.VISIBLE);
			nextButton.setVisibility(View.GONE);
			prevButton.setEnabled(true);
			nextButton.setEnabled(false);
		} else {
			prevButton.setVisibility(View.VISIBLE);
			nextButton.setVisibility(View.VISIBLE);
			prevButton.setEnabled(true);
			nextButton.setEnabled(true);
		}
	}

	private void updateQuestion() {
		IQuestion question = questions.get(currentQuestionIndex);

		questionView.setText(question.getText());

		String selectedAnswer = question.getSelectedAnswer();
		choicesView.removeAllViews();
		for (String choice : question.getChoices()) {
			Button button = new Button(this);
			button.setText(choice);
			button.setPadding(0, 15, 0, 15);
			if (selectedAnswer == null) {
				button.setOnClickListener(new ChoiceClickListener(choice));
			}
			choicesView.addView(button);
		}
		if (selectedAnswer != null) {
			setSelectedAnswer(selectedAnswer);
		}
	}
	
	private void setSelectedAnswer(String answer) {
		currentQuestion.setSelectedAnswer(answer);
		for (int i = 0; i < choicesView.getChildCount(); ++i) {
			Button button = (Button) choicesView.getChildAt(i);
			if (button.getText().equals(answer)) {
				if (currentQuestion.isCorrect()) {
					button.setBackgroundResource(R.drawable.btn_correct);
				}
				else {
					button.setBackgroundResource(R.drawable.btn_incorrect);
				}
				button.setPadding(0, 15, 0, 15);
			}
			button.setEnabled(false);
		}
	}

	private void navigateToQuestion(int index) {
		updateCurrentQuestion(index);
		updatePrevNext();
		updateQuestion();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
