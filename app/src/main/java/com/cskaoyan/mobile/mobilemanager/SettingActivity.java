package com.cskaoyan.mobile.mobilemanager;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cskaoyan.mobile.application.MyApplication;

public class SettingActivity extends ActionBarActivity {

    private static final String TAG ="SettingActivity" ;
    private CheckBox cb_setting_update;
    private TextView tv_setting_updatestatus;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sp= MyApplication.configsp;
        editor =sp.edit();

        //final ActionBar actionBar = getActionBar();
        final android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();

        //
        cb_setting_update = (CheckBox) findViewById(R.id.cb_setting_update);
        tv_setting_updatestatus = (TextView) findViewById(R.id.tv_setting_updatestatus);

        if(MyApplication.configsp.getBoolean("autoupdate",true)){
            tv_setting_updatestatus.setText("开启自动更新");
            cb_setting_update.setChecked(true);

        }
        else{
            tv_setting_updatestatus.setText("取消自动更新");
            cb_setting_update.setChecked(false);

        }


        cb_setting_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取的是click之后的一个选中状态
                boolean checked = cb_setting_update.isChecked();
                Log.i(TAG,checked+"");
                if (checked){

                    //cb_setting_update.setChecked(true);
                    tv_setting_updatestatus.setText("开启自动更新");
                    Log.i(TAG, checked + "开启");
                    editor.putBoolean("autoupdate", true);
                    editor.commit();


                }
                else {
                    //cb_setting_update.setChecked(false);
                    tv_setting_updatestatus.setText("取消自动更新");
                    Log.i(TAG, checked + "取消");
                    editor.putBoolean("autoupdate",false);
                    editor.commit();

                }
            }
        });
    }
}
