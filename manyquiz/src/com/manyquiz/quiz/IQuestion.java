package com.manyquiz.quiz;

import java.util.List;

public interface IQuestion {

    String getText();

    String getExplanation();

    List<IAnswer> getShuffledAnswers();

}
