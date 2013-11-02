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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.manyquiz.R;
import com.manyquiz.db.QuizSQLiteOpenHelper;
import com.manyquiz.fragments.SelectCategoriesDialogFragment;
import com.manyquiz.fragments.SingleChoiceDialogFragment;
import com.manyquiz.quiz.impl.Category;
import com.manyquiz.quiz.impl.Level;
import com.manyquiz.util.SingleChoiceControl;
import com.manyquiz.util.ISingleChoiceControl;
import com.manyquiz.util.IPreferenceEditor;
import com.manyquiz.util.SimpleSharedPreferenceEditor;

import java.util.List;

public class IntroActivity extends QuizActivityBase {

    private static final String TAG = IntroActivity.class.getSimpleName();

    private RadioGroup modeChoices;
    private RadioButton suddenDeathMode;

    ISingleChoiceControl levelChoiceControl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "++onCreate");
        setContentView(R.layout.intro_activity);

        checkAndSetupForLiteVersion();

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        setHelper(new QuizSQLiteOpenHelper(this));

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(IntroActivity.this);

        String levelKey = getString(R.string.key_level);
        IPreferenceEditor levelPreferenceEditor = new SimpleSharedPreferenceEditor(sharedPreferences, levelKey, "0");
        List<Level> levels = getHelper().getLevels();
        levelChoiceControl = new SingleChoiceControl(levelPreferenceEditor, levels);

        if (levels.size() > 1) {
            Button levelSelectorButton = (Button) findViewById(R.id.btn_select_level);
            if (levelSelectorButton != null) {
                Level level = (Level) levelChoiceControl.getSelectedItem();
                levelSelectorButton.setText(level.getName());
                levelSelectorButton.setOnClickListener(new SelectLevelClickListener());
            }
        } else {
            findViewById(R.id.wrapper_select_level).setVisibility(View.GONE);
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
            Level level = (Level) levelChoiceControl.getSelectedItem();
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

    class SelectLevelClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            DialogFragment newFragment = new SingleChoiceDialogFragment(getString(R.string.title_select_level), levelChoiceControl);
            newFragment.show(getSupportFragmentManager(), "select-level");
        }
    }

    class SelectCategoriesClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(IntroActivity.this);

            String key = getString(R.string.key_selected_categories);

            List<Category> categories = getHelper().getCategories();

            IPreferenceEditor preferenceEditor = new SimpleSharedPreferenceEditor(sharedPreferences, key, "");
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
}
