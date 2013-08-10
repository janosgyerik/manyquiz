package com.manyquiz.quiz.model;

import java.io.Serializable;
import java.util.List;

public interface IQuestion extends Serializable {

    String getText();

    String getExplanation();

    List<IAnswer> getShuffledAnswers();

}
