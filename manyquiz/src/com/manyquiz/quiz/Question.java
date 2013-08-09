package com.manyquiz.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question implements IQuestion {

    private final String question;
    private final String explanation;

    private final List<IAnswer> correctAnswers;
    private final List<IAnswer> decoyAnswers;
    private final List<IAnswer> shuffledAnswers;

    public Question(String question, List<IAnswer> answers, String explanation) {
        this.question = question;
        this.explanation = explanation;

        correctAnswers = new ArrayList<IAnswer>();
        decoyAnswers = new ArrayList<IAnswer>();
        shuffledAnswers = new ArrayList<IAnswer>();

        for (IAnswer answer : answers) {
            shuffledAnswers.add(answer);
            if (answer.isCorrect()) {
                correctAnswers.add(answer);
            }
            else {
                decoyAnswers.add(answer);
            }
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
