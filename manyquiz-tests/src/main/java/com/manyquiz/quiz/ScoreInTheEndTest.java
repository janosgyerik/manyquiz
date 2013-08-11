package com.manyquiz.quiz;

import com.manyquiz.quiz.impl.ScoreInTheEndQuiz;
import com.manyquiz.quiz.model.IAnswerControl;
import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuestionControl;
import com.manyquiz.quiz.model.IQuizControl;

import junit.framework.Assert;

import org.junit.Test;

import java.util.List;

/**
 * - Selecting an answer does not end the question
 * - Can change the answer before the game is over
 * - The game ends by explicit user action
 * - The 'end the game' action becomes available after all questions have a selected answer
 *      // this is controlled by the UI, not by the model
 * - Can navigate to next question only after selecting an answer
 */
public class ScoreInTheEndTest extends QuizControlTestBase {

    @Override
    IQuizControl createQuizControl(List<IQuestion> questions) {
        return new ScoreInTheEndQuiz(questions);
    }

    @Test
    public void testCanChangeAnswer() {
        IQuestionControl question = quiz.getCurrentQuestion();
        Assert.assertTrue(question.isOpen());
        IAnswerControl answer = question.getAnswerControls().get(0);
        answer.select();
        Assert.assertTrue(question.isOpen());
    }

    @Test
    public void testCannotSkipQuestions() {
        IQuestionControl question = quiz.getCurrentQuestion();
        Assert.assertFalse(question.isReadyForNext());
        IAnswerControl answer = question.getAnswerControls().get(0);
        answer.select();
        Assert.assertTrue(question.isReadyForNext());
    }

    @Override
    public void testQuestionCorrectlyAnswered() {
        // skip this test
    }

    @Override
    public void testQuestionIncorrectlyAnswered() {
        // skip this test
    }
}
