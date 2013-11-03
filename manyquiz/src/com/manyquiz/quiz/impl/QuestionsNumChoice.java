package com.manyquiz.quiz.impl;

import com.manyquiz.util.IChoice;

public class QuestionsNumChoice implements IChoice {
    public final int num;

    public QuestionsNumChoice(int num) {
        this.num = num;
    }

    @Override
    public String getChoiceName() {
        return String.format("%d questions", num);
    }
}
