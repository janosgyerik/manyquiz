package com.manyquiz.quiz;

import java.util.ArrayList;
import java.util.List;

public class SuddenDeathQuestion implements IQuestionControl {

    private final IQuizControl quiz;

    private final IQuestion question;

    private final List<IAnswerControl> answerControls;

    SuddenDeathQuestion(IQuizControl quiz, IQuestion question) {
        this.quiz = quiz;
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
        if (quiz.isGameOver()) return false;
        for (IAnswerControl answerControl : answerControls) {
            if (answerControl.isSelected()) {
                if (answerControl.getAnswer().isCorrect()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canGotoPrev() {
        return false;
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
