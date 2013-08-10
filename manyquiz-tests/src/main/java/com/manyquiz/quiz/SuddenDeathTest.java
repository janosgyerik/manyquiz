package com.manyquiz.quiz;

import com.manyquiz.quiz.impl.Answer;
import com.manyquiz.quiz.impl.Question;
import com.manyquiz.quiz.impl.SuddenDeathQuiz;
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

public class SuddenDeathTest {

    IQuizControl quiz;

    @Before
    public void setUp() {
        List<IQuestion> questions = new ArrayList<IQuestion>();
        for (int i = 1; i < 5; ++i) {
            String text = "What is..." + i;
            String explanation = "Because..." + i;
            List<IAnswer> answers = new ArrayList<IAnswer>();
            answers.add(new Answer("This is correct" + i, true));
            answers.add(new Answer("This is decoy1-" + i, false));
            answers.add(new Answer("This is decoy2-" + i, false));
            answers.add(new Answer("This is decoy3-" + i, false));
            IQuestion question = new Question(text, answers, explanation);
            questions.add(question);
        }
        quiz = new SuddenDeathQuiz(questions);
    }

    @Test
    public void testBasicSanity() {
        Assert.assertFalse(quiz.isGameOver());

        IQuestionControl question = quiz.getCurrentQuestion();
        Assert.assertTrue(question.isPending());

        Assert.assertEquals(0, quiz.getCurrentQuestionIndex());
        Assert.assertFalse(quiz.hasPrevQuestion());

        Assert.assertTrue(quiz.getQuestionControls().size() > 1);
        Assert.assertTrue(quiz.hasNextQuestion());
        Assert.assertFalse(question.canGotoNext());
    }

    private IAnswerControl getCorrectAnswer(IQuestionControl question) {
        for (IAnswerControl answer : question.getAnswerControls()) {
            if (answer.getAnswer().isCorrect()) {
                return answer;
            }
        }
        return null;
    }

    private IAnswerControl getWrongAnswer(IQuestionControl question) {
        for (IAnswerControl answer : question.getAnswerControls()) {
            if (! answer.getAnswer().isCorrect()) {
                return answer;
            }
        }
        return null;
    }

    @Test
    public void testQuestionCorrectlyAnswered() {
        int score = quiz.getScore();

        IQuestionControl question = quiz.getCurrentQuestion();
        IAnswerControl answer = getCorrectAnswer(question);
        answer.select();
        Assert.assertFalse(question.isPending());

        Assert.assertEquals(score + 1, quiz.getScore());
    }

    @Test
    public void testQuestionIncorrectlyAnswered() {
        int score = quiz.getScore();

        IQuestionControl question = quiz.getCurrentQuestion();
        IAnswerControl answer = getWrongAnswer(question);
        answer.select();
        Assert.assertFalse(question.isPending());

        Assert.assertEquals(score, quiz.getScore());
        Assert.assertTrue(quiz.isGameOver());
    }

    @Test
    public void testSecondQuestionNavigation() {
        Assert.assertTrue(quiz.getQuestionControls().size() > 2);

        Assert.assertTrue(quiz.hasNextQuestion());
        IQuestionControl question = quiz.getCurrentQuestion();
        Assert.assertFalse(question.canGotoNext());
        IAnswerControl answer = getCorrectAnswer(question);
        answer.select();
        Assert.assertTrue(question.canGotoNext());

        quiz.gotoNextQuestion();
        Assert.assertEquals(1, quiz.getCurrentQuestionIndex());

        question = quiz.getCurrentQuestion();
        Assert.assertFalse(question.canGotoNext());
        Assert.assertFalse(question.canGotoPrev());
    }

    @Test
    public void testGameOver() {
        for (IQuestionControl question : quiz.getQuestionControls()) {
            Assert.assertFalse(quiz.isGameOver());
            Assert.assertTrue(question.isPending());
            question.getAnswerControls().get(0).select();
            Assert.assertFalse(question.isPending());
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
