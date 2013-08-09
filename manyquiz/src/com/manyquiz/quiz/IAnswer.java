package com.manyquiz.quiz;

import java.io.Serializable;

public interface IAnswer extends Serializable {

    String getText();

    boolean isCorrect();

}
