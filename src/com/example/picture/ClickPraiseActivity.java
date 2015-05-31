package com.example.picture;
import java.util.ArrayList;
import java.util.HashMap; 

import android.app.Activity;


import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.example.picture.servlet.GetUserInfo;
import com.example.picture.servlet.VoteTo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.GridView;
import android.widget.ImageButton;

public class ClickPraiseActivity extends Activity implements OnClickListener{
	private ImageButton ib_home,ib_camera,ib_back8;
	private GridView gv_timage;
	private RelativeLayout mRelativeLayoutBigPic;
	private ImageView mImageViewBigPic;
	private Button mButtonImageClose;
	private ApplicationData app=(ApplicationData) getApplication();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// 获取username
				new Thread(new Runnable() {
					@Override
					public void run() {
				GetUserInfo uif =new GetUserInfo();
				uif.doPost(UserData.getEmail());
					}
				}).start();
		
				System.out.println("@@@@ name: "+ UserData.getName());
	    //
		super.onCreate(savedInstanceState);
		
		System.out.println("@@@@@@@3 UserData.getName(): "+ UserData.getName());
		setContentView(R.layout.activity6_main);
		System.out.println("@@@@@@@4");
		gv_timage=(GridView) findViewById(R.id.gv_timage);
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
		ib_home=(ImageButton) findViewById(R.id.ib_home);
		ib_camera=(ImageButton) findViewById(R.id.ib_camera);
		ib_back8=(ImageButton) findViewById(R.id.ib_back8);
		ib_home.setOnClickListener(this);
		ib_camera.setOnClickListener(this);
		ib_back8.setOnClickListener(this);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
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
			MyAsynaPhoto mPhotoTask = new MyAsynaPhoto();
			System.out.println("get image: "+picinfo[1]);
			try {
				mPhotoTask.execute(picinfo[0],picinfo[1]).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("title", picinfo[0]);
			// strLocalImag: /mnt/sdcard_title2.jpg
			String strLocalImag = Environment.getExternalStorageDirectory() + "/DCIM/" +picinfo[0] +"_.jpg";
			System.out.println("strLocalImag: "+strLocalImag);
			Bitmap bm =null;
			try {

			    // 实例化Bitmap

				bm = BitmapFactory.decodeFile(strLocalImag);

			} catch (OutOfMemoryError e) {
//				bm = compressImageFromFile(strLocalImag);
			    //
			}

			map.put("image", bm);
			map.put("button", "");
			list.add(map);
			/*
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("imageItem", bp[i]);// 添加图像资源的ID
			String[] myPicInfo = (arrpics[i].split(","))[1].split("/");
			item.put("textItem", myPicInfo[3]);// 按序号添加ItemText
			items.add(item);*/
		}
		
		
		/*
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("title", "大门");
		map.put("image", R.drawable.a);
		map.put("button", "");
		
		
		
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put("title", "广场");
		map2.put("image", R.drawable.a);
		map2.put("button", "");
		Map<String,Object> map3=new HashMap<String,Object>();
		map3.put("title", "街道");
		map3.put("image", R.drawable.c);
		map3.put("button", "");
		Map<String,Object> map4=new HashMap<String,Object>();
		map4.put("title", "街道");
		map4.put("image", R.drawable.d);
		map4.put("button", "");
		Map<String,Object> map5=new HashMap<String,Object>();
		map5.put("title", "学堂");
		map5.put("image", R.drawable.e);
		map5.put("button", "");
		Map<String,Object> map6=new HashMap<String,Object>();
		map6.put("title", "食堂门口");
		map6.put("image", R.drawable.f);
		map6.put("button", "");
		Map<String,Object> map7=new HashMap<String,Object>();
		map7.put("title", "操场");
		map7.put("image", R.drawable.g);
		map7.put("button", "");
		Map<String,Object> map8=new HashMap<String,Object>();
		map8.put("title", "走廊");
		map8.put("image", R.drawable.h);
		map8.put("button", "");
		Map<String,Object> map9=new HashMap<String,Object>();
		map9.put("title", "庭院");
		map9.put("image", R.drawable.i);
		map9.put("button", "");
		Map<String,Object> map10=new HashMap<String,Object>();
		map10.put("title", "草坪");
		map10.put("image", R.drawable.j);
		map10.put("button", "");
		Map<String,Object> map11=new HashMap<String,Object>();
		map11.put("title", "街道");
		map11.put("image", R.drawable.k);
		map11.put("button", "");
		Map<String,Object> map12=new HashMap<String,Object>();
		map12.put("title", "后院");
		map12.put("image", R.drawable.l);
		map12.put("button", "");
		Map<String,Object> map13=new HashMap<String,Object>();
		map13.put("title", "前院");
		map13.put("image", R.drawable.m);
		map13.put("button", "");
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		list.add(map7);
		list.add(map8);
		list.add(map9);
		list.add(map10);
		list.add(map11);
		list.add(map12);
		list.add(map13); */
		
		
		SimpleAdapter adapter=new SimpleAdapter(this, list, R.layout.list_aitem, new String[]{"title","image","button"}, new int[]{R.id.title,R.id.image,R.id.button})
		{
            //在这个重写的函数里设置 每个 item 中按钮的响应事件
            @Override
            public View getView(int position, View convertView,ViewGroup parent) {
                final int p=position;
                final View view=super.getView(position, convertView, parent);
                final TextView v_title = (TextView)view.findViewById(R.id.title);
                final Button button=(Button)view.findViewById(R.id.button);
                
                //设置所有都不选
                button.setText("  ");
        		button.setBackgroundResource(R.drawable.pro_zan_sel);
                button.setOnClickListener(new OnClickListener() {
                	
                    @Override
                    public void onClick(View v) {
                    	/*System.out.println("@@@@@@@5");
                    	app.setB("hello");
                    	String status=app.getB();
                    	System.out.println("@@@@@@@6");
                        if(status!="0"){
                    	
                    	app.setB("0");*/
                    	
                    	if((String)button.getText()==" ") {
                    		//取消喜欢
                    		button.setText("  ");
                    		button.setBackgroundResource(R.drawable.pro_zan_sel);
                    		//更新数据库
                    		final String strTitle = (String)v_title.getText();
                        	
                        	
                        	//提交到服务器
        					new Thread(new Runnable() {
        						@Override
        						public void run() {
        							VoteTo vt =new VoteTo();
        							vt.doPost("votecancel",strTitle);
        						}
        					}).start();
        			
        					System.out.println("votecancel: " + strTitle);
                    	} else {
                    		//选择喜欢
                    		button.setText(" ");
                    		button.setBackgroundResource(R.drawable.love);
                    		//更新数据库
                    		final String strTitle = (String)v_title.getText();
                        	System.out.println("selected: " + strTitle);
                        	
                        	//提交到服务器
        					new Thread(new Runnable() {
        						@Override
        						public void run() {
        							VoteTo vt =new VoteTo();
        							vt.doPost("voteto",strTitle);
        						}
        					}).start();
        					System.out.println("voteto: " + strTitle);
                    	}
                    	
                    }
                });
                return view;
            }
           
		};
		

