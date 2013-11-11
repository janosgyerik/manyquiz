package com.manyquiz.common.quiz;

import com.manyquiz.common.quiz.impl.ScoreAsYouGoQuiz;
import com.manyquiz.common.quiz.model.IAnswerControl;
import com.manyquiz.common.quiz.model.IQuestion;
import com.manyquiz.common.quiz.model.IQuestionControl;
import com.manyquiz.common.quiz.model.IQuizControl;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * - Can navigate freely back and forth in the questions
 * - Selecting an answer closes the question
 * - The game ends automatically when all questions are answered
 */
public class ScoreAsYouGoTest extends QuizControlTestBase {

    @Override
    IQuizControl createQuizControl(List<IQuestion> questions) {
        return new ScoreAsYouGoQuiz(questions);
    }

    /**
     * - can navigate forward and backward always
     * - can navigate forward without answering
     * - can navigate forward after correct answer
     * - can navigate forward after wrong answer
     */
    @Test
    public void testCanNavigateFreelyBackAndForth() {
        Assert.assertTrue(quiz.getQuestionControls().size() > 2);

        Assert.assertEquals(0, quiz.getCurrentQuestionIndex());
        Assert.assertTrue(quiz.getCurrentQuestion().canNavigateForward());
        Assert.assertTrue(quiz.getCurrentQuestion().canNavigateBackward());

        Assert.assertTrue(quiz.hasNextQuestion());
        quiz.gotoNextQuestion();

        Assert.assertEquals(1, quiz.getCurrentQuestionIndex());
        quiz.getCurrentQuestion().getAnyCorrectAnswer().select();
        Assert.assertTrue(quiz.getCurrentQuestion().canNavigateForward());
        Assert.assertTrue(quiz.getCurrentQuestion().canNavigateBackward());

        Assert.assertTrue(quiz.hasNextQuestion());
        quiz.gotoNextQuestion();

        Assert.assertEquals(2, quiz.getCurrentQuestionIndex());
        quiz.getCurrentQuestion().getAnyWrongAnswer().select();
        Assert.assertTrue(quiz.getCurrentQuestion().canNavigateForward());
        Assert.assertTrue(quiz.getCurrentQuestion().canNavigateBackward());
    }

    @Test
    public void testCannotChangeCorrectAnswer() {
        IQuestionControl question = quiz.getCurrentQuestion();
        Assert.assertTrue(question.canChangeAnswer());
        IAnswerControl answer = question.getAnyCorrectAnswer();
        answer.select();
        Assert.assertFalse(question.canChangeAnswer());
    }

    @Test
    public void testCannotChangeWrongAnswer() {
        IQuestionControl question = quiz.getCurrentQuestion();
        Assert.assertTrue(question.canChangeAnswer());
        IAnswerControl answer = question.getAnyWrongAnswer();
        answer.select();
        Assert.assertFalse(question.canChangeAnswer());
    }

    @Test
    public void testGameEndsWhenAllAnswered() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertFalse(quiz.isGameOver());
            Assert.assertTrue(question.canChangeAnswer());
            question.getAnswerControls().get(0).select();
            Assert.assertFalse(question.canChangeAnswer());
        }

        Assert.assertTrue(quiz.isGameOver());
    }

    @Test
    public void testCanSkipQuestions() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertTrue(question.canNavigateForward());
            Assert.assertTrue(question.canNavigateBackward());
        }
    }

    @Test
    public void testPerfectScore() {
        int totalScore = 0;

        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertFalse(quiz.isGameOver());
            question.getAnyCorrectAnswer().select();
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
            question.getAnyWrongAnswer().select();
            Assert.assertTrue(question.getScore() <= 0);
        }

        Assert.assertTrue(quiz.getScore() <= 0);
        Assert.assertTrue(quiz.isGameOver());
    }

    @Override
    public void testNavigationAtGameStart() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertTrue(question.canNavigateForward());
            Assert.assertTrue(question.canNavigateBackward());
        }
    }

    @Override
    public void testNavigationAfterGameEnd() {
        quiz.end();
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertTrue(question.canNavigateForward());
            Assert.assertTrue(question.canNavigateBackward());
        }
    }
}
