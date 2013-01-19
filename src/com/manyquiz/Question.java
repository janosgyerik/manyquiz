package com.manyquiz;

import java.util.List;

public class Question implements IQuestion {
	
	private final String category;
	private final String text;
	
	private final List<String> choices;
	private final String correctAnswer;
	
	private String selectedAnswer;
	
	public Question(String category, String text, List<String> choices, int answer) {
		this.category = category;
		this.text = text;
		this.choices = choices;
		this.correctAnswer = choices.get(answer);
	}
	
	@Override
	public String getCategory() {
		return category;
	}
	
	@Override
	public String getText() {
		return text;
	}
	
	@Override
	public List<String> getChoices() {
		return choices;
	}
	
	@Override
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	
	@Override
	public String getSelectedAnswer() {
		return selectedAnswer;
	}

	@Override
	public void setSelectedAnswer(String answer) {
		this.selectedAnswer = answer;
	}

}
