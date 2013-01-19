package com.manyquiz;

import java.util.List;

public interface IQuestion {
	
	String getCategory();
	String getText();
	List<String> getChoices();
	String getCorrectAnswer();
	
	String getSelectedAnswer();
	void setSelectedAnswer(String choice);

}
