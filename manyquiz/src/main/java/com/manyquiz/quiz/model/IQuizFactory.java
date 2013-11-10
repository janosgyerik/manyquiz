package com.manyquiz.quiz.model;

import java.util.Collection;
import java.util.List;

public interface IQuizFactory {

    /**
     * Return at most N random questions, of given difficulty level, of listed categories.
     *
     * @param length = max number of questions to return
     * @param level  = difficulty level
     * @param categories = categories to include
     * @return list of questions
     */
    List<IQuestion> pickRandomQuestions(int length, int level, Collection<String> categories);
}
