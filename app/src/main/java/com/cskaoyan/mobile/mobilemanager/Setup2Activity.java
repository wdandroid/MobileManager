package com.cskaoyan.mobile.mobilemanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.cskaoyan.mobile.application.MyApplication;
import com.cskaoyan.mobile.view.SettingItem;

public class Setup2Activity extends SetupBaseActivity {

    private String Tag="Setup2Activity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        overridePendingTransition( R.anim.slideinleft,  R.anim.slideoutright);
    }

    public  void next(View v){
        
        String imsi = MyApplication.configsp.getString("imsi", "");
        
        if (!imsi.isEmpty()){
            startActivity(new Intent(this, Setup3Activity.class));
            overridePendingTransition(R.anim.slideinright,  R.anim.slideoutleft);
        }
        else
            Toast.makeText(Setup2Activity.this, "请绑定sim卡！否则无法使用本功能!", Toast.LENGTH_SHORT).show();
    }
}
