package com.manyquiz;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	private static final String TAG = SettingsActivity.class.getSimpleName();

	private String maxQuestionsNormalKey;
	private String maxQuestionsSuddenDeathKey;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
		// memo: this is how to reset to defaults
//		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
//		Editor editor = settings.edit();
//		editor.clear();
//		editor.commit();

		maxQuestionsNormalKey = getString(R.string.key_max_questions_normal);
		maxQuestionsSuddenDeathKey = getString(R.string.key_max_questions_suddendeath);
		
		updatePreferenceGroup(getPreferenceScreen());
	}

	private void updatePreferenceGroup(PreferenceGroup preferenceGroup) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		for (int i = 0; i < preferenceGroup.getPreferenceCount(); ++i) {
			Preference preference = preferenceGroup.getPreference(i);
			if (preference instanceof PreferenceGroup) {
				updatePreferenceGroup((PreferenceGroup) preference);
			} else {
				updatePreferenceEditor(sharedPreferences, preference);
			}
		}
	}

	private void updatePreferenceEditor(SharedPreferences sharedPreferences,
			String key) {
		Preference preference = findPreference(key);
		updatePreferenceEditor(sharedPreferences, preference);
	}

	private void updatePreferenceEditor(SharedPreferences sharedPreferences,
			Preference preference) {
		String key = preference.getKey();
		if (key.equals(maxQuestionsNormalKey)) {
			String value = sharedPreferences.getString(key, null);
			String format = getString(R.string.summary_max_questions_normal_format);
			preference.setSummary(String.format(format, value));
		} else if (key.equals(maxQuestionsSuddenDeathKey)) {
			String value = sharedPreferences.getString(key, null);
			String format = getString(R.string.summary_max_questions_suddendeath_format);
			preference.setSummary(String.format(format, value));
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Log.d(TAG, "preference changed: " + key);
		updatePreferenceEditor(sharedPreferences, key);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}
}
