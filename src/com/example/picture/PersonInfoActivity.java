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

import com.example.picture.servlet.GetUserInfo;
import com.example.picture.servlet.LoginToServer;
import com.example.picture.servlet.UpdateUserInfo;
import com.example.picture.util.ImageTools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonInfoActivity extends Activity implements OnClickListener{
	private ImageButton ib_edit,ib_save;
	private TextView tv_email, tv_name;
	private String strName, strEmail;
	private ImageView mImageViewUserImage;
	private static final int SCALE = 5;// 照片缩小比例
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity9_main);
		
		ib_edit=(ImageButton)findViewById(R.id.bt_edit);
		ib_save=(ImageButton)findViewById(R.id.bt_save);
		tv_name=(TextView)findViewById(R.id.tv_name);
		tv_email=(TextView)findViewById(R.id.tv_email);
		mImageViewUserImage=(ImageView)findViewById(R.id.ImageViewUserImage);
		// 将保存在本地的图片取出并缩小后显示在界面上
		Bitmap bitmap = BitmapFactory.decodeFile(Environment
				.getExternalStorageDirectory() + "/image.jpg");
		if(bitmap != null){
			Bitmap newBitmap = ImageTools.zoomBitmap(bitmap,
					bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
			// 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
			bitmap.recycle();
			mImageViewUserImage.setImageBitmap(newBitmap);
		}
		
		tv_email.setText(UserData.getEmail());
		tv_name.setText(UserData.getName());
		
		tv_name.setEnabled(false);
		tv_email.setEnabled(false);
		
		ib_edit.setOnClickListener(this);
		ib_save.setOnClickListener(this);
		
		ib_edit.setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View v) {
		        // TODO Auto-generated method stub
		    	  tv_name.setEnabled(true);
		      }
		    });
	
		ib_save.setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View v) {
		        // TODO Auto-generated method stub
		    	  tv_name.setEnabled(false);
		  			tv_email.setEnabled(false);
		    	  strName = tv_name.getText().toString();
		    	  strEmail = tv_email.getText().toString();
		    	  UserData.setEmail(strEmail);
		    	  UserData.setName(strName);
		    	  System.out.println("strName: "+strName+" strEmail: "+ strEmail);
		    	  //提交到服务器
					new Thread(new Runnable() {
						@Override
						public void run() {
						UpdateUserInfo uuf =new UpdateUserInfo();
					uuf.doPost(UserData.getEmail(),UserData.getName());
						}
					}).start();
			
					System.out.println("@@@@ name: "+ UserData.getName());
		    	  
		      }
		    });
	
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}
