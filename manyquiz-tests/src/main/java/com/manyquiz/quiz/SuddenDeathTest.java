package com.manyquiz.quiz;

import com.manyquiz.quiz.impl.SuddenDeathQuiz;
import com.manyquiz.quiz.model.IAnswerControl;
import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuestionControl;
import com.manyquiz.quiz.model.IQuizControl;

import junit.framework.Assert;

import org.junit.Test;

import java.util.List;

public class SuddenDeathTest extends QuizControlTestBase {

    @Override
    IQuizControl createQuizControl(List<IQuestion> questions) {
        return new SuddenDeathQuiz(questions);
    }

    @Test
    public void testQuestionIncorrectlyAnswered() {
        super.testQuestionIncorrectlyAnswered();
        Assert.assertTrue(quiz.isGameOver());
    }

    @Test
    public void testSecondQuestionNavigation() {
        Assert.assertTrue(quiz.getQuestionControls().size() > 2);

        Assert.assertTrue(quiz.hasNextQuestion());
        IQuestionControl question = quiz.getCurrentQuestion();
        Assert.assertFalse(question.isReadyForNext());
        IAnswerControl answer = getCorrectAnswer(question);
        answer.select();
        Assert.assertTrue(question.isReadyForNext());

        quiz.gotoNextQuestion();
        Assert.assertEquals(1, quiz.getCurrentQuestionIndex());

        question = quiz.getCurrentQuestion();
        Assert.assertFalse(question.isReadyForNext());
        Assert.assertFalse(question.isReadyForPrevious());
    }

    @Test
    public void testGameOver() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertFalse(quiz.isGameOver());
            Assert.assertTrue(question.isOpen());
            question.getAnswerControls().get(0).select();
            Assert.assertFalse(question.isOpen());
            if (question.getScore() == 0) break;
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
            break;
        }

        Assert.assertEquals(0, quiz.getScore());
        Assert.assertTrue(quiz.isGameOver());
    }
}
