package com.manyquiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.util.SparseArray;

public class QuizSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String TAG = QuizSQLiteOpenHelper.class.getSimpleName();

	private static final String DATABASE_NAME = "sqlite3.db";
	private static final int DATABASE_VERSION = 1;

	private static final String LEVELS_TABLE_NAME = "main_level";
	private static final String QUESTIONS_TABLE_NAME = "main_question";
	private static final String ANSWERS_TABLE_NAME = "main_answer";

	private List<String> sqlCreateStatements;
	private SparseArray<List<String>> sqlUpgradeStatements;

	QuizSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		//context.deleteDatabase(DATABASE_NAME);

		sqlCreateStatements = getSqlStatements(context, "sql_create.sql");
		sqlUpgradeStatements = new SparseArray<List<String>>();
		//		sqlUpgradeStatements.put(2, getSqlStatements(context, "sql_upgrade2.sql"));
	}

	private List<String> getSqlStatements(Context context, String assetName) {
		List<String> statements;
		try {
			statements = readSqlStatements(context, assetName);
		} catch (IOException e) {
			statements = Collections.emptyList();
			e.printStackTrace();
		}
		return statements;
	}

	static List<String> readSqlStatements(Context context, String assetName) throws IOException {
		List<String> statements = new ArrayList<String>();
		InputStream stream = context.getAssets().open(assetName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line;
		StringBuilder builder = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			builder.append(line);
			if (line.trim().endsWith(";")) {
				statements.add(builder.toString());
				builder = new StringBuilder();
			}
		}
		return statements;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (String sql : sqlCreateStatements) {
			db.execSQL(sql);
			// TODO check success
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int i = oldVersion; i < newVersion; ++i) {
			upgradeToVersion(db, i + 1);
		}
	}

	private void upgradeToVersion(SQLiteDatabase db, int version) {
		Log.d(TAG, "upgrade to version " + version);
		for (String sql : sqlUpgradeStatements.get(version)) {
			db.execSQL(sql);
		}
	}

	public Cursor getLevelListCursor() {
		Log.d(TAG, "get all levels");
		Cursor cursor = getReadableDatabase().query(
				LEVELS_TABLE_NAME, 
				new String[]{ BaseColumns._ID, "name", "level", },
				"is_active = 1",
				null, null, null, null);
		return cursor;
	}
	
	public Cursor getQuestionListCursor() {
		Log.d(TAG, "get all questions");
		Cursor cursor = getReadableDatabase().query(
				QUESTIONS_TABLE_NAME, 
				new String[]{ BaseColumns._ID, "text",
						"category", "level", "hint", "explanation", }, 
				"is_active = 1",
				null, null, null, null);
		return cursor;
	}
	
	public List<IQuestion> getQuestions() {
		List<IQuestion> questions = new ArrayList<IQuestion>();
		// TODO
		return questions;
	}

	public Cursor getAnswerListCursor() {
		Log.d(TAG, "get all answers");
		Cursor cursor = getReadableDatabase().query(
				ANSWERS_TABLE_NAME, 
				new String[]{ BaseColumns._ID, "text", },
				"is_active = 1",
				null, null, null, null);
		return cursor;
	}
}
