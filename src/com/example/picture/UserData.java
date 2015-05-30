package com.example.picture;

public class UserData {
	
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
