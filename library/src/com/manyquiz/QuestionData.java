package com.manyquiz;

import java.util.ArrayList;
import java.util.List;

public class QuestionData {

    String id;
    String text;
    String category;
    String explanation;
    List<String> choices = new ArrayList<String>();
    String correctAnswer;

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void addAnswer(String answer, boolean isCorrect) {
        if (isCorrect) {
            correctAnswer = answer;
        } else {
            choices.add(answer);
        }
    }

}
