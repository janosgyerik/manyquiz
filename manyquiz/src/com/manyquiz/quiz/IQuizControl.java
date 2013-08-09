package com.manyquiz.quiz;

import java.io.Serializable;
import java.util.List;

public interface IQuizControl extends Serializable {

    List<IQuestionControl> getQuestionControls();

    int getCurrentQuestionIndex();

    int getScore();

    boolean isGameOver();

    boolean hasNextQuestion();

    boolean hasPrevQuestion();

    IQuestionControl getCurrentQuestion();

    void gotoNextQuestion();

    void gotoPrevQuestion();
}
