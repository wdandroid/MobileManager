package com.cskaoyan.mobile.mobilemanager;

import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cskaoyan.mobile.bean.AppCacheInfo;
import com.cskaoyan.mobile.bean.AppInfo;
import com.cskaoyan.mobile.utils.PackageUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClearCacheActivity extends ActionBarActivity {

    private ProgressBar pb_clearcache_scan;
    private TextView tv_clearcache_appname;
    private PackageManager  mPm;

    private List<AppCacheInfo> appcachelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cache);

        pb_clearcache_scan = (ProgressBar) findViewById(R.id.pb_clearcache_scan);
        tv_clearcache_appname = (TextView) findViewById(R.id.tv_clearcache_appname);


        /*  for (int i=0;i<=100;i++){
            pb_clearcache_scan.setProgress(i);

        }*/

        new AsyncTask<Void,Integer,Float>(){


            private List<AppInfo> allAppInfo;
            int count =0;
            @Override
            protected void onPreExecute() {



                allAppInfo = PackageUtils.getAllAppInfo(ClearCacheActivity.this);


                pb_clearcache_scan.setMax(allAppInfo.size());

                mPm = ClearCacheActivity.this.getPackageManager();

                appcachelist= new ArrayList<AppCacheInfo>();

                super.onPreExecute();
            }

            @Override
            protected Float doInBackground(Void... params) {

                //子线程内做耗时操作

                while(count<allAppInfo.size()){


                    //拿到每个应用的缓存信息：
                    //反射
                    //mPm.getPackageSizeInfo(allAppInfo.get(count).getPackagename(), mStatsObserver);

                    try {
                        final Class<?> pmClass = ClearCacheActivity.this.getClassLoader().loadClass("android.content.pm.PackageManager");

                        final Method getPackageSizeInfo = pmClass.getMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);

                        getPackageSizeInfo.invoke(mPm,allAppInfo.get(count).getPackagename(),mStatsObserver);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    publishProgress(++count);

                }

                return null;
            }

            @Override
            protected void onPostExecute(Float aFloat) {

                tv_clearcache_appname.setText("扫描完成！");
                super.onPostExecute(aFloat);
            }


            @Override
            protected void onProgressUpdate(Integer... values) {

                pb_clearcache_scan.setProgress(values[0]);
                super.onProgressUpdate(values);
            }
        }.execute();


    }


    final IPackageStatsObserver.Stub mStatsObserver = new IPackageStatsObserver.Stub() {
        public void onGetStatsCompleted(PackageStats stats, boolean succeeded) {

            final long cacheSize = stats.cacheSize; //字节为单位

            final String packageName = stats.packageName;

            if (cacheSize>12288){

                try {
                    final ApplicationInfo applicationInfo = mPm.getApplicationInfo(packageName, 0);

                    final CharSequence name = applicationInfo.loadLabel(mPm);
                    final Drawable icon = applicationInfo.loadIcon(mPm);

                    AppCacheInfo cacheInfo  = new AppCacheInfo(cacheSize,icon,name.toString());

                    appcachelist.add(cacheInfo);

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }



            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_clearcache_appname.setText(packageName+":"+cacheSize);
                }
            });

        }
    };
}
