package com.example.picture;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.picture.servlet.LoginToServer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ComoileActivity extends Activity implements OnClickListener{
	private String result = "";
	private String URL="http://srcer.com/mydemo/selpic/regist.php";
	protected static final int SERVER_SEND = 1001;
	private ImageButton ib_back2,ib_back5;
	private Button bt_signup;
	private EditText et_email,et_password,et_name;

	private Handler myHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SERVER_SEND:
				
					System.out.println("the password its wrong");
					Toast.makeText(ComoileActivity.this, "regesit failed", Toast.LENGTH_LONG).show();
				
				break;

			default:
				break;
			}
		}
		
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity8_main);
		ib_back2=(ImageButton) findViewById(R.id.ib_back2);
		ib_back5=(ImageButton) findViewById(R.id.ib_back5);
		bt_signup=(Button) findViewById(R.id.bt_signup);
		et_email=(EditText) findViewById(R.id.et_email);
		et_password=(EditText) findViewById(R.id.et_password);
		et_name=(EditText) findViewById(R.id.et_name);
		ib_back2.setOnClickListener(this);
		bt_signup.setOnClickListener(this);
		ib_back5.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ib_back2:
			Intent intent2=new Intent(this,MainActivity.class);
			startActivity(intent2);
			break;
		case R.id.bt_signup:
			

			new Thread(new Runnable() {
				@Override
				public void run() {
					/*
					LoginToServer login =new LoginToServer();*/
					String email=et_email.getText().toString();
					String password=et_password.getText().toString();
					String name = et_name.getText().toString();
					if(email.contains("@")==false) {
						Message msg=Message.obtain();
						msg.what=SERVER_SEND;
						myHandler.sendMessage(msg);
					}
					result = "";
					result =doPostReg(email, password,name);
					System.out.println("doPostReg result:["+result+"]");
					
					
				if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)&&result.equals("1")){
					Message msg=Message.obtain();
					msg.what=1;
					myHandler.sendMessage(msg);
					Intent intent=new Intent(ComoileActivity.this,UserActivity.class);
					startActivity(intent);
				}else{
					
					
					System.out.println("no login!");
					
					
					Message msg=Message.obtain();
					msg.what=SERVER_SEND;
					myHandler.sendMessage(msg);
					
				}
				
				}
			}).start();
			break;	
		case R.id.ib_back5:
			Intent intent5=new Intent(this,APictureShowActivity.class);
			startActivity(intent5);
			break;

		default:
			break;
		}
	}
	
	public String doPostReg(String email,String password,String name){
		HttpClient hc=new DefaultHttpClient();
		HttpPost hp=new HttpPost(URL);
		NameValuePair pairEmail=new BasicNameValuePair("uemail", email);
		NameValuePair pairPsd=new BasicNameValuePair("upassword", password);
		NameValuePair pairName=new BasicNameValuePair("uname", name);
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(pairEmail);
		list.add(pairPsd);
		list.add(pairName);
		HttpEntity he;
		try {
			he=new UrlEncodedFormEntity(list, "utf-8");
			hp.setEntity(he);
			HttpResponse response = hc.execute(hp);
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				System.out.println("成功返回");
				HttpEntity entity=response.getEntity();
				InputStream is=entity.getContent();
				BufferedReader reader=new BufferedReader(new InputStreamReader(is));
				String readLine=null;
				while((readLine=reader.readLine())!=null){
					result+=readLine;
				}
				is.close();
	
			}else{
				System.out.println("失败返回");
				result= "ERROR";
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result= "ERROR";
		}
		return result; 
	}
	
	
}

