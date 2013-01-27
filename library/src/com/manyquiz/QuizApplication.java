package com.manyquiz;

import android.app.Application;

public class QuizApplication extends Application {
	public boolean isLiteVersion() {
		return getPackageName().toLowerCase().endsWith(".lite"); 
	}
}
