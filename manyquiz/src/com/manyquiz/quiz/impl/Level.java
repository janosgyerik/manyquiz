package com.manyquiz.quiz.impl;

import com.manyquiz.util.IChoice;

import java.io.Serializable;

public class Level implements Serializable, IChoice {

    private static final long serialVersionUID = -2650243060379640957L;

    private final String id;
    private final String name;
    private final int level;

    public Level(String id, String name, int level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return String.format("%s (level %d)", name, level);
    }

    @Override
    public String getChoiceName() {
        return getName();
    }
}
