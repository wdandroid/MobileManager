package com.cskaoyan.mobile.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug;

import com.cskaoyan.mobile.bean.ProcessInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lan on 2016/4/2.
 */
public class ProcessUtils {


    //当前的进程数
      public static int getRunningProcessCount(Context ctx){

          ActivityManager ams = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);

          final List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ams.getRunningAppProcesses();

          return  runningAppProcesses.size();
      }


    //总ram

    public static long geTotalRam(Context ctx){

        ActivityManager ams = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);


        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ams.getMemoryInfo(memoryInfo);


        return  memoryInfo.totalMem;



    }



    //可用ram

    public static long getAvailableRam(Context ctx){

        ActivityManager ams = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);


        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ams.getMemoryInfo(memoryInfo);


        return  memoryInfo.availMem;



    }


    public static List<ProcessInfo> getAllProcInfo(Context ctx){

        List<ProcessInfo>  processlist = new ArrayList<>();


        ActivityManager ams = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);
        final PackageManager packageManager = ctx.getPackageManager();

        final List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ams.getRunningAppProcesses();


        for (ActivityManager.RunningAppProcessInfo procinfo:runningAppProcesses) {
            // name icon pkg  appram

            try {
                String packagename = procinfo.processName;
                final Drawable icon = packageManager.getApplicationIcon(packagename);
                final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packagename, 0);
                final CharSequence appname = applicationInfo.loadLabel(packageManager);

                int[] pids = new int[]{procinfo.pid};
                final Debug.MemoryInfo[] processMemoryInfo = ams.getProcessMemoryInfo(pids);

                long process_ram =  processMemoryInfo[0].getTotalPss();


                //怎么去判断进程到底是用户进程还是系统进程？
                //如果该应用是系统应用，则他启动的进程就是系统进程
                boolean isSystem=  false;

                if ((applicationInfo.flags&applicationInfo.FLAG_SYSTEM)!=0){
                    isSystem=  true;
                }




                ProcessInfo processInfo = new ProcessInfo(icon,appname.toString(),process_ram,packagename,isSystem);

                processlist.add(processInfo);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }


            // String appname =procinfo.
        }


        return  processlist;
    }
}
