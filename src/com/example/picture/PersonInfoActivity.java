package com.example.picture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.example.picture.servlet.UpdateUserInfo;
import com.example.picture.servlet.VoteTo;
import com.example.picture.util.ImageTools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

public class PersonInfoActivity extends Activity implements OnClickListener{
	private ImageButton ib_edit,ib_save;
	private TextView tv_email, tv_name;
	private String strName, strEmail;
	private ImageButton ib_back2,ib_back5;
	private static final int SCALE = 5;// 照片缩小比例
	
	private GridView gv_timage;
	SimpleAdapter adapter;
	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	protected RequestDialog requestDialog;
	
	private RelativeLayout mRelativeLayoutBigPic;
	private ImageView mImageViewBigPic;
	private Button mButtonImageClose;
	/**
	 * 显示对话框
	 * @param strId
	 */
	public void showReqeustDialog(int strId){
		if(requestDialog == null){
			requestDialog = new RequestDialog(this);
		}
		requestDialog.setCancelable(false);
		requestDialog.setMessage(getString(strId));
		
		if(!requestDialog.isShowing()){
			requestDialog.show();
		}
	}
	
	/**
	 * 取消对话框
	 */
	public void cancelRequestDialog(){
		if(requestDialog != null && requestDialog.isShowing()){
			requestDialog.cancel();
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity9_main);
		gv_timage=(GridView) findViewById(R.id.user_timage);
		ib_edit=(ImageButton)findViewById(R.id.bt_edit);
		ib_save=(ImageButton)findViewById(R.id.bt_save);
		tv_name=(TextView)findViewById(R.id.tv_name);
		tv_email=(TextView)findViewById(R.id.tv_email);
		ib_back2=(ImageButton) findViewById(R.id.ib_back2);
		ib_back5=(ImageButton) findViewById(R.id.ib_back5);
		mRelativeLayoutBigPic=(RelativeLayout) findViewById(R.id.RelativeLayoutBigPic);
		mImageViewBigPic=(ImageView) findViewById(R.id.ImageViewBigPic);
		mButtonImageClose=(Button) findViewById(R.id.ButtonImageClose);
		
		mButtonImageClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mRelativeLayoutBigPic.setVisibility(View.GONE);
			}
		});
		ib_back2.setOnClickListener(this);
		ib_back5.setOnClickListener(this);
		// 将保存在本地的图片取出并缩小后显示在界面上
	/*	Bitmap bitmap = BitmapFactory.decodeFile(Environment
				.getExternalStorageDirectory() + "/image.jpg");
		if(bitmap != null){
			Bitmap newBitmap = ImageTools.zoomBitmap(bitmap,
					bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
			// 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
			bitmap.recycle();
			mImageViewUserImage.setImageBitmap(newBitmap);
		}*/
		
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
		showReqeustDialog(R.string.load_pic_list);
      	//提交到服务器
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				getUrlPictureList();
				mHandler.sendEmptyMessage(MSG_UPDATE_PICLIST);
				cancelRequestDialog();
			}
		}).start();
		
	}
	private static final int MSG_UPDATE_PICLIST = 0x0001;
	Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_UPDATE_PICLIST:
				adapter.setViewBinder(new ViewBinder() {  
		            
		            public boolean setViewValue(View view, Object data,  
		                    String textRepresentation) {  
		                //判断是否为我们要处理的对象  
		                if(view instanceof ImageView  && data instanceof Bitmap){  
		                    ImageView iv = (ImageView) view;  
		                    final Bitmap bitmap = (Bitmap) data;
		                    iv.setImageBitmap(zoomImage(bitmap, 320, 240)); 
		                    iv.setOnClickListener(new OnClickListener() {
		                        @Override
		                        public void onClick(View v) {
		                        	mRelativeLayoutBigPic.setVisibility(View.VISIBLE);
		                        	Log.i("hhhh", "w = "+bitmap.getWidth() +"  h = "+bitmap.getHeight());
		                        	Log.i("rrrr", "w = "+gv_timage.getWidth() +"  h = "+gv_timage.getHeight());
		                        	double bili = (double)gv_timage.getWidth()/(double)bitmap.getWidth();
		                        	Log.i("比例", "bili = "+bili);
		                        	double w = gv_timage.getWidth();
		                        	double h = bitmap.getHeight()*bili;
		                        	mImageViewBigPic.setImageBitmap(zoomImage(bitmap,w,h));
		                        }
		                    });
		                    return true;  
		                }else  
		                return false;  
		            }  
		        });


			
				//绑定适配器
				gv_timage.setAdapter(adapter);     
				
				//设置背景颜色选择器
				//listview.setSelector(R.drawable.on_item_selected);
				
				//设置焦点响应问题    同时要将 item 中的焦点 focusable 设置为 false
				gv_timage.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
				
				//设置 item 的监听事件
				gv_timage.setOnItemClickListener(new OnItemClickListener() {
			    @Override
				    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				        
				        //获得 item 里面的文本控件
				       // TextView text1=(TextView)view.findViewById(R.id.text1);
				        //Toast.makeText(getApplicationContext(), text1.getText().toString(), Toast.LENGTH_SHORT).show();
				    }
				});
				break;

			default:
				break;
			}
			
		};
	};
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,  
            double newHeight) {  
		// 获取这个图片的宽和高  
		float width = bgimage.getWidth();  
		float height = bgimage.getHeight();  
		// 创建操作图片用的matrix对象  
		Matrix matrix = new Matrix();  
		// 计算宽高缩放率  
		float scaleWidth = ((float) newWidth) / width;  
		float scaleHeight = ((float) newHeight) / height;  
		// 缩放图片动作  
		matrix.postScale(scaleWidth, scaleHeight);  
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,  
		            (int) height, matrix, true);  
		return bitmap;  
	}  
	private void getUrlPictureList() {
		
		//从远程服务器上获取图片地址
		MyAsynaGet mTask = new MyAsynaGet();
		String strRet = null;
		try {
			strRet = mTask.execute(UserData.getEmail()).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(" ClickPraiseActivity @@@@@strRet[" + strRet + "]");
		
		// 准备要添加的数据条目
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		String[] arrpics = strRet.split(";");
		System.out.println("arrpics.length: " +arrpics.length);
		for (int i = 0; i < arrpics.length; i++) {
			System.out.println("down now!");
			// 获取下载图片的URL
			String[] picinfo = arrpics[i].split(",");
		
			Map<String,Object> map=new HashMap<String,Object>();

			
			// strLocalImag: /mnt/sdcard_title2.jpg
			String strLocalImag = Environment.getExternalStorageDirectory() + "/DCIM/" +picinfo[0] +"_.jpg";
			System.out.println("strLocalImag: "+strLocalImag);
			if(picinfo[0].equals("Aaa")){
				try {
				    // 实例化Bitmap
					BitmapFactory.decodeFile(strLocalImag);

				} catch (OutOfMemoryError e) {
				    //
				}
			}
			
			if(picinfo[0].equals("") || picinfo[0].equals("nip")){
				/*map.put("title", "nip");
				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error); 
				map.put("image", bmp);
				*/
			}else{
				Bitmap bm =null;
				try {
				    // 实例化Bitmap
					bm = BitmapFactory.decodeFile(strLocalImag);
					if(bm != null){
						map.put("title", picinfo[0]);
						map.put("image", bm);
						list.add(map);
					}

				} catch (OutOfMemoryError e) {
				    //
				}
				
			}
			
			
			
			/*Map<String, Object> item = new HashMap<String, Object>();
			item.put("imageItem", bp[i]);// 添加图像资源的ID
			String[] myPicInfo = (arrpics[i].split(","))[1].split("/");
			item.put("textItem", myPicInfo[3]);// 按序号添加ItemText
			items.add(item);*/
		}
		adapter=new SimpleAdapter(this, list, R.layout.list_item, new String[]{"title","image"}, new int[]{R.id.title,R.id.image})
		{
            //在这个重写的函数里设置 每个 item 中按钮的响应事件
            @Override
            public View getView(int position, View convertView,ViewGroup parent) {
                final View view=super.getView(position, convertView, parent);
                return view;
            }
           
		};
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
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
