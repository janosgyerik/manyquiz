package com.manyquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public abstract class QuizBaseActivity extends Activity {

	protected void checkAndSetupForLiteVersion() {
		if (((QuizApplication)this.getApplication()).isLiteVersion()) {
			findViewById(R.id.lite_watermark).setVisibility(View.VISIBLE);
			setupAds();
		}
	}

	protected void setupAds() {
		AdView googleAdMobView = (AdView)this.findViewById(R.id.google_admob);
		googleAdMobView.setBackgroundColor(Color.BLACK);
		googleAdMobView.loadAd(new AdRequest());
		
		findViewById(R.id.ads).setVisibility(View.VISIBLE);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.menu_about) {
			Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
			startActivity(intent);
			return true;
		}
		if (itemId == R.id.menu_settings) {
			Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
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