package com.manyquiz;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class AdMobActivity extends Activity{
	private static final String ADMOB_UNIT_ID = "a14b9eac992329e";
	
	protected void setupAds() {
		QuizActivity.googleAdMobAds = new AdView(this, AdSize.BANNER, ADMOB_UNIT_ID);
	    QuizActivity.googleAdMobAds.setBackgroundColor(Color.BLACK);

	    LinearLayout layout = (LinearLayout)findViewById(R.id.googleAdMobAds);
	    layout.addView(QuizActivity.googleAdMobAds);
	    
	    QuizActivity.googleAdMobAds.loadAd(new AdRequest());
	    QuizActivity.googleAdMobAds.setVisibility(View.GONE);
	}
	protected void showAds() {
		QuizActivity.googleAdMobAds.setVisibility(View.VISIBLE);
	}
}