package com.example.picture;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.example.picture.servlet.LoginToServer;
import com.example.picture.servlet.UpdatePicInfo;
import com.example.picture.util.Base64Coder;
import com.example.picture.util.ImageTools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SendPictureActivity extends Activity implements OnClickListener {
	// 服务器地址
	private String uploadUrl = "http://www.srcer.com/mydemo/selpic/upload.php";
	private static final String path = "/sdcard/image.jpg"; 
	// 显示图片
	private ImageView image;
	// 两个but
	private Button take;
	private Button selete;
	
	// 记录文件名
	private String filename;
	// 上传的bitmap
	private Bitmap upbitmap;
	private Button up;
	private TextView tv_title;

	// 多线程通信
	private Handler myHandler=new Handler(){
		public void handleMessage(Message msg) {
			myDialog.dismiss();
		};
	};

	private ProgressDialog myDialog;
	private static final int TAKE_PICTURE = 0;
	private static final int CHOOSE_PICTURE = 1;

	private static final int SCALE = 5;// 照片缩小比例
	private ImageView iv_image = null;
	private ImageButton ib_back6;
	private Button bt_post;
	private Button bt_submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity7_main);

		iv_image = (ImageView) this.findViewById(R.id.iv_image);
		bt_post = (Button) this.findViewById(R.id.bt_post);
		bt_submit =(Button) this.findViewById(R.id.bt_submit);
		tv_title = (TextView) this.findViewById(R.id.title);
		bt_post.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
		showPicturePicker(SendPictureActivity.this);
		ib_back6 = (ImageButton) findViewById(R.id.ib_back6);
		ib_back6.setOnClickListener(this);
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_PICTURE:
				// 将保存在本地的图片取出并缩小后显示在界面上
				Bitmap bitmap = BitmapFactory.decodeFile(Environment
						.getExternalStorageDirectory() + "/image.jpg");
				Bitmap newBitmap = ImageTools.zoomBitmap(bitmap,
						bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
				// 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
				bitmap.recycle();

				// 将处理过的图片显示在界面上，并保存到本地
				iv_image.setImageBitmap(newBitmap);
				ImageTools.savePhotoToSDCard(newBitmap, Environment
						.getExternalStorageDirectory().getAbsolutePath(),
						String.valueOf(System.currentTimeMillis()));
				break;

			case CHOOSE_PICTURE:
				ContentResolver resolver = getContentResolver();
				// 照片的原始资源地址
				Uri originalUri = data.getData();
				try {
					// 使用ContentProvider通过URI获取原始图片
					Bitmap photo = MediaStore.Images.Media.getBitmap(resolver,
							originalUri);
					if (photo != null) {
						// 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
						Bitmap smallBitmap = ImageTools.zoomBitmap(photo,
								photo.getWidth() / SCALE, photo.getHeight()
										/ SCALE);
						// 释放原始图片占用的内存，防止out of memory异常发生
						photo.recycle();

						iv_image.setImageBitmap(smallBitmap);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	}

	

	// 上传
	public void upload() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		upbitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
		byte[] b = stream.toByteArray();
		// 将图片流以字符串形式存储下来
		String file = new String(Base64Coder.encodeLines(b));
		HttpClient client = new DefaultHttpClient();
		// 设置上传参数
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("file", file));
		formparams.add(new BasicNameValuePair("title", tv_title.getText().toString()));
		formparams.add(new BasicNameValuePair("email", UserData.getEmail()));
		System.out.print(UserData.getEmail()+" upload image: "+tv_title.getText().toString() +"!!!!");
		HttpPost post = new HttpPost(uploadUrl);	
		
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			post.addHeader("Accept",
					"text/javascript, text/html, application/xml, text/xml");
			post.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
			post.addHeader("Accept-Encoding", "gzip,deflate,sdch");
			post.addHeader("Connection", "Keep-Alive");
			post.addHeader("Cache-Control", "no-cache");
			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			System.out.println("response:"
					+ response.getStatusLine().getStatusCode());
			HttpEntity e = response.getEntity();
			System.out.println("打印出来吗：" + EntityUtils.toString(e));
			if (200 == response.getStatusLine().getStatusCode()) {
				System.out.println("上传完成");
			} else {
				System.out.println("上传失败");
			}
			client.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showPicturePicker(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("图片来源");
		builder.setNegativeButton("取消", null);
		builder.setItems(new String[] { "拍照", "相册" },
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case TAKE_PICTURE:
							Intent openCameraIntent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							Uri imageUri = Uri.fromFile(new File(Environment
									.getExternalStorageDirectory(), "image.jpg"));
							// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
							openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
									imageUri);
							startActivityForResult(openCameraIntent,
									TAKE_PICTURE);
							break;

						case CHOOSE_PICTURE:
							Intent openAlbumIntent = new Intent(
									Intent.ACTION_GET_CONTENT);
							openAlbumIntent.setType("image/*");
							startActivityForResult(openAlbumIntent,
									CHOOSE_PICTURE);
							break;

						default:
							break;
						}
					}
				});
		builder.create().show();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ib_back6:
			Intent intent = new Intent(this, ClickPraiseActivity.class);
			startActivity(intent);
			break;
		case R.id.bt_post:
			myDialog = ProgressDialog.show(this, "Loading...", "Please wait...", true, false);
			System.out.println("进来了吗1");
			new Thread(new Runnable() {
				public void run() {
					Looper.prepare();
					System.out.println("进来了吗2");
						uploadFile(uploadUrl);
					myHandler.sendMessage(new Message());
				}
			}).start();
			Looper.loop();   
	
			break;
		case R.id.bt_submit:
			//发送请求，获取结果，相应处理
			new Thread() {
				@Override
				public void run() {
					UpdatePicInfo uppic =new UpdatePicInfo();
					

	
					String strTitle = tv_title.getText().toString();
					String strEmail= UserData.getEmail();
					SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");       
					String date = sDateFormat.format(new java.util.Date());
					String strUrl = date + ".jpg";
					System.out.println("@@@@@@ @@@@@" +strTitle+strEmail+strUrl);
					String result=uppic.doPost(strTitle,strEmail,strUrl);
					//System.out.println("email："+email+",password:"+password+"result:"+result);
				}
			}.start();
			break;
		default:
			break;
		}
	}
	
	
	/* 上传文件至Server，uploadUrl：接收文件的处理页面 */
	  private void uploadFile(String uploadUrl)
	  {
	    String end = "\r\n";
	    String twoHyphens = "--";
	    String boundary = "******";
	    try
	    {
	      URL url = new URL(uploadUrl);
	      HttpURLConnection httpURLConnection = (HttpURLConnection) url
	          .openConnection();
	      // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
	      // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
	      httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
	      // 允许输入输出流
	      httpURLConnection.setDoInput(true);
	      httpURLConnection.setDoOutput(true);
	      httpURLConnection.setUseCaches(false);
	      // 使用POST方法
	      httpURLConnection.setRequestMethod("POST");
	      httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
	      httpURLConnection.setRequestProperty("Charset", "UTF-8");
	      httpURLConnection.setRequestProperty("Content-Type",
	          "multipart/form-data;boundary=" + boundary);
	 
	      DataOutputStream dos = new DataOutputStream(
	          httpURLConnection.getOutputStream());
	      dos.writeBytes(twoHyphens + boundary + end);
	      dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""
	          + path.substring(path.lastIndexOf("/") + 1)
	          + "\""
	          + end);
	      System.out.println("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""
	          + path.substring(path.lastIndexOf("/") + 1)
	          + "\""
	          + end);
	      dos.writeBytes(end);
	 
	      FileInputStream fis = new FileInputStream(path);
	      byte[] buffer = new byte[8192]; // 8k
	      int count = 0;
	      // 读取文件
	      while ((count = fis.read(buffer)) != -1)
	      {
	        dos.write(buffer, 0, count);
	      }
	      fis.close();
	 
	      dos.writeBytes(end);
	      dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
	      dos.flush();
	 
	      InputStream is = httpURLConnection.getInputStream();
	      InputStreamReader isr = new InputStreamReader(is, "utf-8");
	      BufferedReader br = new BufferedReader(isr);
	      String result = br.readLine();
	 
	      
	      Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	      dos.close();
	      is.close();
	 
	    } catch (Exception e)
	    {
	      e.printStackTrace();
	      setTitle(e.getMessage());
	    }
	  }
    
    
    
  
	
}
