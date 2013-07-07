package com.manyquiz;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class InfoActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_activity);

		findViewById(R.id.btnBlouCalorieCounter).setOnClickListener(
				new LinkButtonOnClickListener(R.string.lnk_blou_calorie_counter));
		findViewById(R.id.btnWineNotes).setOnClickListener(
				new LinkButtonOnClickListener(R.string.lnk_wine_notes));
		findViewById(R.id.btnRecipeNotes).setOnClickListener(
				new LinkButtonOnClickListener(R.string.lnk_recipe_notes));
		findViewById(R.id.btnMikesBlog).setOnClickListener(
				new LinkButtonOnClickListener(R.string.lnk_mikes_blog));
	}

	class LinkButtonOnClickListener implements OnClickListener {
		private final int urlStringId;

		public LinkButtonOnClickListener(int urlResourceId) {
			this.urlStringId = urlResourceId;
		}

		@Override
		public void onClick(View arg0) {
			String url = getResources().getString(urlStringId);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url));
			startActivity(intent);
		}
	}

}
