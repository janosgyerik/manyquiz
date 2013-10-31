package com.manyquiz.tools;

import android.content.SharedPreferences;

public class SimpleSharedPreferenceEditor implements IPreferenceEditor {

    private final SharedPreferences sharedPreferences;
    private final String key;

    public SimpleSharedPreferenceEditor(SharedPreferences sharedPreferences, String key) {
        this.sharedPreferences = sharedPreferences;
        this.key = key;
    }

    @Override
    public String getPreferenceValue() {
        return sharedPreferences.getString(key, "");
    }

    @Override
    public void savePreferenceValue(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
