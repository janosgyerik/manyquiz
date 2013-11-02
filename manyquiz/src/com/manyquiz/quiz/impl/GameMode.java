package com.manyquiz.quiz.impl;

import com.manyquiz.util.IChoice;

public class GameMode implements IChoice {

    public final String id;
    private final String name;

    public GameMode(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getChoiceName() {
        return name;
    }
}
