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
import com.manyquiz.quiz.model.ICategoryFilterControl;
import com.manyquiz.util.EmailTools;
import com.manyquiz.util.IPreferenceEditor;
import com.manyquiz.util.SimpleSharedPreferenceEditor;

import java.util.List;

public abstract class QuizActivityBase extends FragmentActivity {

    protected static final int RETURN_FROM_SETTINGS = 1;

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
            startActivityForResult(intent, RETURN_FROM_SETTINGS);
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

    protected ICategoryFilterControl getCategoryFilterControl() {
        List<Category> categories = helper.getCategories();
        String key = getString(R.string.key_selected_categories);
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        IPreferenceEditor preferenceEditor = new SimpleSharedPreferenceEditor(sharedPreferences, key, "");
        return new CategoryFilterControl(preferenceEditor, categories);
    }

    protected SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }
}