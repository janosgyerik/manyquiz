package com.manyquiz.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.manyquiz.R;
import com.manyquiz.application.QuizApplication;
import com.manyquiz.tools.EmailTools;

public abstract class QuizActivityBase extends Activity {

    protected static final int RETURN_FROM_SETTINGS = 1;

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

}