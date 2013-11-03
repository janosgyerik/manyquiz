package com.manyquiz.quiz.impl;

import com.manyquiz.util.IChoice;

public class Level implements IChoice {

    public final String name;
    public final int difficulty;

    public Level(String name, int difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    @Override
    public String getChoiceName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s (difficulty %d)", name, difficulty);
    }
}
