package com.cskaoyan.mobile.mobilemanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

//当做设置详情页面
//判断用户是否已经设置过，如果没有，则进入一个设置向导页面
public class PhoneSafeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_safe);



        //如何判断是否已经设置过：通过设置向导会生成一个绑定sim卡信息，如果该信息存在则视为已经设置过
        startActivity(new Intent(this,Setup1Activity.class));


    }
}
