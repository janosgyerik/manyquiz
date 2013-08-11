package com.manyquiz.quiz.model;

import java.io.Serializable;
import java.util.List;

/**
 * A question control keeps track of the state of a question.
 */
public interface IQuestionControl extends Serializable {

    /**
     * Get the question object tracked by this controller.
     * @return question associated with this control
     */
    IQuestion getQuestion();

    /**
     * Get the list of answer control objects.
     * An answer control keeps track of the state of an answer.
     * @return list of answer control objects
     */
    List<IAnswerControl> getAnswerControls();

    /**
     * Check if the question is still open for selecting or
     * changing answers.
     * @return is open for changes
     */
    boolean canChangeAnswer();

    /**
     * Close the question, prohibit selecting or changing answers.
     */
    void close();

    /**
     * Check if the question was correctly answered or not.
     * @return is correctly answered
     */
    boolean isCorrectlyAnswered();

    /**
     * Check if the user is allowed to navigate to the next question,
     * given the current state of the question.
     * @return is the user allowed to navigate to the next question or not
     */
    boolean isReadyForNext();

    /**
     * Check if the user is allowed to navigate to the previous question,
     * given the current state of the question.
     * @return is the user allowed to navigate to the previous question or not
     */
    boolean isReadyForPrevious();

    /**
     * If the question is closed, return the score gained from this question.
     * Otherwise return 0.
     * @return score gained from the question if closed, or 0 if not closed.
     */
    int getScore();

    /**
     * Convenience method to get any correct answer.
     * @return any correct answer
     */
    IAnswerControl getAnyCorrectAnswer();

    /**
     * Convenience method to get any wrong answer.
     * @return any wrong answer
     */
    IAnswerControl getAnyWrongAnswer();
}
