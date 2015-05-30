package com.example.picture;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity implements OnClickListener{
	private Button bt_guest,bt_join;
	private TextView tv_what;
	public  static int status=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2_main);
		tv_what=(TextView) findViewById(R.id.tv_what);
		bt_guest=(Button) findViewById(R.id.bt_guest);
		bt_join=(Button) findViewById(R.id.bt_join);
		bt_guest.setOnClickListener(this);
		bt_join.setOnClickListener(this);
		tv_what.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.bt_guest:
			Intent intent4=new Intent(this,PictureShowActivity.class);
			startActivity(intent4);
			break;
		case R.id.bt_join:
			Intent intent1=new Intent(this,UserActivity.class);
			startActivity(intent1);
			break;
		case R.id.tv_what:
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("介绍");
			builder.setIcon(R.drawable.ic_launcher);
			builder.setMessage("的UicPicUp设计UIC学生到UicPicUo应用是专为UIC学生浏览或发布UIC校园查看照片。上传照片在比赛中可以加入并展示最好的UIC视图照片在上个月。");
			builder.setPositiveButton("英文介绍", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent3=new Intent(MainActivity.this,IntroduceActivity.class);
					startActivity(intent3);
				}
			});
			builder.setNegativeButton("取消", null);
			builder.create();
			builder.show();
			break;
		}
	}

}
