package com.manyquiz.quiz.model;

import java.util.List;

public interface IQuizFactory {

    /**
     * Return at most N random questions, if possible, from the entire collection.
     *
     * @param length = max number of questions to return
     * @return list of questions
     */
    List<IQuestion> pickRandomQuestions(int length);

    /**
     * Return at most N random questions, if possible, of given difficulty level.
     *
     * @param length = max number of questions to return
     * @param level  = difficulty level
     * @return list of questions
     */
    List<IQuestion> pickRandomQuestions(int length, int level);
}
