package com.example.picture;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class ccacitvity extends Activity implements OnClickListener{
	private ImageButton ib_back2,ib_back5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity9_main);
		ib_back2=(ImageButton) findViewById(R.id.ib_back2);
		ib_back5=(ImageButton) findViewById(R.id.ib_back5);
		ib_back2.setOnClickListener(this);
		ib_back5.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ib_back2:
			Intent intent2=new Intent(this,MainActivity.class);
			startActivity(intent2);
			break;
		case R.id.ib_back5:
			Intent intent5=new Intent(this,APictureShowActivity.class);
			startActivity(intent5);
			break;

		default:
			break;
		}
	}
}
