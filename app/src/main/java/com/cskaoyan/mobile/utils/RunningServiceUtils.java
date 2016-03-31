package com.cskaoyan.mobile.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Lan on 2016/3/31.
 */
public class RunningServiceUtils {

    public static boolean isRunning(Context ctx,String servicename){
        //判断当前的service是否在运行
        //ActivityManager ams
        ActivityManager ams = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> runningServices = ams.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo serviceInfo: runningServices
             ) {

           if (servicename.equals(serviceInfo.service.getClassName()))
               return true;
        }

        return  false;
    }
}
