package com.manyquiz.quiz.model;

import java.io.Serializable;
import java.util.List;

public interface IQuestion extends Serializable {

    /**
     * Get the question text itself.
     *
     * @return the question text itself
     */
    String getText();

    /**
     * Get the explanation of the correct answer,
     * with the aim to teach the user something.
     *
     * @return the explanation of the correct answer
     */
    String getExplanation();

    /**
     * Get the possible answers, both correct and decoy answers,
     * already shuffled. Always return the shuffled answers in
     * the same order, do not reshuffle between calls,
     * for a consistent, non-confusing user interface.
     *
     * @return shuffled answers
     */
    List<IAnswer> getShuffledAnswers();

}
