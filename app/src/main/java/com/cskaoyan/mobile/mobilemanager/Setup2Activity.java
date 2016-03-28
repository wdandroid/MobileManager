package com.cskaoyan.mobile.mobilemanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cskaoyan.mobile.application.MyApplication;
import com.cskaoyan.mobile.view.SettingItem;

public class Setup2Activity extends ActionBarActivity {

    private String Tag="Setup2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        SettingItem  si_setup2_bindsim = (SettingItem) findViewById(R.id.si_setup2_bindsim);


        si_setup2_bindsim.setMyOnclickListener(new SettingItem.MyOnclickListen() {
            @Override
            public void myCheckOnclick() {
                Log.i(Tag, "myonclick executed");
                //绑定SIM卡的业务逻辑
                //如何判断两个sim卡不一样
                TelephonyManager mTelmanager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

                String imsi = mTelmanager.getSimSerialNumber(); //IMSI

                //Toast.makeText(Setup2Activity.this, ims, Toast.LENGTH_SHORT).show();

                MyApplication.setConfigValue("imsi", imsi);
            }

            @Override
            public void myCancleOnclick() {
                MyApplication.setConfigValue("imsi", "");

            }


        });
    }


    public  void previous(View v){

        startActivity(new Intent(this,Setup1Activity.class));
    }

    public  void next(View v){
        startActivity(new Intent(this,Setup3Activity.class));

    }
}
