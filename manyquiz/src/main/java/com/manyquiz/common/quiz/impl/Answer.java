package com.manyquiz.common.quiz.impl;

import com.manyquiz.common.quiz.model.IAnswer;

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
