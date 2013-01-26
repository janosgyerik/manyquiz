package com.manyquiz;

import java.util.List;

public interface IQuiz {

	List<IQuestion> pickRandomQuestions(int length);
}
