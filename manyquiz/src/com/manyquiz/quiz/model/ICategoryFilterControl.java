package com.manyquiz.quiz.model;

public interface ICategoryFilterControl {

    void saveFilters();

    String[] getItems();

    boolean[] getCheckedItems();

    void setFilter(int which, boolean enabled);

}
