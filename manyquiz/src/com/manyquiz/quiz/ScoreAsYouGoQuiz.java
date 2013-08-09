package com.manyquiz.quiz;

import java.util.ArrayList;
import java.util.List;

public class ScoreAsYouGoQuiz implements IQuizControl {

    List<IQuestionControl> questions;
    List<IQuestion> originalQuestions;

    int currentQuestionIndex;

    public ScoreAsYouGoQuiz(List<IQuestion> questions) {
        originalQuestions = new ArrayList<IQuestion>();
        originalQuestions.addAll(questions);

        this.questions = new ArrayList<IQuestionControl>();

        currentQuestionIndex = 0;

        for (IQuestion question : originalQuestions) {
            this.questions.add(new ScoreAsYouGoQuestion(question));
        }
    }

    @Override
    public List<IQuestionControl> getQuestionControls() {
        return questions;
    }

    @Override
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    @Override
    public int getScore() {
        int score = 0;
        for (IQuestionControl question : questions) {
            score += question.getScore();
        }
        return score;
    }

    @Override
    public boolean isGameOver() {
        for (IQuestionControl question : questions) {
            if (question.isPending()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size() - 1;
    }

    @Override
    public boolean hasPrevQuestion() {
        return currentQuestionIndex > 0;
    }

    @Override
    public IQuestionControl getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    @Override
    public void gotoNextQuestion() {
        if (hasNextQuestion()) {
            ++currentQuestionIndex;
        }
    }

    @Override
    public void gotoPrevQuestion() {
        if (hasPrevQuestion()) {
            --currentQuestionIndex;
        }
    }
}
