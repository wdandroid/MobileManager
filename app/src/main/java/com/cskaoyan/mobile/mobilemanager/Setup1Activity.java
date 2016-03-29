package com.cskaoyan.mobile.mobilemanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

public class Setup1Activity extends SetupBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }


    public void next(View v){
        startActivity(new Intent(this,Setup2Activity.class));


    }

    @Override
    public void previous(View v) {
    }
}
