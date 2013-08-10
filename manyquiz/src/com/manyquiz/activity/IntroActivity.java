package com.manyquiz.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.manyquiz.quiz.impl.Level;
import com.manyquiz.db.QuizSQLiteOpenHelper;
import com.manyquiz.R;

public class IntroActivity extends QuizBaseActivity {

    private static final String TAG = IntroActivity.class.getSimpleName();

    private RadioGroup levelChoices;
    private CheckBox suddenDeathMode;
    private Button btnStartQuiz;

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
        for (Level level : helper.getLevels()) {
            RadioButton levelOption = new RadioButton(this);
            levelOption.setText(level.getName());
            levelOption.setTag(level);
            if (first == null) {
                first = levelOption;
            }
            levelChoices.addView(levelOption);
        }
        levelChoices.check(first.getId());

        suddenDeathMode = (CheckBox) findViewById(R.id.suddendeath_mode);
        updateSuddenDeathModeLabel();

        btnStartQuiz = (Button) findViewById(R.id.btn_startQuiz);
        btnStartQuiz.setOnClickListener(new StartQuizClickListener());
    }

    class ExitClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    class StartQuizClickListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            View selectedOption = findViewById(levelChoices.getCheckedRadioButtonId());
            Level level = (Level) selectedOption.getTag();
            Log.d(TAG, "selected level = " + level);

            Bundle bundle = new Bundle();
            bundle.putSerializable(QuizActivity.PARAM_LEVEL, level);
            bundle.putBoolean(QuizActivity.PARAM_SUDDENDEATH_MODE, suddenDeathMode.isChecked());

            Intent intent = new Intent(IntroActivity.this, QuizActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
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
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "++onDestroy");
        super.onDestroy();
        helper.close();
    }

}
