package com.cskaoyan.mobile.mobilemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cskaoyan.mobile.utils.HTTPUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
 import java.util.logging.LogRecord;

public class SplashActivity extends ActionBarActivity {

    private static final String TAG = "SplashActivity";

    private static final  int MSG_OK =1;
    private String current_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        current_version = getVersionName();
        getNewVersion();


    }



    private String getVersionName(){

        String versionName ="";

        //管理当前手机的应用
        PackageManager manager=getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(), 0);
             versionName = packageInfo.versionName;
             int versionCode=    packageInfo.versionCode;
           // Log.i(TAG,"version"+versionName +versionCode);
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //can't reach
        }


         return  versionName;

    }



    Handler myhandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case MSG_OK:

                   String[] info = (String[]) msg.obj;

                   String version= info[0];
                   String newVersiondescription =info[1];
                   String downurl =info[2];

                   float newver= Float.parseFloat(version);
                    float currver = Float.parseFloat(current_version);
                    if (newver>currver){

                         update(info);

                    }

                    break;
            }
        }
    };

    public void update(final String[] info){

        //
        Log.i(TAG,"update");

        new AlertDialog.Builder(this)
                .setTitle("发现新版本")
                .setMessage(info[1])
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //download

                        AsyncHttpClient client = new AsyncHttpClient();
                        client.get("http://192.168.3.36/MobileManager"+info[2], new MyAsyncHttpHandler() );

                       // client.get


                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //进入到主页面

                    }
                })
                .show();



    }


    class MyAsyncHttpHandler extends AsyncHttpResponseHandler{


        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/moblie.apk");

            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.close();

                install(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            Toast.makeText(SplashActivity.this,"下载失败",Toast.LENGTH_LONG).show();
            //进入到主页面

        }
    }

    private void install(File f){
        //思路比较简单，和调用系统其他app类似
        Intent intent =new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(f), "application/vnd.android.package-archive");
        startActivity(intent);
    }


    private void getNewVersion(){

        new Thread(){

            @Override
            public void run() {
                super.run();

                String path ="http://192.168.3.36/MobileManager/version.json";
                try {
                    URL url = new URL(path);
                    HttpURLConnection  conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);

                    conn.connect();

                    int ret =conn.getResponseCode();

                    //进行json解析
                    if (ret==200){

                      InputStream is= conn.getInputStream();
                      String  text=  HTTPUtils.getTextFromStream(is);


                        JSONObject obj = new JSONObject(text) ;

                        String newVersion  = obj.getString("version");
                        String downlaodurl  =   obj.getString("download_url");
                        String newVersiondescription =obj.getString("description");
                        String[] newversioninfo = {newVersion,newVersiondescription,downlaodurl};

                        Log.i(TAG,newVersion+":"+downlaodurl);
                        Message msg = myhandler.obtainMessage();
                        msg.what=MSG_OK;
                        msg.obj=newversioninfo;
                        myhandler.sendMessage(msg);

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }.start();


    }

}
