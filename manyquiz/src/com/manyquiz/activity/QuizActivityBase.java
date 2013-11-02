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
import com.manyquiz.quiz.impl.CategoryFilterControl;
import com.manyquiz.quiz.impl.GameMode;
import com.manyquiz.quiz.impl.Level;
import com.manyquiz.quiz.model.ICategoryFilterControl;
import com.manyquiz.util.EmailTools;
import com.manyquiz.util.IPreferenceEditor;
import com.manyquiz.util.ISingleChoiceControl;
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

    protected void checkAndSetupForLiteVersion() {
        if (((QuizApplication) this.getApplication()).isLiteVersion()) {
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
        super.onDestroy();
        if (helper != null) {
            helper.close();
        }
    }

    protected SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    protected ISingleChoiceControl createLevelChoiceControl() {
        String levelKey = getString(R.string.pref_level);
        IPreferenceEditor levelPreferenceEditor = new SimpleSharedPreferenceEditor(getSharedPreferences(), levelKey, "0");
        List<Level> levels = getHelper().getLevels();
        return new SingleChoiceControl(levelPreferenceEditor, levels);
    }

    protected ISingleChoiceControl createModeChoiceControl() {
        String modeKey = getString(R.string.pref_mode);
        IPreferenceEditor modePreferenceEditor = new SimpleSharedPreferenceEditor(getSharedPreferences(), modeKey, getString(R.string.const_score_as_you_go));
        List<GameMode> modes = new ArrayList<GameMode>();
        modes.add(new GameMode(getString(R.string.const_score_as_you_go), getString(R.string.mode_score_as_you_go)));
        modes.add(new GameMode(getString(R.string.const_score_in_the_end), getString(R.string.mode_score_in_the_end)));
        modes.add(new GameMode(getString(R.string.const_suddendeath), getString(R.string.mode_suddendeath)));
        return new SingleChoiceControl(modePreferenceEditor, modes);
    }

    protected ICategoryFilterControl getCategoryFilterControl() {
        List<Category> categories = helper.getCategories();
        String key = getString(R.string.pref_selected_categories);
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        IPreferenceEditor preferenceEditor = new SimpleSharedPreferenceEditor(sharedPreferences, key, "");
        return new CategoryFilterControl(preferenceEditor, categories);
    }
}