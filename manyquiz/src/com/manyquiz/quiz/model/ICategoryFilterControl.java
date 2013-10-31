package com.manyquiz.quiz.model;

import java.util.Collection;

public interface ICategoryFilterControl {

    void saveFilters();

    String[] getCategoryNames();

    boolean[] getCategoryStates();

    void setCategoryState(int which, boolean enabled);

    Collection<String> getSelectedItems();
}
