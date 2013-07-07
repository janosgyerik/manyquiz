package com.manyquiz;

import java.util.Collections;
import java.util.List;

public class DatabaseBackedQuiz implements IQuiz {

    private final QuizSQLiteOpenHelper helper;

    public DatabaseBackedQuiz(QuizSQLiteOpenHelper helper) {
        this.helper = helper;
    }

    @Override
    public List<IQuestion> pickRandomQuestions(int length) {
        List<IQuestion> questions = helper.getQuestions();
        Collections.shuffle(questions);
        return length > questions.size() ? questions : questions.subList(0, length);
    }

    @Override
    public List<IQuestion> pickRandomQuestions(int length, int level) {
        List<IQuestion> questions = helper.getQuestions(level);
        Collections.shuffle(questions);
        return length > questions.size() ? questions : questions.subList(0, length);
    }
}
