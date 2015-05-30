package com.example.picture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;  
import java.net.HttpURLConnection;  
import java.net.URL; 

import android.graphics.Bitmap;  
import android.graphics.BitmapFactory; 
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.util.Log;
import android.view.InflateException;

public class MyAsynaPhoto extends AsyncTask<String, Integer, Bitmap>{
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
	
	 @Override
     protected void onProgressUpdate(Integer... progress) {
     }

	@Override
	protected Bitmap doInBackground(String... arg0) {
		 Bitmap bitmap=null;  
	        try {  
/*	        	Log.i("arg0", String.format("###[%s]", arg0[0]));
	             URL url=new URL(arg0[0]);  
	             
	             HttpURLConnection connection=(HttpURLConnection) url.openConnection();  
	             connection.setDoInput(true);  
	             connection.connect();  
	             InputStream inputStream=connection.getInputStream();  
	             bitmap=BitmapFactory.decodeStream(inputStream);
	             Log.i("LogDemo", "###end");
	             inputStream.close();  */
	             
	             
		    	 String url =arg0[1];
		    	 System.out.println(url);
		    	 Bitmap tmpBitmap = null;  
		    	 try { 
		    	 InputStream is = new java.net.URL(url).openStream(); 
		    	 tmpBitmap = BitmapFactory.decodeStream(is); 
		    	 is.close();
		    	 } catch (Exception e) { 
		    	 e.printStackTrace(); 
		    	 Log.i("down image failed...", e.getMessage()); 
		    	 } 
		    	 if(tmpBitmap!=null) {
			        	System.out.println("down image["+arg0[1]+"] ok!!!!");
			        }
		    	 
		    	 //保存图片
		    	  File f = new File("/sdcard/DCIM/",arg0[0]+"_.jpg");
		    	  if (f.exists()) {
		    	   f.delete();
		    	  }
		    	  try {
		    		  Log.i("start to save image...", arg0[0]); 
		    	   FileOutputStream out = new FileOutputStream(f);
		    	   tmpBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
		    	   out.flush();
		    	   out.close();
		    	   System.out.println("save image ok! /sdcard/mypic/"+arg0[0]);
		    	  } catch (FileNotFoundException e) {
		    	   // TODO Auto-generated catch block
		    	   e.printStackTrace();
		    	  } catch (InflateException e) {
		    	   // TODO Auto-generated catch block
		    	   e.printStackTrace();
		    	  }
		    	 
		    	 return tmpBitmap;      
	             
	        } catch (Exception e) {  
	            // TODO: handle exception   
	        }
	       
	        
	        return bitmap;  
	}

}
