package com.manyquiz.quiz.impl;

import com.manyquiz.quiz.model.IAnswer;
import com.manyquiz.quiz.model.IAnswerControl;
import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuestionControl;

import java.util.ArrayList;
import java.util.List;

public class ScoreAsYouGoQuestion implements IQuestionControl {

    private final IQuestion question;

    private final List<IAnswerControl> answerControls;

    private boolean pending;

    ScoreAsYouGoQuestion(IQuestion question) {
        this.question = question;

        answerControls = new ArrayList<IAnswerControl>();
        for (IAnswer answer : question.getShuffledAnswers()) {
            answerControls.add(new AnswerControl(answer));
        }

        pending = true;
    }

    @Override
    public IQuestion getQuestion() {
        return question;
    }

    @Override
    public List<IAnswerControl> getAnswerControls() {
        return answerControls;
    }

    @Override
    public boolean isPending() {
        for (IAnswerControl answerControl : answerControls) {
            if (answerControl.isSelected()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canGotoNext() {
        return true;
    }

    @Override
    public boolean canGotoPrev() {
        return true;
    }

    @Override
    public int getScore() {
        if (! isPending()) {
            for (IAnswerControl answerControl : answerControls) {
                if (answerControl.isSelected() && answerControl.getAnswer().isCorrect()) {
                    return 1;
                }
            }
        }
        return 0;
    }

}
