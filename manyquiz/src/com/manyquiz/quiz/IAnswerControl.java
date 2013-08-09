package com.manyquiz.quiz;

import java.io.Serializable;

public interface IAnswerControl extends Serializable {

    IAnswer getAnswer();

    void select();

    boolean isSelected();
}
