package com.cskaoyan.mobile.mobilemanager;

import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cskaoyan.mobile.bean.AppCacheInfo;
import com.cskaoyan.mobile.bean.AppInfo;
import com.cskaoyan.mobile.utils.PackageUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class ClearCacheActivity extends ActionBarActivity {

    private ProgressBar pb_clearcache_scan;
    private TextView tv_clearcache_appname;
    private PackageManager  mPm;

    private List<AppCacheInfo> appcachelist;
    private ListView lv_clearcache_cachelist;
    private Button bt_clearcache_clear;
    private MyAdpater adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cache);

        pb_clearcache_scan = (ProgressBar) findViewById(R.id.pb_clearcache_scan);
        tv_clearcache_appname = (TextView) findViewById(R.id.tv_clearcache_appname);
        lv_clearcache_cachelist = (ListView) findViewById(R.id.lv_clearcache_cachelist);
        bt_clearcache_clear =
                (Button) findViewById(R.id.bt_clearcache_clear);


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

                if (appcachelist.size()==0){

                    //没有缓存可清理的case
                }else{
                    adpater = new MyAdpater();
                    lv_clearcache_cachelist.setAdapter(adpater);
                    bt_clearcache_clear.setVisibility(View.VISIBLE);
                    super.onPostExecute(aFloat);
                }

            }


            @Override
            protected void onProgressUpdate(Integer... values) {

                pb_clearcache_scan.setProgress(values[0]);
                pb_clearcache_scan.setSecondaryProgress(values[0]*2);
                super.onProgressUpdate(values);
            }
        }.execute();


    }


    class MyAdpater extends BaseAdapter{


        @Override
        public int getCount() {
            return appcachelist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final View inflate = View.inflate(ClearCacheActivity.this, R.layout.item_cachelist,null);

            final ImageView  iv_cachelist_icon = (ImageView) inflate.findViewById(R.id.iv_cachelist_icon);
            final TextView  tv_cachelist_appname = (TextView) inflate.findViewById(R.id.tv_cachelist_appname);
            final TextView  tv_cachelist_appcache = (TextView) inflate.findViewById(R.id.tv_cachelist_appcache);

            final AppCacheInfo cacheInfo = appcachelist.get(position);

            iv_cachelist_icon.setImageDrawable(cacheInfo.getIcon());
            tv_cachelist_appname.setText(cacheInfo.getName());
            tv_cachelist_appcache.setText(Formatter.formatFileSize(ClearCacheActivity.this, cacheInfo.getSize())  );

            return inflate;
        }
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


    public void clearcache(View v){
        //清除所有缓存

        // 想系统申请空间，申请data storage
        // public abstract void freeStorageAndNotify(long freeStorageSize,IPackageDataObserver observer);
        // 系统把所有缓存清空之后，会call到 callback
        Class<?> pmClass = null;
        try {
            pmClass = ClearCacheActivity.class.getClassLoader()
                    .loadClass("android.content.pm.PackageManager");

            Method declaredMethod = pmClass.getDeclaredMethod(
                    "freeStorageAndNotify", Long.TYPE,
                    IPackageDataObserver.class);

            declaredMethod.invoke(mPm, Long.MAX_VALUE, new MyIPackageDataObserver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    private class MyIPackageDataObserver extends IPackageDataObserver.Stub {
        // 在子线程中回调
        @Override
        public void onRemoveCompleted(String packageName, boolean succeeded)
                throws RemoteException {
           // 清理缓存完成
            appcachelist.clear();
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    adpater.notifyDataSetChanged();
                }
            });
        }

    }



}
