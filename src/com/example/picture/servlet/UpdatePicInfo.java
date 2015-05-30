package com.example.picture.servlet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

import com.example.picture.UserData;

public class UpdatePicInfo {
	private String URL="http://srcer.com/mydemo/selpic/uptpicinfo.php";
	private String result="";
	public String doPost(String strTitle,String strEmail,String strUrl){
		HttpClient hc=new DefaultHttpClient();
		HttpPost hp=new HttpPost(URL);
		NameValuePair pairEmail=new BasicNameValuePair("title", strTitle);
		NameValuePair pairName=new BasicNameValuePair("email", strEmail);
		NameValuePair pairUrl=new BasicNameValuePair("url", strUrl);
		System.out.println("#### #####"+strTitle+strEmail+strUrl );
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(pairEmail);
		list.add(pairName);
		list.add(pairUrl);
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
		System.out.println("doPost UpdatePicInfo ret[" + result + "]");
		return result; 
	}
}


