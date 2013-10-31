package com.manyquiz.quiz.impl;

public class Category {
    public final String name;
    private final int count;

    public Category(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public Category(String name) {
        this(name, 0);
    }
}
