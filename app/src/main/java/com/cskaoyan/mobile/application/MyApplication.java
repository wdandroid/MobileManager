package com.cskaoyan.mobile.application;

import android.app.Application;

/**
 * Created by Lan on 2016/3/25.
 */
public class MyApplication extends Application{


    public static  String SERVER_PATH  ;
    @Override
    public void onCreate() {
        super.onCreate();

         SERVER_PATH="http://192.168.3.34/MobileManager";

    }
}
