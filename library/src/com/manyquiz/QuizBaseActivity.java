package com.manyquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

public abstract class QuizBaseActivity extends Activity {

    protected static final int RETURN_FROM_SETTINGS = 1;

    protected void checkAndSetupForLiteVersion() {
        if (((QuizApplication) this.getApplication()).isLiteVersion()) {
            findViewById(R.id.lite_watermark).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivityForResult(intent, RETURN_FROM_SETTINGS);
            return true;
        }
        if (itemId == R.id.menu_report_fault) {
            Intent intent = new Intent(getApplicationContext(), ReportBugActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemId == R.id.menu_suggest_question) {
            Intent intent = new Intent(getApplicationContext(), QuestionSuggestionActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemId == R.id.menu_quit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.msg_quit)
                    .setCancelable(true)
                    .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).show();
            return true;
        }
        return false;
    }

}