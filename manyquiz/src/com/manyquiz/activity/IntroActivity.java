package com.manyquiz.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.manyquiz.R;
import com.manyquiz.db.QuizSQLiteOpenHelper;
import com.manyquiz.fragments.SelectCategoriesDialogFragment;
import com.manyquiz.fragments.SingleChoiceDialogFragment;
import com.manyquiz.quiz.impl.Category;
import com.manyquiz.quiz.impl.GameMode;
import com.manyquiz.quiz.impl.Level;
import com.manyquiz.util.IPreferenceEditor;
import com.manyquiz.util.ISingleChoiceControl;
import com.manyquiz.util.SimpleSharedPreferenceEditor;

import java.util.List;

public class IntroActivity extends QuizActivityBase implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = IntroActivity.class.getSimpleName();

    private ISingleChoiceControl levelChoiceControl;
    private Button levelSelectorButton;

    private ISingleChoiceControl modeChoiceControl;
    private Button modeSelectorButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "++onCreate");
        setContentView(R.layout.intro_activity);

        checkAndSetupForLiteVersion();

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        setHelper(new QuizSQLiteOpenHelper(this));

        levelChoiceControl = createLevelChoiceControl();

        if (levelChoiceControl.getNames().length > 1) {
            Level level = (Level) levelChoiceControl.getSelectedItem();
            levelSelectorButton = (Button) findViewById(R.id.btn_select_level);
            levelSelectorButton.setText(level.name);
            levelSelectorButton.setOnClickListener(new SelectLevelClickListener());
        } else {
            findViewById(R.id.wrapper_select_level).setVisibility(View.GONE);
        }

        modeChoiceControl = createModeChoiceControl();
        GameMode mode = (GameMode) modeChoiceControl.getSelectedItem();
        modeSelectorButton = (Button) findViewById(R.id.btn_select_mode);
        modeSelectorButton.setText(mode.getChoiceName());
        modeSelectorButton.setOnClickListener(new SelectModeClickListener());

        findViewById(R.id.btn_select_categories).setOnClickListener(new SelectCategoriesClickListener());

        findViewById(R.id.btn_start_quiz).setOnClickListener(new StartQuizClickListener());
    }

    class StartQuizClickListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(IntroActivity.this, QuizActivity.class);
            startActivity(intent);
        }
    }

    class SelectLevelClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            DialogFragment newFragment = new SingleChoiceDialogFragment(getString(R.string.title_select_level), levelChoiceControl);
            newFragment.show(getSupportFragmentManager(), "select-level");
        }
    }

    class SelectModeClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            DialogFragment newFragment = new SingleChoiceDialogFragment(getString(R.string.title_select_mode), modeChoiceControl);
            newFragment.show(getSupportFragmentManager(), "select-mode");
        }
    }

    class SelectCategoriesClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            SharedPreferences sharedPreferences = getSharedPreferences();
            String key = getString(R.string.pref_selected_categories);

            List<Category> categories = getHelper().getCategories();

            IPreferenceEditor preferenceEditor = new SimpleSharedPreferenceEditor(sharedPreferences, key, "");
            DialogFragment newFragment = new SelectCategoriesDialogFragment(preferenceEditor, categories);
            newFragment.show(getSupportFragmentManager(), "select-categories");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.intro, menu);
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String prefName) {
        if (prefName.equals(getString(R.string.pref_level))) {
            levelSelectorButton.setText(levelChoiceControl.getSelectedItem().getChoiceName());
        } else if (prefName.equals(getString(R.string.pref_mode))) {
            modeSelectorButton.setText(modeChoiceControl.getSelectedItem().getChoiceName());
        }
    }

    @Override
    protected void onResume() {
        getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}
