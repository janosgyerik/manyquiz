package com.manyquiz.quiz.impl;

import com.manyquiz.quiz.model.IAnswer;

public class Answer implements IAnswer {
    private final String text;
    private final boolean correct;

    public Answer(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public boolean isCorrect() {
        return correct;
    }
}
