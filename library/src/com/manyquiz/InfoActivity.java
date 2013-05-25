package com.manyquiz;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InfoActivity extends Activity implements OnClickListener {

	Button btnBlouCalorieCounter;
	Button btnWineNotes;
	Button btnRecipeNotes;
	Button btnMikesBlog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		btnBlouCalorieCounter = (Button) findViewById(R.id.btnBlouCalorieCounter);
		btnWineNotes = (Button) findViewById(R.id.btnWineNotes);
		btnRecipeNotes = (Button) findViewById(R.id.btnRecipeNotes);
		btnMikesBlog = (Button) findViewById(R.id.btnMikesBlog);

		btnBlouCalorieCounter.setOnClickListener(this);
		btnWineNotes.setOnClickListener(this);
		btnRecipeNotes.setOnClickListener(this);
		btnMikesBlog.setOnClickListener(this);
	}	

	public void onClick(View v) {

		String url = "";

		if (v.getId() == R.id.btnBlouCalorieCounter) {
			url = getResources().getString(R.string.lnk_blou_calorie_counter);
		}
		else if (v.getId() == R.id.btnWineNotes) {
			url = getResources().getString(R.string.lnk_wine_notes);	
		}
		else if (v.getId() == R.id.btnRecipeNotes) {
			url = getResources().getString(R.string.lnk_recipe_notes);
		}
		else if (v.getId() == R.id.btnMikesBlog) {
			url = getResources().getString(R.string.lnk_mikes_blog);
		}

		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}
}
