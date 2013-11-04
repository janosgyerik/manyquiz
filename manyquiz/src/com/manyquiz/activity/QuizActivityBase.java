package com.manyquiz.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.manyquiz.R;
import com.manyquiz.application.QuizApplication;
import com.manyquiz.db.QuizSQLiteOpenHelper;
import com.manyquiz.quiz.impl.Category;
import com.manyquiz.quiz.impl.GameMode;
import com.manyquiz.quiz.impl.Level;
import com.manyquiz.quiz.impl.QuestionsNumChoice;
import com.manyquiz.util.EmailTools;
import com.manyquiz.util.IMultiChoiceControl;
import com.manyquiz.util.IPreferenceEditor;
import com.manyquiz.util.ISingleChoiceControl;
import com.manyquiz.util.MultiChoiceControl;
import com.manyquiz.util.SimpleSharedPreferenceEditor;
import com.manyquiz.util.SingleChoiceControl;

import java.util.ArrayList;
import java.util.List;

public abstract class QuizActivityBase extends FragmentActivity {

    private QuizSQLiteOpenHelper helper;

    protected QuizSQLiteOpenHelper getHelper() {
        return helper;
    }

    protected void setHelper(QuizSQLiteOpenHelper helper) {
        this.helper = helper;
    }

    protected boolean isLiteVersion() {
        return ((QuizApplication) this.getApplication()).isLiteVersion();
    }

    protected void checkAndSetupForLiteVersion() {
        if (isLiteVersion()) {
            findViewById(R.id.lite_watermark).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            //startActivity(intent);  // TODO restore after the screen is needed again
            return true;
        }
        if (itemId == R.id.menu_contact) {
            EmailTools.send(this, R.string.subject_contact);
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if (helper != null) {
            helper.close();
        }
        super.onDestroy();
    }

    protected SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    protected ISingleChoiceControl createLevelChoiceControl() {
        String levelPrefKey = getString(R.string.pref_level);
        IPreferenceEditor levelPreferenceEditor = new SimpleSharedPreferenceEditor(getSharedPreferences(), levelPrefKey, "0");
        List<Level> levels = getHelper().getLevels();
        return new SingleChoiceControl(levelPreferenceEditor, levels);
    }

    protected ISingleChoiceControl createModeChoiceControl() {
        String modePrefKey = getString(R.string.pref_mode);
        GameMode defaultGameMode = new GameMode.ScoreAsYouGo(getString(R.string.mode_score_as_you_go));
        IPreferenceEditor modePreferenceEditor =
                new SimpleSharedPreferenceEditor(getSharedPreferences(), modePrefKey, defaultGameMode.value);
        List<GameMode> modes = new ArrayList<GameMode>();
        modes.add(defaultGameMode);
        modes.add(new GameMode.ScoreInTheEnd(getString(R.string.mode_score_in_the_end)));
        modes.add(new GameMode.SuddenDeath(getString(R.string.mode_suddendeath)));
        return new SingleChoiceControl(modePreferenceEditor, modes);
    }

    protected ISingleChoiceControl createQuestionsNumChoiceControl() {
        String numPrefKey = getString(R.string.pref_questions_num);
        IPreferenceEditor questionsNumPreferenceEditor =
                new SimpleSharedPreferenceEditor(getSharedPreferences(), numPrefKey, "15");
        List<QuestionsNumChoice> questionsNumChoices = new ArrayList<QuestionsNumChoice>();
        questionsNumChoices.add(new QuestionsNumChoice(100));
        questionsNumChoices.add(new QuestionsNumChoice(50));
        questionsNumChoices.add(new QuestionsNumChoice(15));
        questionsNumChoices.add(new QuestionsNumChoice(10));
        questionsNumChoices.add(new QuestionsNumChoice(5));
        questionsNumChoices.add(new QuestionsNumChoice(3));
        return new SingleChoiceControl(questionsNumPreferenceEditor, questionsNumChoices);
    }

    protected IMultiChoiceControl createCategoryFilterControl() {
        String categoriesPrefKey = getString(R.string.pref_categories);
        IPreferenceEditor preferenceEditor =
                new SimpleSharedPreferenceEditor(getSharedPreferences(), categoriesPrefKey, "");
        List<Category> categories = helper.getCategories();
        return new MultiChoiceControl(preferenceEditor, categories, true);
    }
}