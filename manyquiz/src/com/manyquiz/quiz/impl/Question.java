package com.manyquiz.quiz.impl;

import com.manyquiz.quiz.model.IAnswer;
import com.manyquiz.quiz.model.IQuestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question implements IQuestion {

    private final String question;
    private final String explanation;

    private final List<IAnswer> shuffledAnswers;

    public Question(String question, List<IAnswer> answers, String explanation) {
        this.question = question;
        this.explanation = explanation;

        shuffledAnswers = new ArrayList<IAnswer>();

        for (IAnswer answer : answers) {
            shuffledAnswers.add(answer);
        }

        Collections.shuffle(shuffledAnswers);
    }

    @Override
    public String getText() {
        return question;
    }

    @Override
    public String getExplanation() {
        return explanation;
    }

    @Override
    public List<IAnswer> getShuffledAnswers() {
        return shuffledAnswers;
    }
}
