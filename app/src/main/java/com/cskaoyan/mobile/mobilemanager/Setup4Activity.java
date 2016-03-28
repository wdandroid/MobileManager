package com.cskaoyan.mobile.mobilemanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cskaoyan.mobile.application.MyApplication;

public class Setup4Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
    }

    public void previous(View v){
        startActivity(new Intent(this,Setup3Activity.class));

    }

    public void next(View v){


        startActivity(new Intent(this,PhoneSafeActivity.class));


    }
}
