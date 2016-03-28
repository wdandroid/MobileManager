package com.cskaoyan.mobile.application;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by Lan on 2016/3/25.
 */
public class MyApplication extends Application{


    public static  String SERVER_PATH  ;
    public static  SharedPreferences configsp;
    public static  SharedPreferences.Editor editor;
    @Override
    public void onCreate() {
        super.onCreate();

         SERVER_PATH="http://192.168.3.34/MobileManager";

         configsp=getSharedPreferences("config",MODE_PRIVATE);

         editor =configsp.edit();
    }


    public static void setConfigValue(String key, String value){

        editor.putString(key,value);
        editor.commit();
    }


}
