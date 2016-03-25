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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cskaoyan.mobile.application.MyApplication;
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

    private String current_version;
    private TextView tv_splash_version;
    private ProgressBar pb_splash_download;

    //
    private static final  int MSG_OK =1;
    private static final  int MSG_ERROR_INTERSEVER =-1;
    private static final  int MSG_ERROR_URL =-2;
    private static final  int MSG_ERROR_IO =-3;
    private static final  int MSG_ERROR_JSON =-4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
        pb_splash_download = (ProgressBar) findViewById(R.id.pb_splash_download);


        current_version = getVersionName();
        tv_splash_version.setText("Version : "+current_version);

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


                case MSG_ERROR_INTERSEVER :
                    Toast.makeText(SplashActivity.this,MSG_ERROR_INTERSEVER+"",Toast.LENGTH_LONG).show();
                    enterHome();
                    break;
                case MSG_ERROR_IO :
                    Toast.makeText(SplashActivity.this,MSG_ERROR_IO+"",Toast.LENGTH_LONG).show();
                    enterHome();
                    break;
                case MSG_ERROR_JSON :
                    Toast.makeText(SplashActivity.this,MSG_ERROR_JSON+"",Toast.LENGTH_LONG).show();
                    enterHome();
                    break;
                case MSG_ERROR_URL :
                    Toast.makeText(SplashActivity.this,MSG_ERROR_URL+"",Toast.LENGTH_LONG).show();
                    enterHome();
                    break;
            }
        }
    };

    public void update(final String[] info){

        //
        Log.i(TAG, "update");

        new AlertDialog.Builder(this)
                .setTitle("发现新版本")
                .setMessage(info[1])
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //download

                        AsyncHttpClient client = new AsyncHttpClient();
                        client.get(MyApplication.SERVER_PATH+info[2], new MyAsyncHttpHandler() );
                        pb_splash_download.setVisibility(View.VISIBLE);
                       // client.get


                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //进入到主页面
                        enterHome();
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
                //需要进入主页面
                enterHome();

            } catch (IOException e) {
                e.printStackTrace();
                enterHome();

            }

        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            Toast.makeText(SplashActivity.this,"下载失败",Toast.LENGTH_LONG).show();
            //进入到主页面
            enterHome();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
            pb_splash_download.setMax((int)totalSize);
            pb_splash_download.setProgress((int)bytesWritten);
        }
    }

    private void install(File f){
        //思路比较简单，和调用系统其他app类似
        Intent intent =new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(f), "application/vnd.android.package-archive");
        startActivityForResult(intent, 100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_CANCELED){
            //进入主页面
            enterHome();
        }


    }

    private void enterHome() {
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }

    private void getNewVersion(){

        new Thread(){

            @Override
            public void run() {
                super.run();

                String path =MyApplication.SERVER_PATH+"/version.json";
                Message msg = myhandler.obtainMessage();

                try {
                    URL url = new URL(path);
                    HttpURLConnection  conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(2000);
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
                        msg.what=MSG_OK;
                        msg.obj=newversioninfo;

                    }else {

                        if (ret==500){
                            msg.what=MSG_ERROR_INTERSEVER;
                        }

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    msg.what=MSG_ERROR_URL;

                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what=MSG_ERROR_IO;

                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.what=MSG_ERROR_JSON;

                }finally {
                    myhandler.sendMessage(msg);
                }


            }
        }.start();


    }

}
