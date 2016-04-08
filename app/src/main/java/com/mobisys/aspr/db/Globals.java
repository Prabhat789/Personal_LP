package com.mobisys.aspr.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Globals {

	public final static String TAG = Globals.class.getSimpleName();
    private static final String SHARED = "DOCTOR";
    private static final String USER_ID = "USER_ID";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_EMAIL = "USER_EMAIL";
    private static final String USER_PASSWORD = "USER_PASSWORD";
    private static final String USER_MOBILE_NUMBER = "USER_MOBILE_NUMBER";
    private static final String LATITUDE = "LATITUDE";
    private static final String LONGITUDE = "LONGITUDE";
    private static final String IS_PUSHENABLE = "IS_PUSHENABLE";
    private static final String FILEPATH = "FILEPATH";
    // wifi and data connection constants
    public static int wifiStatus;
    public static String wifiStatusString;
    public static boolean isWifiAvailable;
    public static boolean isMobileDataAvailable;
    public static int airplaneModeStatus;
    public static int width;
    public static int height;
    public static int AWS_ONNECTION_TIMEOUT = 30000;
    Context context;
    private SharedPreferences sharedPref;
    private Editor editor;


    public Globals(Context context) {
		sharedPref = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
		editor = sharedPref.edit();
		this.context = context;
	}

    public String getUserId() {
        return sharedPref.getString(USER_ID, "");
    }

	public void setUserId(String status) {
		editor.putString(USER_ID, status);
		editor.commit();
	}

    public String getUserName() {
        return sharedPref.getString(USER_NAME, "");
    }

    public void setUserName(String status) {
        editor.putString(USER_NAME, status);
        editor.commit();
    }

    public String getUserEmail() {
        return sharedPref.getString(USER_EMAIL, "");
    }

    public void setUserEmail(String status) {
        editor.putString(USER_EMAIL, status);
        editor.commit();
    }

    public String getUserPassword() {
        return sharedPref.getString(USER_PASSWORD, "");
    }

    public void setUserPassword(String status) {
        editor.putString(USER_PASSWORD, status);
        editor.commit();
    }

    public String getMobileNumber() {
        return sharedPref.getString(USER_MOBILE_NUMBER, "");
    }

    public void setMobileNumber(String status) {
        editor.putString(USER_MOBILE_NUMBER, status);
        editor.commit();
    }

    public String getLatitude() {
        return sharedPref.getString(LONGITUDE, "");
    }

    public void setLatitude(String status) {
        editor.putString(LATITUDE, status);
        editor.commit();
    }

    public String getLongitude() {
        return sharedPref.getString(LONGITUDE, "");
    }

    public void setLongitude(String status) {
        editor.putString(LATITUDE, status);
        editor.commit();
    }

    public String getIsPushEnable() {
        return sharedPref.getString(IS_PUSHENABLE, "Yes");
    }

    public void setIsPushEnable(String status) {
        editor.putString(IS_PUSHENABLE, status);
        editor.commit();
    }
    public String getfileName() {
        return sharedPref.getString(FILEPATH, "");
    }

    public void setfileName(String status) {
        editor.putString(FILEPATH, status);
        editor.commit();
    }
}

