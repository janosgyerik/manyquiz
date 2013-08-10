package com.manyquiz.quiz.model;

import java.io.Serializable;
import java.util.List;

public interface IQuizControl extends Serializable {

    List<IQuestionControl> getQuestionControls();

    int getCurrentQuestionIndex();

    int getScore();

    boolean isGameOver();

    boolean readyToEndGame();

    void endGame();

    boolean hasNextQuestion();

    boolean hasPrevQuestion();

    IQuestionControl getCurrentQuestion();

    void gotoNextQuestion();

    void gotoPrevQuestion();
}
