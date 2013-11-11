package com.manyquiz.common.quiz.impl;

import com.manyquiz.common.quiz.model.IAnswer;
import com.manyquiz.common.quiz.model.IAnswerControl;

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
    public void deselect() {
        this.selected = false;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }
}
