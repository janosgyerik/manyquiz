package com.manyquiz.quiz;

import java.util.List;

public interface IQuestionControl {

    IQuestion getQuestion();

    List<IAnswerControl> getAnswerControls();

    boolean isPending();

    boolean canGotoNext();

    boolean canGotoPrev();

    int getScore();

}
