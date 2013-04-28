package com.manyquiz;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

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
}