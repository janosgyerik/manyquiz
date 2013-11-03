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

    public static class ScoreAsYouGo extends GameMode {
        public ScoreAsYouGo(String name) {
            super("SCORE_AS_YOU_GO", name);
        }
    }

    public static class ScoreInTheEnd extends GameMode {
        public ScoreInTheEnd(String name) {
            super("SCORE_IN_THE_END", name);
        }
    }

    public static class SuddenDeath extends GameMode {
        public SuddenDeath(String name) {
            super("SUDDENDEATH", name);
        }
    }
}