		 adapter.setViewBinder(new ViewBinder() {  
            
            public boolean setViewValue(View view, Object data,  
                    String textRepresentation) {  
                //判断是否为我们要处理的对象  
                if(view instanceof ImageView  && data instanceof Bitmap){  
                    ImageView iv = (ImageView) view;  
                    final Bitmap bitmap = (Bitmap) data;
                    iv.setImageBitmap(bitmap);  
                    iv.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        	mRelativeLayoutBigPic.setVisibility(View.VISIBLE);
                        	Log.i("hhhh", "w = "+bitmap.getWidth() +"  h = "+bitmap.getHeight());
                        	Log.i("rrrr", "w = "+gv_timage.getWidth() +"  h = "+gv_timage.getHeight());
                        	float bili = gv_timage.getWidth()/bitmap.getWidth();
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

		
		
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ib_home:
			Toast.makeText(this, "这就是Home", Toast.LENGTH_LONG).show();
			break;
		case R.id.ib_camera:
			Intent intent7=new Intent(this,SendPictureActivity.class);
			startActivity(intent7);
			break;
		case R.id.ib_back8:
			Intent intent8=new Intent(this,PersonInfoActivity.class);
			startActivity(intent8);
		break;
		default:
			break;
		}
	}
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
	  public Bitmap compressImageFromFile(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;//只读边,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = 320f;//
		float ww = 240f;//
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置采样率
		
		newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
		
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//			return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
									//其实是无效的,大家尽管尝试
		return bitmap;
	}
}
