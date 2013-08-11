package com.manyquiz.quiz.model;

import java.io.Serializable;

/**
 * An answer control keeps track of the state of an answer.
 */
public interface IAnswerControl extends Serializable {

    /**
     * Get the answer object tracked by this controller.
     *
     * @return answer associated with this control
     */
    IAnswer getAnswer();

    /**
     * Select this answer.
     * This may affect both the UI and the question's state.
     */
    void select();

    /**
     * Unselect this answer.
     * This may affect both the UI and the question's state.
     */
    void deselect();

    /**
     * Check if answer is selected.
     *
     * @return is answer selected or not
     */
    boolean isSelected();
}
