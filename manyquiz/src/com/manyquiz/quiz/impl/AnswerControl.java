package com.manyquiz.quiz.impl;

import com.manyquiz.quiz.model.IAnswer;
import com.manyquiz.quiz.model.IAnswerControl;

public class AnswerControl implements IAnswerControl {

    private final IAnswer answer;

    private boolean selected;

    public AnswerControl(IAnswer answer) {
        this.answer = answer;
    }

    @Override
    public IAnswer getAnswer() {
        return answer;
    }

    @Override
    public void select() {
        this.selected = true;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }
}
