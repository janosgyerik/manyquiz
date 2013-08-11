package com.manyquiz.quiz;

import com.manyquiz.quiz.impl.ScoreInTheEndQuiz;
import com.manyquiz.quiz.model.IAnswerControl;
import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuestionControl;
import com.manyquiz.quiz.model.IQuizControl;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * - Selecting an answer does not close the question
 * - Can change the answer before the game is over
 * - The game ends by explicit user action, not automatically
 * - Can navigate to next question only after selecting an answer
 */
public class ScoreInTheEndTest extends QuizControlTestBase {

    @Override
    IQuizControl createQuizControl(List<IQuestion> questions) {
        return new ScoreInTheEndQuiz(questions);
    }

    @Test
    public void testCanChangeCorrectAnswer() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertTrue(question.canChangeAnswer());
            IAnswerControl answer = question.getAnyCorrectAnswer();
            answer.select();
            Assert.assertTrue(question.canChangeAnswer());
        }
    }

    @Test
    public void testCanChangeWrongAnswer() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertTrue(question.canChangeAnswer());
            IAnswerControl answer = question.getAnyWrongAnswer();
            answer.select();
            Assert.assertTrue(question.canChangeAnswer());
        }
    }

    @Test
    public void testCanCorrectWrongAnswer() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            question.getAnyWrongAnswer().select();
            question.getAnyCorrectAnswer().select();
            question.close();
            Assert.assertTrue(question.isCorrectlyAnswered());
        }
    }

    @Test
    public void testCanBotchCorrectAnswer() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            question.getAnyCorrectAnswer().select();
            question.getAnyWrongAnswer().select();
            question.close();
            Assert.assertFalse(question.isCorrectlyAnswered());
        }
    }

    @Test
    public void testCanOnlyAdvanceAfterAnswering() {
        IQuestionControl question = quiz.getCurrentQuestion();
        Assert.assertFalse(question.canNavigateForward());
        IAnswerControl answer = question.getAnswerControls().get(0);
        answer.select();
        Assert.assertTrue(question.canNavigateForward());
    }

    @Test
    public void testCannotSkipQuestions() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertFalse(question.canNavigateForward());
            Assert.assertTrue(question.canNavigateBackward());
        }
    }

    @Test
    public void testQuizEndClosesAllQuestions() {
        quiz.end();
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertFalse(question.canChangeAnswer());
        }
    }

    @Test
    public void testGameDoesNotEndWhenAllAnswered() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertFalse(quiz.isGameOver());
            Assert.assertTrue(question.canChangeAnswer());
            question.getAnswerControls().get(0).select();
            Assert.assertTrue(question.canChangeAnswer());
        }
        Assert.assertFalse(quiz.isGameOver());
    }

    @Test
    public void testScoreForCorrectAfterClosed() {
        IQuestionControl question = quiz.getCurrentQuestion();
        IAnswerControl answer = question.getAnyCorrectAnswer();
        answer.select();
        Assert.assertEquals(0, question.getScore());
        question.close();
        Assert.assertTrue(question.getScore() > 0);
    }

    @Test
    public void testScoreForCorrectAfterGameOver() {
        IQuestionControl question = quiz.getCurrentQuestion();
        IAnswerControl answer = question.getAnyCorrectAnswer();
        answer.select();
        Assert.assertEquals(0, question.getScore());
        quiz.end();
        Assert.assertTrue(question.getScore() > 0);
    }

    @Test
    public void testScoreForIncorrectAfterClosed() {
        IQuestionControl question = quiz.getCurrentQuestion();
        IAnswerControl answer = question.getAnyWrongAnswer();
        answer.select();
        Assert.assertEquals(0, question.getScore());
        question.close();
        Assert.assertTrue(question.getScore() <= 0);
    }

    @Test
    public void testScoreForIncorrectAfterGameOver() {
        IQuestionControl question = quiz.getCurrentQuestion();
        IAnswerControl answer = question.getAnyWrongAnswer();
        answer.select();
        Assert.assertEquals(0, question.getScore());
        quiz.end();
        Assert.assertTrue(question.getScore() <= 0);
    }

    @Test
    public void testPerfectScore() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            question.getAnyCorrectAnswer().select();
        }
        quiz.end();

        int totalScore = 0;
        for (IQuestionControl question : quiz.getQuestionControls()) {
            int score = question.getScore();
            Assert.assertTrue(score > 0);
            totalScore += score;
        }

        Assert.assertEquals(totalScore, quiz.getScore());
    }

    @Test
    public void testWorstScore() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            question.getAnyWrongAnswer().select();
        }
        quiz.end();

        int totalScore = 0;
        for (IQuestionControl question : quiz.getQuestionControls()) {
            int score = question.getScore();
            Assert.assertTrue(score <= 0);
            totalScore += score;
        }

        Assert.assertEquals(totalScore, quiz.getScore());
    }

    @Override
    public void testNavigationAtGameStart() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertFalse(question.canNavigateForward());
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
