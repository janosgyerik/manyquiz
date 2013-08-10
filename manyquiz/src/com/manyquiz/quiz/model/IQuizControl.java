package com.manyquiz.quiz.model;

import java.io.Serializable;
import java.util.List;

public interface IQuizControl extends Serializable {

    List<IQuestionControl> getQuestionControls();

    int getQuestionsNum();

    IQuestionControl getCurrentQuestion();

    int getCurrentQuestionIndex();

    int getScore();

    boolean isGameOver();

    boolean readyToEnd();

    void end();

    boolean canNavigateBack();

    boolean hasNextQuestion();

    boolean hasPrevQuestion();

    void gotoNextQuestion();

    void gotoPrevQuestion();
}
