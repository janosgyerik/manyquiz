package com.manyquiz.quiz.model;

import java.io.Serializable;
import java.util.List;

/**
 * A quiz control keeps track of the state of a quiz.
 */
public interface IQuizControl extends Serializable {

    /**
     * Get the list of question control objects.
     * A question control keeps track of the state of a question.
     *
     * @return list of question control objects
     */
    List<IQuestionControl> getQuestionControls();

    /**
     * Just a convenience function to get the number of questions.
     * Normally this should be the length of the list of question controls.
     *
     * @return number of questions
     */
    int getQuestionsNum();

    /**
     * Get the current question control object that the user is looking at.
     *
     * @return current question control
     */
    IQuestionControl getCurrentQuestion();

    /**
     * Get the index of the current question, for showing progress stats.
     *
     * @return index of the current question: 0 .. N-1
     */
    int getCurrentQuestionIndex();

    /**
     * Get the total score earned from all closed questions.
     * Pending questions don't contribute to this score.
     *
     * @return score
     */
    int getScore();

    /**
     * Check if the quiz is over or not.
     * When the quiz is over, the user interface might provide
     * a new button to view results, email results, or others.
     *
     * @return is the quiz over or not
     */
    boolean isGameOver();

    /**
     * Check if the quiz is ready to end or not.
     * A quiz is ready to end when all questions are answered.
     *
     * @return is the quiz ready to end or not
     */
    boolean readyToEnd();

    /**
     * End the quiz.
     * Some quiz might end automatically when all questions
     * are closed, others may require explicit user action.
     */
    void end();

    /**
     * Check if the quiz supports navigating backward
     * to previous questions.
     * This may affect the layout of navigation buttons in the UI.
     *
     * @return does the quiz support navigating backward or not
     */
    boolean canNavigateBackward();

    /**
     * Check if there is a "next" question.
     * This may affect the layout of navigation buttons in the UI.
     *
     * @return is there a next question or not
     */
    boolean hasNextQuestion();

    /**
     * Check if there is a "previous" question.
     * This may affect the layout of navigation buttons in the UI.
     *
     * @return is there a previous question or not
     */
    boolean hasPrevQuestion();

    /**
     * Navigate to the next question, which will become the new current question.
     */
    void gotoNextQuestion();

    /**
     * Navigate to the next question, which will become the new current question.
     */
    void gotoPrevQuestion();
}
