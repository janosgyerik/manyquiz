package com.manyquiz.quiz.model;

import java.util.Collection;

public interface ICategoryFilterControl {

    void saveFilters();

    String[] getItems();

    boolean[] getCheckedItems();

    void setFilter(int which, boolean enabled);

    Collection<String> getSelectedItems();
}
