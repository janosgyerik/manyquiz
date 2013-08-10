package com.manyquiz.quiz.model;

import java.util.List;

public interface IQuiz {

    /**
     * Return at most N random questions, if possible, from the entire collection.
     *
     * @param N = max number of questions to return
     * @return
     */
    List<IQuestion> pickRandomQuestions(int length);

    /**
     * Return at most N random questions, if possible, of given difficulty level.
     *
     * @param N     = max number of questions to return
     * @param level = difficulty level
     * @return
     */
    List<IQuestion> pickRandomQuestions(int length, int level);
}
