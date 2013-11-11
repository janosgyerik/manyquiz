package com.manyquiz.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.manyquiz.R;
import com.manyquiz.db.QuizSQLiteOpenHelper;
import com.manyquiz.fragments.MultiChoiceDialogFragment;
import com.manyquiz.fragments.SingleChoiceDialogFragment;
import com.manyquiz.quiz.impl.GameMode;
import com.manyquiz.quiz.impl.Level;
import com.manyquiz.quiz.impl.QuestionsNumChoice;
import com.manyquiz.util.IMultiChoiceControl;
import com.manyquiz.util.ISingleChoiceControl;

public class IntroActivity extends QuizActivityBase implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = IntroActivity.class.getSimpleName();

    private ISingleChoiceControl levelChoiceControl;
    private Button levelSelectorButton;

    private ISingleChoiceControl modeChoiceControl;
    private Button modeSelectorButton;

    private ISingleChoiceControl questionsNumChoiceControl;
    private Button questionsNumSelectorButton;

    private IMultiChoiceControl categoryFilterControl;

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
            levelSelectorButton.setText(level.label);
            levelSelectorButton.setOnClickListener(new SelectLevelClickListener());
        } else {
            findViewById(R.id.wrapper_select_level).setVisibility(View.GONE);
        }

        modeChoiceControl = createModeChoiceControl();
        GameMode mode = (GameMode) modeChoiceControl.getSelectedItem();
        modeSelectorButton = (Button) findViewById(R.id.btn_select_mode);
        modeSelectorButton.setText(mode.label);
        modeSelectorButton.setOnClickListener(new SelectModeClickListener());

        questionsNumChoiceControl = createQuestionsNumChoiceControl();
        QuestionsNumChoice questionsNumChoice = (QuestionsNumChoice) questionsNumChoiceControl.getSelectedItem();
        questionsNumSelectorButton = (Button) findViewById(R.id.btn_select_questions_num);
        questionsNumSelectorButton.setText(questionsNumChoice.label);
        questionsNumSelectorButton.setOnClickListener(new SelectQuestionsNumClickListener());

        categoryFilterControl = createCategoryFilterControl();

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
            DialogFragment newFragment = SingleChoiceDialogFragment.newInstance(getString(R.string.title_select_level), levelChoiceControl);
            newFragment.show(getSupportFragmentManager(), "select-level");
        }
    }

    class SelectModeClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            DialogFragment newFragment = SingleChoiceDialogFragment.newInstance(getString(R.string.title_select_mode), modeChoiceControl);
            newFragment.show(getSupportFragmentManager(), "select-mode");
        }
    }

    class SelectQuestionsNumClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            DialogFragment newFragment = SingleChoiceDialogFragment.newInstance(getString(R.string.title_select_questions_num), questionsNumChoiceControl);
            newFragment.show(getSupportFragmentManager(), "questions-num");
        }
    }

    class SelectCategoriesClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            DialogFragment newFragment = new MultiChoiceDialogFragment(getString(R.string.title_select_categories), categoryFilterControl);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!super.onOptionsItemSelected(item)) {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_reset) {
                SharedPreferences.Editor editor = getSharedPreferences().edit();
                editor.clear();
                editor.commit();

                levelChoiceControl.reloadSelection();
                updateLevelSelectorButton();

                modeChoiceControl.reloadSelection();
                updateModeSelectorButton();

                questionsNumChoiceControl.reloadSelection();
                updateQuestionsNumSelectorButton();

                categoryFilterControl.reloadSelection();

                return true;
            }
        }
        return false;
    }

    private void updateLevelSelectorButton() {
        levelSelectorButton.setText(levelChoiceControl.getSelectedItem().getChoiceLabel());
    }

    private void updateModeSelectorButton() {
        modeSelectorButton.setText(modeChoiceControl.getSelectedItem().getChoiceLabel());
    }

    private void updateQuestionsNumSelectorButton() {
        questionsNumSelectorButton.setText(questionsNumChoiceControl.getSelectedItem().getChoiceLabel());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String prefName) {
        Log.d(TAG, "onSharedPreferenceChanged");
        if (prefName.equals(getString(R.string.pref_level))) {
            updateLevelSelectorButton();
        } else if (prefName.equals(getString(R.string.pref_mode))) {
            updateModeSelectorButton();
        } else if (prefName.equals(getString(R.string.pref_questions_num))) {
            updateQuestionsNumSelectorButton();
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
