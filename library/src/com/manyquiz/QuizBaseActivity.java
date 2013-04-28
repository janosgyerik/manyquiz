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

	private AdView googleAdMobView;

	protected void checkAndSetupForLiteVersion() {
		if (((QuizApplication)this.getApplication()).isLiteVersion()) {
			findViewById(R.id.lite_watermark).setVisibility(View.VISIBLE);
			setupAds();
		}
	}

	protected void setupAds() {
		googleAdMobView = new AdView(this, AdSize.BANNER, ADMOB_UNIT_ID);
		googleAdMobView.setBackgroundColor(Color.BLACK);
		googleAdMobView.loadAd(new AdRequest());
		
		LinearLayout adsLayout = (LinearLayout)findViewById(R.id.ads);
		adsLayout.addView(googleAdMobView);
		adsLayout.setVisibility(View.VISIBLE);
	}
}