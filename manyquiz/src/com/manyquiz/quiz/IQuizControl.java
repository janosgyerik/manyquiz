package com.manyquiz.quiz;

import java.util.List;

public interface IQuizControl {

    List<IQuestionControl> getQuestionControls();

    int getCurrentQuestionIndex();

    int getScore();

    int getScoreAsPercentage();

    boolean isGameOver();

    boolean hasNextQuestion();

    boolean hasPrevQuestion();

    IQuestionControl getCurrentQuestion();

    void gotoNextQuestion();

    void gotoPrevQuestion();
}
