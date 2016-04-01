package com.cskaoyan.mobile.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.cskaoyan.mobile.dao.LockAppDao;
import com.cskaoyan.mobile.mobilemanager.LockAppActivity;

import java.net.URI;
import java.util.List;

public class MyLockAppService extends Service {

    private static final String TAG = "MyLockAppService";
    private ActivityManager ams;
    private LockAppDao dao ;
    private String tempunlockapp="";

    List<String> lockedapplist;

    public MyLockAppService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        //实现一直监听系统。


        ams = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        dao = new LockAppDao(this);

        //动态注册广播接收者
        MyReceiver receiver = new MyReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.cskaoyan.mobilemanager.tempunlock");
        registerReceiver(receiver, filter);

        //
        lockedapplist = dao.getAllLockedApp();

        getContentResolver().registerContentObserver(Uri.parse("content://com.cskaoyan.app"),false,new MyObserver(new Handler()));


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
                    if (lockedapplist.contains(packagename)&&!tempunlockapp.equals(packagename)){

                        final Intent intent1 = new Intent(MyLockAppService.this, LockAppActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //
                        intent1.putExtra("packagename",packagename);
                        startActivity(intent1);
                    }


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


    class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            tempunlockapp = intent.getStringExtra("package");
            Log.i(TAG,"onReceive"+tempunlockapp);
        }
    }

    class MyObserver extends ContentObserver{


        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            lockedapplist = dao.getAllLockedApp();

            Log.i(TAG,"onChange");

        }
    }
}
