package com.manyquiz;

import java.util.Arrays;
import java.util.List;

public class ComputerQuiz implements IQuiz {

	public static final String CATEGORY_PROGRAMMING = "programming";
	public static final String CATEGORY_SOFTWARE = "software";
	public static final String CATEGORY_HARDWARE = "hardware";

	@Override
	public List<IQuestion> pickRandomQuestions(int length) {
		return Arrays.asList(
				(IQuestion) new Question(CATEGORY_PROGRAMMING,
						"One kilobyte is equal to...?",
						"Although kilobyte is formally equal to 1000 bytes, a second definition and usage has historically been in practice in the field of computer science and IT, as 1024 bytes.",
						Arrays.asList(
								"1000 bytes", "100 bytes", "1024 bytes",
								"1023 bytes"), 2),
				new Question(CATEGORY_SOFTWARE,
						"Which of the following is not an Operating System?",
						"Microsoft Office XP is a suite of desktop applications, with text editor, spreadsheet editor, etc, NOT an operating system.",
						Arrays.asList("HP-UX", "Windows 98",
								"Microsoft Office XP", "RedHat Linux"), 2));
	}

}
