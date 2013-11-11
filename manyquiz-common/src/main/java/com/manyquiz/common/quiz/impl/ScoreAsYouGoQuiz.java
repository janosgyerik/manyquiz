package com.manyquiz.common.quiz.impl;

import com.manyquiz.common.quiz.model.IQuestion;
import com.manyquiz.common.quiz.model.IQuestionControl;

import java.util.List;

public class ScoreAsYouGoQuiz extends QuizControlBase {

    public ScoreAsYouGoQuiz(List<IQuestion> questions) {
        super(questions);
    }

    @Override
    public IQuestionControl createQuestionControl(IQuestion question) {
        return new ScoreAsYouGoQuestion(question);
    }
}
