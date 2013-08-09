package com.manyquiz.quiz;

import java.io.Serializable;
import java.util.List;

public interface IQuestionControl extends Serializable {

    IQuestion getQuestion();

    List<IAnswerControl> getAnswerControls();

    boolean isPending();

    boolean canGotoNext();

    boolean canGotoPrev();

    int getScore();

}
