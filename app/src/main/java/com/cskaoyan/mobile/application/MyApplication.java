package com.cskaoyan.mobile.application;

import android.app.ActivityManager;
import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.cskaoyan.mobile.bean.ProcessInfo;
import com.cskaoyan.mobile.mobilemanager.R;
import com.cskaoyan.mobile.service.MyNumberLocationService;
import com.cskaoyan.mobile.utils.ProcessUtils;
import com.cskaoyan.mobile.widget.MyAppWidgetProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lan on 2016/3/25.
 */
public class MyApplication extends Application{


    private static final String TAG = "MyApplication";
    public static  String SERVER_PATH  ;
    public static  SharedPreferences configsp;
    public static  SharedPreferences.Editor editor;
    private MyReveiver myReveiver;

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter("com.cskaoyan.widgetupdate");
        myReveiver = new MyReveiver();
        getApplicationContext().registerReceiver(myReveiver, filter);

         SERVER_PATH="http://192.168.3.34/MobileManager";

         configsp=getSharedPreferences("config", MODE_PRIVATE);

         editor =configsp.edit();


        if(configsp.getBoolean("showloaction",false))
            startService(new Intent(this, MyNumberLocationService.class));
    }


    public static void setConfigValue(String key, String value){

        editor.putString(key,value);
        editor.commit();
    }


    public static void setConfigValue(String key, int value){

        editor.putInt(key,value);
        editor.commit();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        getApplicationContext().unregisterReceiver(myReveiver);

    }



    class MyReveiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


            Log.i(TAG, "Myreceiver onreceive" + intent.getAction());


            //第一步，先kill后台进程
            final ActivityManager ams = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

            List<ProcessInfo> userprocessInfoList = ProcessUtils.getAllProcInfo(getApplicationContext());
            for (ProcessInfo pp : userprocessInfoList) {
                        if (pp.getPackagename().equals("com.cskaoyan.mobile.mobilemanager"))
                            continue;
                        ams.killBackgroundProcesses(pp.getPackagename());
                        Log.i(TAG,"kill"+pp.getPackagename());
            }

            //第二部，更新widget
            final AppWidgetManager instance = AppWidgetManager.getInstance(context);
            ComponentName name = new ComponentName(context,MyAppWidgetProvider.class);
            RemoteViews remoteview =  new RemoteViews("com.cskaoyan.mobile.mobilemanager", R.layout.processmanager_appwidget);

            final long availableRam = ProcessUtils.getAvailableRam(context);
            remoteview.setTextViewText(R.id.tv_processwidget_memory, "可用内存" + availableRam);

            final int runningProcessCount = ProcessUtils.getRunningProcessCount(context);
            remoteview.setTextViewText(R.id.tv_processwidget_count, "总进程数" + runningProcessCount);

            instance.updateAppWidget(name, remoteview);


        }
    }
}
