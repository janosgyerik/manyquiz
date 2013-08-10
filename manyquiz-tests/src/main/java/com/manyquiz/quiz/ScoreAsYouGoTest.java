package com.manyquiz.quiz;

import com.manyquiz.quiz.impl.Answer;
import com.manyquiz.quiz.impl.Question;
import com.manyquiz.quiz.impl.ScoreAsYouGoQuiz;
import com.manyquiz.quiz.model.IAnswer;
import com.manyquiz.quiz.model.IAnswerControl;
import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuestionControl;
import com.manyquiz.quiz.model.IQuizControl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ScoreAsYouGoTest extends QuizControlTestBase {

    @Override
    IQuizControl createQuizControl(List<IQuestion> questions) {
        return new ScoreAsYouGoQuiz(questions);
    }

    @Test
    public void testSecondQuestionNavigation() {
        Assert.assertTrue(quiz.getQuestionControls().size() > 2);

        Assert.assertTrue(quiz.hasNextQuestion());
        Assert.assertTrue(quiz.getCurrentQuestion().canGotoNext());
        quiz.gotoNextQuestion();
        Assert.assertEquals(1, quiz.getCurrentQuestionIndex());

        IQuestionControl question = quiz.getCurrentQuestion();
        Assert.assertTrue(question.canGotoNext());
        Assert.assertTrue(question.canGotoPrev());
    }

    @Test
    public void testLastQuestion() {
        while (quiz.hasNextQuestion() && quiz.getCurrentQuestion().canGotoNext()) {
            quiz.gotoNextQuestion();
        }

        Assert.assertEquals(quiz.getQuestionControls().size() - 1, quiz.getCurrentQuestionIndex());

        Assert.assertFalse(quiz.hasNextQuestion());
        Assert.assertTrue(quiz.hasPrevQuestion());
    }

    @Test
    public void testGameOver() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertFalse(quiz.isGameOver());
            Assert.assertTrue(question.isPending());
            question.getAnswerControls().get(0).select();
            Assert.assertFalse(question.isPending());
        }

        Assert.assertTrue(quiz.isGameOver());
    }

    @Test
    public void testPerfectScore() {
        int totalScore = 0;

        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertFalse(quiz.isGameOver());
            getCorrectAnswer(question).select();
            int score = question.getScore();
            Assert.assertTrue(score > 0);
            totalScore += score;
        }

        Assert.assertEquals(totalScore, quiz.getScore());
        Assert.assertTrue(quiz.isGameOver());
    }

    @Test
    public void testWorstScore() {

        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertFalse(quiz.isGameOver());
            getWrongAnswer(question).select();
            Assert.assertEquals(0, question.getScore());
        }

        Assert.assertEquals(0, quiz.getScore());
        Assert.assertTrue(quiz.isGameOver());
    }
}
