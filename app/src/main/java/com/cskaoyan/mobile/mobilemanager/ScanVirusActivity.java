package com.cskaoyan.mobile.mobilemanager;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cskaoyan.mobile.bean.AppInfo;
import com.cskaoyan.mobile.dao.AntiVirusDao;
import com.cskaoyan.mobile.utils.Md5Utils;
import com.cskaoyan.mobile.utils.PackageUtils;

import java.util.ArrayList;
import java.util.List;

public class ScanVirusActivity extends ActionBarActivity {

    private ImageView iv_scanvirus_scan;
    private ProgressBar pb_scanvirus_scan;
    private TextView tv_scanvirus_status;
    private RotateAnimation animation;
    private ListView lv_scanvirus_applist;


    class appResult {

       public String name;
        public boolean isVirus;

        public appResult(boolean isVirus, String name) {
            this.isVirus = isVirus;
            this.name = name;
        }

        public boolean isVirus() {
            return isVirus;
        }

        public void setIsVirus(boolean isVirus) {
            this.isVirus = isVirus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    private List<appResult>  apppackagelist;
    private MyVirusAdapter myVirusAdapter;

    private PackageManager mPm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_virus);

        iv_scanvirus_scan = (ImageView) findViewById(R.id.iv_scanvirus_scan);
        pb_scanvirus_scan = (ProgressBar) findViewById(R.id.pb_scanvirus_scan);
        tv_scanvirus_status = (TextView) findViewById(R.id.tv_scanvirus_status);

        lv_scanvirus_applist = (ListView) findViewById(R.id.lv_scanvirus_applist);

        apppackagelist= new ArrayList<>();
        myVirusAdapter = new MyVirusAdapter();
        lv_scanvirus_applist.setAdapter(myVirusAdapter);

        mPm =getPackageManager();

        startscan();

        new AsyncTask<Void,Integer,Void>(){

            private List<AppInfo> allAppInfo;
            int count = 0;
            @Override
            protected void onPreExecute() {

                allAppInfo = PackageUtils.getAllAppInfo(ScanVirusActivity.this);

                pb_scanvirus_scan.setMax(allAppInfo.size() );
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {



                boolean isVirus=false;

                while(count<allAppInfo.size()){

                  /*  try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/

                    Log.i("scanvirus",allAppInfo.get(count).toString());


                    //去后去该应用apk的位置
                    try {

                        final ApplicationInfo applicationInfo = mPm.getApplicationInfo(allAppInfo.get(count).getPackagename(), 0);
                        String apkpath= applicationInfo. sourceDir;
                        Log.i("scanvirus","sourceDir   is  " +apkpath);

                        String apkmd5 = Md5Utils.getAppMd5Digest(apkpath);


                          isVirus=  AntiVirusDao.isVirusApp(ScanVirusActivity.this,apkmd5);
                        Log.i("scanvirus","this apk   is  virus ? " +isVirus);


                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }


                    if (allAppInfo.get(count).getPackagename().isEmpty()){
                        Log.i("scanvirus","package name is emput");
                        continue;
                    }
                    apppackagelist.add(0,new appResult( isVirus,allAppInfo.get(count).getPackagename()));

                    publishProgress(++count);

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                tv_scanvirus_status.setText("扫描完成");
                animation.cancel();

                super.onPostExecute(aVoid);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {

                pb_scanvirus_scan.setProgress(values[0]);
                myVirusAdapter.notifyDataSetChanged();
                super.onProgressUpdate(values);
            }
        }.execute();


    }



    class MyVirusAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return apppackagelist.size();
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
            TextView tv = new TextView(ScanVirusActivity.this);

            final appResult appResult = apppackagelist.get(position);
            tv.setText(appResult.name);

            if (appResult.isVirus){
              tv.setTextColor(Color.RED);
            }

            return tv;
        }
    }

    private void startscan() {
        animation = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(3000);
        animation.setRepeatCount(-1);
        iv_scanvirus_scan.setAnimation(animation);

        animation.start();
    }
}
