package com.example.picture;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.AsyncTask;

// GET请求获取字符串
public class MyAsynaGet extends AsyncTask<String, Integer, String>{
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
	
	 @Override
     protected void onProgressUpdate(Integer... progress) {
     }
	 

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		HttpResponse httpResponse = null; 
		String url = "http://www.srcer.com/mydemo/selpic/getphoto.php?uemail=" + arg0[0];
		
		System.out.println("url:" + url );
		// 使用网络连接类HttpClient
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        InputStream inputStream=null;
        try {
        	httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                
                inputStream = httpEntity.getContent();
             // 从反馈的response中获得输入流
                BufferedReader mReader = new BufferedReader(new InputStreamReader(inputStream));
                String resultString="";  
                String lineString="";  
                while((lineString=mReader.readLine())!=null){  
                    resultString=resultString+lineString;  
                }
                return resultString;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
	}



}
