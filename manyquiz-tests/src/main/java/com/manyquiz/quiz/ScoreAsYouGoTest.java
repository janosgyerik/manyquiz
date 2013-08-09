package com.manyquiz.quiz;

import com.manyquiz.quiz.Answer;
import com.manyquiz.quiz.IAnswer;
import com.manyquiz.quiz.IAnswerControl;
import com.manyquiz.quiz.IQuestion;
import com.manyquiz.quiz.IQuestionControl;
import com.manyquiz.quiz.IQuizControl;
import com.manyquiz.quiz.Question;
import com.manyquiz.quiz.ScoreAsYouGoQuiz;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ScoreAsYouGoTest {

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
        quiz = new ScoreAsYouGoQuiz(questions);
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
        Assert.assertTrue(question.canGotoNext());
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
