package com.manyquiz;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

abstract class QuizBaseActivity extends Activity{

	private static final String ADMOB_UNIT_ID = "a14b9eac992329e";

	private AdView googleAdMobAds;

	protected void checkAndSetupForLiteVersion() {
		if (((QuizApplication)this.getApplication()).isLiteVersion()) {
			findViewById(R.id.lite_watermark).setVisibility(View.VISIBLE);
			setupAds();
			showAds();
		}
	}

	protected void setupAds() {
		googleAdMobAds = new AdView(this, AdSize.BANNER, ADMOB_UNIT_ID);
		googleAdMobAds.setBackgroundColor(Color.BLACK);

		LinearLayout layout = (LinearLayout)findViewById(R.id.googleAdMobAds);
		layout.addView(googleAdMobAds);

		googleAdMobAds.loadAd(new AdRequest());
		googleAdMobAds.setVisibility(View.GONE);
	}

	protected void showAds() {
		googleAdMobAds.setVisibility(View.VISIBLE);
	}
}