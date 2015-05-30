package com.example.picture;

import android.app.Application;

public class ApplicationData extends Application{
	private String b;
	
	public String getB(){
		return this.b;
	}
	public void setB(String c){
		this.b= c;
	}
	@Override
	public void onCreate(){
		b = "hello";
		super.onCreate();
	}
}
