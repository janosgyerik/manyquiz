package com.manyquiz.quiz.impl;

import com.manyquiz.quiz.model.IAnswer;
import com.manyquiz.quiz.model.IAnswerControl;
import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuestionControl;

import java.util.ArrayList;
import java.util.List;

public abstract class QuestionControlBase implements IQuestionControl {

    private final IQuestion question;

    protected final List<IAnswerControl> answerControls;

    protected boolean closed;
    private boolean marked;

    QuestionControlBase(IQuestion question) {
        this.question = question;

        answerControls = new ArrayList<IAnswerControl>();
        for (IAnswer answer : question.getShuffledAnswers()) {
            answerControls.add(new AnswerControl(answer));
        }
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
    public boolean canChangeAnswer() {
        for (IAnswerControl answerControl : answerControls) {
            if (answerControl.isSelected()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isCorrectlyAnswered() {
        if (!canChangeAnswer()) {
            for (IAnswerControl answerControl : answerControls) {
                if (answerControl.isSelected() && !answerControl.getAnswer().isCorrect()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void close() {
        closed = true;
    }

    @Override
    public boolean canNavigateForward() {
        return true;
    }

    @Override
    public boolean canNavigateBackward() {
        return true;
    }

    @Override
    public int getScore() {
        if (!canChangeAnswer()) {
            for (IAnswerControl answerControl : answerControls) {
                if (answerControl.isSelected() && answerControl.getAnswer().isCorrect()) {
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public IAnswerControl getAnyCorrectAnswer() {
        for (IAnswerControl answer : answerControls) {
            if (answer.getAnswer().isCorrect()) {
                return answer;
            }
        }
        return null;
    }

    @Override
    public IAnswerControl getAnyWrongAnswer() {
        for (IAnswerControl answer : answerControls) {
            if (!answer.getAnswer().isCorrect()) {
                return answer;
            }
        }
        return null;
    }

    @Override
    public void mark() {
        marked = true;
    }

    @Override
    public boolean isMarked() {
        return marked;
    }
}
