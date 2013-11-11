package com.manyquiz.common.quiz;

import com.manyquiz.common.quiz.impl.SuddenDeathQuiz;
import com.manyquiz.common.quiz.model.IQuestion;
import com.manyquiz.common.quiz.model.IQuestionControl;
import com.manyquiz.common.quiz.model.IQuizControl;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * - Cannot navigate back and forth in the questions
 * - Selecting an answer closes the question
 * - Can navigate to next question only after selecting a correct answer
 * - The game ends automatically on a wrong answer
 * - The game ends automatically when all questions are answered
 */
public class SuddenDeathTest extends QuizControlTestBase {

    @Override
    IQuizControl createQuizControl(List<IQuestion> questions) {
        return new SuddenDeathQuiz(questions);
    }

    @Test
    public void testCannotNavigateBackAndForth() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertFalse(question.canNavigateForward());
            Assert.assertFalse(question.canNavigateBackward());
        }
    }

    @Test
    public void testCorrectAnswerClosesQuestion() {
        IQuestionControl question = quiz.getCurrentQuestion();
        Assert.assertTrue(question.canChangeAnswer());
        question.getAnyCorrectAnswer().select();
        Assert.assertFalse(question.canChangeAnswer());

        Assert.assertFalse(quiz.isGameOver());
        Assert.assertTrue(question.canNavigateForward());
    }

    @Test
    public void testWrongAnswerClosesQuestionEndsTheGame() {
        IQuestionControl question = quiz.getCurrentQuestion();
        Assert.assertTrue(question.canChangeAnswer());
        question.getAnyWrongAnswer().select();
        Assert.assertFalse(question.canChangeAnswer());

        Assert.assertTrue(quiz.isGameOver());
        Assert.assertFalse(question.canNavigateForward());
    }

    @Test
    public void testQuestionIncorrectlyAnswered() {
        super.testQuestionIncorrectlyAnswered();
        Assert.assertTrue(quiz.isGameOver());
    }

    @Test
    public void testPerfectScore() {
        int totalScore = 0;

        for (IQuestionControl question : quiz.getQuestionControls()) {
            question.getAnyCorrectAnswer().select();
            int score = question.getScore();
            Assert.assertTrue(score > 0);
            totalScore += score;
        }

        Assert.assertTrue(quiz.isGameOver());
        Assert.assertEquals(totalScore, quiz.getScore());
    }

    @Test
    public void testWorstScore() {
        quiz.getCurrentQuestion().getAnyWrongAnswer().select();
        Assert.assertTrue(quiz.isGameOver());
        Assert.assertTrue(quiz.getScore() <= 0);
    }

    @Test
    public void testNavigationAtGameStart() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertFalse(question.canNavigateForward());
            Assert.assertFalse(question.canNavigateBackward());
        }
    }

    @Test
    public void testNavigationAfterGameEnd() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            question.getAnyCorrectAnswer().select();
        }
        Assert.assertTrue(quiz.isGameOver());

        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertFalse(question.canNavigateForward());
            Assert.assertFalse(question.canNavigateBackward());
        }
    }
}
