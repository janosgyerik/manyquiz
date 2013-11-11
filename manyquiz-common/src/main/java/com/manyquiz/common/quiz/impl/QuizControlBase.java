package com.manyquiz.common.quiz.impl;

import com.manyquiz.common.quiz.model.IQuestion;
import com.manyquiz.common.quiz.model.IQuestionControl;
import com.manyquiz.common.quiz.model.IQuizControl;

import java.util.ArrayList;
import java.util.List;

public abstract class QuizControlBase implements IQuizControl {

    List<IQuestionControl> questionControls;

    int currentQuestionIndex;

    public QuizControlBase(List<IQuestion> questions) {

        this.questionControls = new ArrayList<IQuestionControl>();

        currentQuestionIndex = 0;

        for (IQuestion question : questions) {
            this.questionControls.add(createQuestionControl(question));
        }
    }

    public abstract IQuestionControl createQuestionControl(IQuestion question);

    @Override
    public List<IQuestionControl> getQuestionControls() {
        return questionControls;
    }

    @Override
    public int getQuestionsNum() {
        return getQuestionControls().size();
    }

    @Override
    public IQuestionControl getCurrentQuestion() {
        return questionControls.get(currentQuestionIndex);
    }

    @Override
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    @Override
    public int getScore() {
        int score = 0;
        for (IQuestionControl question : questionControls) {
            score += question.getScore();
        }
        return score;
    }

    @Override
    public boolean isGameOver() {
        for (IQuestionControl question : questionControls) {
            if (question.canChangeAnswer()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean readyToEnd() {
        return isGameOver();
    }

    @Override
    public void end() {
    }

    @Override
    public boolean canNavigateBackward() {
        return true;
    }

    @Override
    public boolean hasNextQuestion() {
        return currentQuestionIndex < questionControls.size() - 1;
    }

    @Override
    public boolean hasPrevQuestion() {
        return currentQuestionIndex > 0;
    }

    @Override
    public void gotoNextQuestion() {
        ++currentQuestionIndex;
    }

    @Override
    public void gotoPrevQuestion() {
        --currentQuestionIndex;
    }
}
