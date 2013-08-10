package com.manyquiz.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.manyquiz.quiz.impl.Answer;
import com.manyquiz.quiz.model.IAnswer;
import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.impl.Level;
import com.manyquiz.quiz.impl.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = QuizSQLiteOpenHelper.class
            .getSimpleName();

    private static final String DATABASE_NAME = "sqlite3.db";
    private static final int DATABASE_VERSION = 6;

    private static final String LEVELS_TABLE_NAME = "quiz_level";
    private static final String QUESTIONS_TABLE_NAME = "quiz_question";
    private static final String ANSWERS_TABLE_NAME = "quiz_answer";

    private List<String> sqlCreateStatements;

    public QuizSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        sqlCreateStatements = getSqlStatements(context, "sql_create.sql");
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

    static List<String> readSqlStatements(Context context, String assetName)
            throws IOException {
        List<String> statements = new ArrayList<String>();
        InputStream stream = context.getAssets().open(assetName);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream));
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

    /**
     * Very primitive for now: recreate all tables
     * (drop all existing tables and then call onCreate)
     *
     * @param db SQLiteDatabase object
     * @param oldVersion version to migrate FROM
     * @param newVersion version to migrate TO
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        List<String> tables = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT tbl_name FROM sqlite_master WHERE type = 'table';", null);
        while (cursor.moveToNext()) {
            String tableName = cursor.getString(0);
            if (!tableName.equals("android_metadata") &&
                    !tableName.equals("sqlite_sequence"))
                tables.add(tableName);
        }
        cursor.close();

        for (String tableName : tables) {
            db.execSQL("DROP TABLE IF EXISTS " + tableName);
        }
        onCreate(db);
    }

    public Cursor getLevelListCursor() {
        Log.d(TAG, "get all levels (that have any questions)");
        Cursor cursor = getReadableDatabase().rawQuery(
                String.format(
                        "SELECT DISTINCT l.%s %s, l.name name, l.level level FROM %s l JOIN %s q ON l.%s = q.level_id ORDER BY l.level",
                        BaseColumns._ID, BaseColumns._ID, LEVELS_TABLE_NAME, QUESTIONS_TABLE_NAME, BaseColumns._ID
                ),
                null
        );
        return cursor;
    }

    public Cursor getQuestionListCursor(int level) {
        Log.d(TAG, "get all questions at level = " + level);
        Cursor cursor;
        if (level == Level.ANY) {
            cursor = getReadableDatabase().query(
                    QUESTIONS_TABLE_NAME,
                    new String[]{BaseColumns._ID, "text", "category", "hint",
                            "explanation",}, "is_active = 1", null, null, null,
                    null);
        } else {
            cursor = getReadableDatabase().rawQuery(
                    String.format(
                            "SELECT q.%s %s, q.text text, category, hint, explanation FROM %s q JOIN %s l ON q.level_id = l.%s WHERE l.level = ?",
                            BaseColumns._ID, BaseColumns._ID, QUESTIONS_TABLE_NAME, LEVELS_TABLE_NAME, BaseColumns._ID
                    ),
                    new String[]{Integer.toString(level)}
            );
        }
        return cursor;
    }

    public Cursor getAnswerListCursor(int level) {
        Log.d(TAG, "get all answers");
        Cursor cursor;
        if (level == Level.ANY) {
            cursor = getReadableDatabase().query(
                    ANSWERS_TABLE_NAME,
                    new String[]{BaseColumns._ID, "question_id", "text",
                            "is_correct",}, "is_active = 1", null, null, null,
                    null);
        } else {
            cursor = getReadableDatabase().rawQuery(
                    String.format(
                            "SELECT a.%s %s, question_id, a.text text, is_correct " +
                                    "FROM %s a JOIN %s q ON a.question_id = q.%s " +
                                    "JOIN %s l ON q.level_id = l.%s WHERE l.level = ? AND q.is_active = 1",
                            BaseColumns._ID, BaseColumns._ID,
                            ANSWERS_TABLE_NAME, QUESTIONS_TABLE_NAME, BaseColumns._ID,
                            LEVELS_TABLE_NAME, BaseColumns._ID
                    ),
                    new String[]{Integer.toString(level)}
            );
        }
        return cursor;
    }

    static class AnswerRecord {
        String text;
        boolean correct;
    }

    static class QuestionRecord {
        String id;
        String text;
        String explanation;
        List<IAnswer> answers = new ArrayList<IAnswer>();
    }

    public List<IQuestion> getQuestions(int level) {
        List<IQuestion> questions = new ArrayList<IQuestion>();

        Map<String, QuestionRecord> questionRecordMap = new HashMap<String, QuestionRecord>();

        {
            Cursor cursor = getQuestionListCursor(level);
            final int idIndex = cursor.getColumnIndex(BaseColumns._ID);
            final int textIndex = cursor.getColumnIndex("text");
            final int explanationIndex = cursor.getColumnIndex("explanation");
            while (cursor.moveToNext()) {
                QuestionRecord record = new QuestionRecord();
                record.id = cursor.getString(idIndex);
                record.text = cursor.getString(textIndex);
                record.explanation = cursor.getString(explanationIndex);
                questionRecordMap.put(record.id, record);
            }
            cursor.close();
        }

        {
            Cursor cursor = getAnswerListCursor(level);
            final int questionIdIndex = cursor.getColumnIndex("question_id");
            final int textIndex = cursor.getColumnIndex("text");
            final int isCorrectIndex = cursor.getColumnIndex("is_correct");
            while (cursor.moveToNext()) {
                AnswerRecord record = new AnswerRecord();
                String questionId = cursor.getString(questionIdIndex);
                record.text = cursor.getString(textIndex);
                record.correct = cursor.getInt(isCorrectIndex) > 0;
                QuestionRecord question = questionRecordMap.get(questionId);
                question.answers.add(new Answer(record.text, record.correct));
            }
            cursor.close();
        }

        for (QuestionRecord data : questionRecordMap.values()) {
            IQuestion question = new Question(data.text, data.answers, data.explanation);
            questions.add(question);
        }

        return questions;
    }

    public List<IQuestion> getQuestions() {
        return getQuestions(Level.ANY);
    }

    public List<Level> getLevels() {
        List<Level> levels = new ArrayList<Level>();

        Cursor cursor = getLevelListCursor();
        final int idIndex = cursor.getColumnIndex(BaseColumns._ID);
        final int nameIndex = cursor.getColumnIndex("name");
        final int levelIndex = cursor.getColumnIndex("level");
        while (cursor.moveToNext()) {
            LevelData data = new LevelData();
            String id = cursor.getString(idIndex);
            data.setId(id);
            data.setName(cursor.getString(nameIndex));
            data.setLevel(cursor.getInt(levelIndex));
            levels.add(new Level(data.id, data.name, data.level));
        }
        cursor.close();

        return levels;
    }
}
