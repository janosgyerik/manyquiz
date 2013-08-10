package com.manyquiz.quiz.model;

import java.io.Serializable;
import java.util.List;

public interface IQuestionControl extends Serializable {

    IQuestion getQuestion();

    List<IAnswerControl> getAnswerControls();

    boolean isPending();

    void close();

    boolean canGotoNext();

    boolean canGotoPrev();

    int getScore();
}
