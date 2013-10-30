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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.manyquiz.R;
import com.manyquiz.db.QuizSQLiteOpenHelper;
import com.manyquiz.fragments.SelectCategoriesDialogFragment;
import com.manyquiz.quiz.impl.Category;
import com.manyquiz.quiz.impl.Level;
import com.manyquiz.tools.IPreferenceEditor;
import com.manyquiz.tools.SimpleSharedPreferenceEditor;

import java.util.List;

public class IntroActivity extends QuizActivityBase {

    private static final String TAG = IntroActivity.class.getSimpleName();

    private RadioGroup levelChoices;
    private RadioGroup modeChoices;
    private RadioButton suddenDeathMode;

    private QuizSQLiteOpenHelper helper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "++onCreate");
        setContentView(R.layout.intro_activity);

        checkAndSetupForLiteVersion();

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        helper = new QuizSQLiteOpenHelper(this);

        levelChoices = (RadioGroup) findViewById(R.id.level_choices);
        RadioButton first = null;
        List<Level> levels = helper.getLevels();
        for (Level level : levels) {
            RadioButton levelOption = new RadioButton(this);
            levelOption.setText(level.getName());
            levelOption.setTag(level);
            if (first == null) {
                first = levelOption;
            }
            levelChoices.addView(levelOption);
        }
        if (first != null) {
            // this may seem strange,
            // but we have to check the choice after adding all choices
            levelChoices.check(first.getId());
        }
        if (levels.size() < 2) {
            levelChoices.setVisibility(View.GONE);
            findViewById(R.id.msg_select_level).setVisibility(View.GONE);
        }

        modeChoices = (RadioGroup) findViewById(R.id.mode_choices);

        suddenDeathMode = (RadioButton) findViewById(R.id.mode_sudden_death);
        updateSuddenDeathModeLabel();

        findViewById(R.id.btn_select_categories).setOnClickListener(new SelectCategoriesClickListener());

        findViewById(R.id.btn_start_quiz).setOnClickListener(new StartQuizClickListener());
    }

    class StartQuizClickListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            View selectedOption = findViewById(levelChoices.getCheckedRadioButtonId());
            Level level = (Level) selectedOption.getTag();
            Log.i(TAG, "selected level = " + level);

            View selectedMode = findViewById(modeChoices.getCheckedRadioButtonId());
            String mode = (String) selectedMode.getTag();
            Log.i(TAG, "selected mode = " + mode);

            Bundle bundle = new Bundle();
            bundle.putSerializable(QuizActivity.PARAM_LEVEL, level);
            bundle.putString(QuizActivity.PARAM_MODE, mode);

            Intent intent = new Intent(IntroActivity.this, QuizActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    class SelectCategoriesClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(IntroActivity.this);

            String key = getString(R.string.key_selected_categories);

            List<Category> categories = helper.getCategories();

            IPreferenceEditor preferenceEditor = new SimpleSharedPreferenceEditor(sharedPreferences, key);
            DialogFragment newFragment = new SelectCategoriesDialogFragment(preferenceEditor, categories);
            newFragment.show(getSupportFragmentManager(), "select-categories");
        }
    }

    private void updateSuddenDeathModeLabel() {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        String key = getString(R.string.key_max_questions_suddendeath);
        int maxQuestionsSuddenDeath = Integer.parseInt(settings.getString(key, null));

        suddenDeathMode.setText(String.format(getString(R.string.suddendeath_mode_format),
                maxQuestionsSuddenDeath
        ));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RETURN_FROM_SETTINGS:
                updateSuddenDeathModeLabel();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        updateSuddenDeathModeLabel();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.intro, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "++onDestroy");
        super.onDestroy();
        helper.close();
    }
}
