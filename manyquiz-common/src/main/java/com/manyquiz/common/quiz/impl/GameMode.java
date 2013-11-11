package com.manyquiz.common.quiz.impl;

import com.manyquiz.common.util.IChoice;

public class GameMode implements IChoice {

    public final String value;
    public final String label;

    public GameMode(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String getChoiceValue() {
        return value;
    }

    @Override
    public String getChoiceLabel() {
        return label;
    }

    public static class ScoreAsYouGo extends GameMode {
        public ScoreAsYouGo(String label) {
            super("SCORE_AS_YOU_GO", label);
        }
    }

    public static class ScoreInTheEnd extends GameMode {
        public ScoreInTheEnd(String label) {
            super("SCORE_IN_THE_END", label);
        }
    }

    public static class SuddenDeath extends GameMode {
        public SuddenDeath(String label) {
            super("SUDDENDEATH", label);
        }
    }
}
