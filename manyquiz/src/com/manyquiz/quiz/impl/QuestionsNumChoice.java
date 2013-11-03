package com.manyquiz.quiz.impl;

import com.manyquiz.util.IChoice;

public class QuestionsNumChoice implements IChoice {
    public final int num;
    public final String label;

    public QuestionsNumChoice(int num) {
        this.num = num;
        this.label = String.format("%d questions", num);
    }

    @Override
    public String getChoiceValue() {
        return Integer.toString(num);
    }

    @Override
    public String getChoiceLabel() {
        return label;
    }
}
