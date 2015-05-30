package com.example.picture;

import com.example.picture.servlet.LoginToServer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UserActivity extends Activity implements OnClickListener{
	protected static final int SERVER_SEND = 1001;
	private ImageButton ib_back2;
	private Button bt_signup,bt_log;
	private EditText et_email,et_password;
	private Handler myHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SERVER_SEND:
				
					System.out.println("邮箱与密码不正确");
					Toast.makeText(UserActivity.this, "邮箱与密码不正确", Toast.LENGTH_LONG).show();
				
				break;

			default:
				break;
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity1_main);
		ib_back2=(ImageButton) findViewById(R.id.ib_back2);
		bt_signup=(Button) findViewById(R.id.bt_signup);
		bt_log=(Button) findViewById(R.id.bt_log);
		et_email=(EditText) findViewById(R.id.et_email);
		et_password=(EditText) findViewById(R.id.et_password);
		ib_back2.setOnClickListener(this);
		bt_signup.setOnClickListener(this);
		bt_log.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ib_back2:
			Intent intent2=new Intent(this,MainActivity.class);
			startActivity(intent2);
			finish();
			break;
		case R.id.bt_signup:
			//跳转到注册页面
			Intent intent6=new Intent(this,ComoileActivity.class);
			startActivity(intent6);
			break;	
		case R.id.bt_log:
			//发送请求，获取结果，相应处理
			new Thread(new Runnable() {
				@Override
				public void run() {
					LoginToServer login =new LoginToServer();
					String email=et_email.getText().toString();
					String password=et_password.getText().toString();
					String result=login.doPost(email, password);
					//System.out.println("email："+email+",password:"+password+"result:"+result);
					
					//System.out.println("@@@@@@@1");
				if(result.equals("1")) {
					//设置全局变量
					UserData.setEmail(email);
					Message msg=Message.obtain();
					msg.what=1;
					myHandler.sendMessage(msg);
					Intent intent=new Intent(UserActivity.this,ClickPraiseActivity.class);
					startActivity(intent);
				}else{
					UserData.setEmail("guest");
					Message msg=Message.obtain();
					msg.what=SERVER_SEND;
					myHandler.sendMessage(msg);
				}
				
				}
			}).start();
			break;
		default:
			break;
		}
	}
}
