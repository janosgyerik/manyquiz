package com.manyquiz.android.db;

import com.manyquiz.common.quiz.model.IQuestion;
import com.manyquiz.common.quiz.model.IQuizFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DatabaseBackedQuizFactory implements IQuizFactory {

    private final QuizSQLiteOpenHelper helper;

    public DatabaseBackedQuizFactory(QuizSQLiteOpenHelper helper) {
        this.helper = helper;
    }

    @Override
    public List<IQuestion> pickRandomQuestions(int length, int level, Collection<String> categories) {
        List<IQuestion> questions = helper.getQuestions(level, categories);
        Collections.shuffle(questions);
        return length > questions.size() ? questions : questions.subList(0, length);
    }
}
