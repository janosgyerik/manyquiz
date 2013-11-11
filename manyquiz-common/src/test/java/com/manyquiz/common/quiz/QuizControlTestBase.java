package com.manyquiz.common.quiz;

import com.manyquiz.common.quiz.impl.Answer;
import com.manyquiz.common.quiz.impl.Question;
import com.manyquiz.common.quiz.model.IAnswer;
import com.manyquiz.common.quiz.model.IAnswerControl;
import com.manyquiz.common.quiz.model.IQuestion;
import com.manyquiz.common.quiz.model.IQuestionControl;
import com.manyquiz.common.quiz.model.IQuizControl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class QuizControlTestBase {

    IQuizControl quiz;

    @Before
    public void setUp() {
        List<IQuestion> questions = createDummyQuestions();
        quiz = createQuizControl(questions);
    }

    abstract IQuizControl createQuizControl(List<IQuestion> questions);

    private List<IQuestion> createDummyQuestions() {
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
        return questions;
    }

    /**
     * Basic sanity checks on the quiz right after the start:
     * - the game is not over
     * - can change the answer on all questions
     * - the current index is 0
     * - the last index is N-1
     */
    @Test
    public void testBasicSanity() {
        Assert.assertFalse(quiz.isGameOver());

        for (IQuestionControl question : quiz.getQuestionControls()) {
            question.canChangeAnswer();
        }

        Assert.assertEquals(0, quiz.getCurrentQuestionIndex());
        while (quiz.hasNextQuestion()) {
            quiz.gotoNextQuestion();
        }
        Assert.assertEquals(quiz.getQuestionsNum() - 1, quiz.getCurrentQuestionIndex());
    }

    /**
     * Sanity checks on first question:
     * - there are more than 1 questions
     * - the current index is 0
     * - there is no previous question
     * - there is a next question
     */
    @Test
    public void testFirstQuestionSanity() {
        Assert.assertTrue(quiz.getQuestionsNum() > 1);

        Assert.assertEquals(0, quiz.getCurrentQuestionIndex());
        Assert.assertFalse(quiz.hasPrevQuestion());
        Assert.assertTrue(quiz.hasNextQuestion());
    }

    /**
     * Sanity checks on last question:
     * - there are more than 1 questions
     * - the current index is N-1
     * - there is a previous question
     * - there is no next question
     */
    @Test
    public void testLastQuestionSanity() {
        Assert.assertTrue(quiz.getQuestionControls().size() > 1);

        while (quiz.hasNextQuestion()) {
            quiz.gotoNextQuestion();
        }

        Assert.assertEquals(quiz.getQuestionsNum() - 1, quiz.getCurrentQuestionIndex());
        Assert.assertTrue(quiz.hasPrevQuestion());
        Assert.assertFalse(quiz.hasNextQuestion());
    }

    /**
     * Test the behavior of correctly answered questions:
     * - score of a correctly answered question is greater than 0
     * - total quiz score gets incremented by the score of the question
     */
    @Test
    public void testQuestionCorrectlyAnswered() {
        int quizScore = quiz.getScore();

        IQuestionControl question = quiz.getCurrentQuestion();
        IAnswerControl answer = question.getAnyCorrectAnswer();
        answer.select();
        question.close();

        int questionScore = question.getScore();
        Assert.assertTrue(questionScore > 0);
        Assert.assertEquals(quizScore + questionScore, quiz.getScore());
    }

    /**
     * Test the behavior of incorrectly answered questions:
     * - score of an incorrectly answered question is 0
     * - total quiz score does not increase after answering incorrectly
     */
    @Test
    public void testQuestionIncorrectlyAnswered() {
        int quizScore = quiz.getScore();

        IQuestionControl question = quiz.getCurrentQuestion();
        IAnswerControl answer = question.getAnyWrongAnswer();
        answer.select();
        question.close();

        Assert.assertTrue(question.getScore() <= quizScore);
        Assert.assertEquals(quizScore, quiz.getScore());
    }

    /**
     * Test the sanity of a quiz with only one question:
     * - passes the basic sanity
     * - there is only one question
     * - there is no next question
     * - there is no previous question
     */
    @Test
    public void testSingleQuestionQuizSanity() {
        List<IQuestion> questions = Collections.singletonList(createDummyQuestions().get(0));
        quiz = createQuizControl(questions);
        testBasicSanity();

        Assert.assertEquals(1, quiz.getQuestionsNum());
        Assert.assertFalse(quiz.hasPrevQuestion());
        Assert.assertFalse(quiz.hasNextQuestion());
    }

    @Test
    public abstract void testNavigationAtGameStart();

    @Test
    public abstract void testNavigationAfterGameEnd();
}