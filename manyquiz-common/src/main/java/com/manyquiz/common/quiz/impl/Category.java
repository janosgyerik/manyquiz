package com.manyquiz.common.quiz.impl;

import com.manyquiz.common.util.IChoice;

public class Category implements IChoice {

    public final String name;

    public Category(String name) {
        this.name = name;
    }

    @Override
    public String getChoiceValue() {
        return name;
    }

    @Override
    public String getChoiceLabel() {
        return name;
    }
}
