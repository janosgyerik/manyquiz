package com.manyquiz.android.util;

import android.content.SharedPreferences;

import com.manyquiz.common.util.IPreferenceEditor;

public class SimpleSharedPreferenceEditor implements IPreferenceEditor {

    private final SharedPreferences sharedPreferences;
    private final String key;
    private final String defaultValue;

    public SimpleSharedPreferenceEditor(SharedPreferences sharedPreferences, String key, String defaultValue) {
        this.sharedPreferences = sharedPreferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getPreferenceValue() {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    public void savePreferenceValue(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
