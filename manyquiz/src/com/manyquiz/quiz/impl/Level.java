package com.manyquiz.quiz.impl;

import com.manyquiz.util.IChoice;

public class Level implements IChoice {

    public final int difficulty;
    public final String label;

    public Level(int difficulty, String label) {
        this.difficulty = difficulty;
        this.label = label;
    }

    @Override
    public String getChoiceValue() {
        return Integer.toString(difficulty);
    }

    @Override
    public String getChoiceLabel() {
        return label;
    }

    @Override
    public String toString() {
        return String.format("%s (difficulty %d)", label, difficulty);
    }
}
