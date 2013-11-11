package com.manyquiz.common.quiz.model;

import java.io.Serializable;

public interface IAnswer extends Serializable {

    String getText();

    boolean isCorrect();

}
