package com.example.picture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class IntroduceActivity extends Activity {
	private ImageButton ib_back2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity3_main);
		ib_back2=(ImageButton) findViewById(R.id.ib_back2);
		ib_back2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent2=new Intent(IntroduceActivity.this,MainActivity.class);
				startActivity(intent2);
			}
		});
	}
}
