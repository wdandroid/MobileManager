package com.cskaoyan.mobile.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

public class MyLockAppService extends Service {

    private static final String TAG = "MyLockAppService";
    private ActivityManager ams;

    public MyLockAppService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //实现一直监听系统。


        ams = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        new Thread(){

            @Override
            public void run() {
                super.run();


                while(true){

                    //检测当前是哪个应用 ，得到该应用的包名。

                    final List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ams.getRunningAppProcesses();
                    final ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcesses.get(0);
                    String packagename =runningAppProcessInfo.processName;

                    Log.i(TAG,packagename);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            }
        }.start();




        return super.onStartCommand(intent, flags, startId);
    }
}
