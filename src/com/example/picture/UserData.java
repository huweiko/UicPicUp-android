package com.example.picture;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData {
	public static class Preference {

		public static final String LOGIN_USER = "LOGIN_USER";
		public static final String UNAME = "UNAME";
		public static final String PWD = "PWD";

		public static SharedPreferences getSharedPreferences(Context context) {
			return context.getSharedPreferences("MyFellowShipSharePref", Context.MODE_PRIVATE);
		}
	}
	private static String email ="guest";  
	private static String name = "";
    
    public static String getEmail() {  
        return email;
    }  
    public static void setEmail(String a) {  
    	UserData.email = a;  
    } 
    public static String getName() {  
        return name;
    }    
    public static void setName(String a) {  
    	UserData.name = a;  
    } 

}
