package com.manyquiz.quiz;

import com.manyquiz.quiz.impl.Answer;
import com.manyquiz.quiz.impl.Question;
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

public abstract class QuizControlTestBase {

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
        quiz = createQuizControl(questions);
    }

    abstract IQuizControl createQuizControl(List<IQuestion> questions);

    @Test
    public void testBasicSanity() {
        Assert.assertFalse(quiz.isGameOver());

        IQuestionControl question = quiz.getCurrentQuestion();
        Assert.assertTrue(question.isOpen());

        Assert.assertEquals(0, quiz.getCurrentQuestionIndex());
        Assert.assertFalse(quiz.hasPrevQuestion());

        Assert.assertTrue(quiz.getQuestionControls().size() > 1);
        Assert.assertTrue(quiz.hasNextQuestion());
    }

    protected IAnswerControl getCorrectAnswer(IQuestionControl question) {
        for (IAnswerControl answer : question.getAnswerControls()) {
            if (answer.getAnswer().isCorrect()) {
                return answer;
            }
        }
        return null;
    }

    protected IAnswerControl getWrongAnswer(IQuestionControl question) {
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
        Assert.assertFalse(question.isOpen());

        Assert.assertEquals(score + 1, quiz.getScore());
    }

    @Test
    public void testQuestionIncorrectlyAnswered() {
        int score = quiz.getScore();

        IQuestionControl question = quiz.getCurrentQuestion();
        IAnswerControl answer = getWrongAnswer(question);
        answer.select();
        Assert.assertFalse(question.isOpen());

        Assert.assertEquals(score, quiz.getScore());
    }
}
