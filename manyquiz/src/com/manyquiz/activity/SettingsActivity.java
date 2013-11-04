package com.manyquiz.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.manyquiz.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        setContentView(R.layout.settings_activity);

        // memo: this is how to reset to defaults
		//SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		//Editor editor = settings.edit();
		//editor.clear();
		//editor.commit();
    }
}
