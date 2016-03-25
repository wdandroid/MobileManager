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


    }
}
