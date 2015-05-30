package com.example.picture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class APictureShowActivity extends Activity implements OnClickListener{
	private ImageButton ib_back4; 
	private ImageView iv_aimage; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity5_main);
		ib_back4=(ImageButton) findViewById(R.id.ib_back4);
		iv_aimage=(ImageView) findViewById(R.id.iv_aimage);
		ib_back4.setOnClickListener(this);
		iv_aimage.setImageResource(R.drawable.a);
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ib_back4:
			Intent intent4=new Intent(this,PictureShowActivity.class);
			startActivity(intent4);
			break;

		default:
			break;
		}
	}
}
