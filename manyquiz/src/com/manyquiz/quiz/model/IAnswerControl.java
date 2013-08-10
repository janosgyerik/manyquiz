package com.manyquiz.quiz.model;

import java.io.Serializable;

public interface IAnswerControl extends Serializable {

    IAnswer getAnswer();

    void select();

    void unselect();

    boolean isSelected();
}
