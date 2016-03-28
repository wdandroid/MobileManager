package com.cskaoyan.mobile.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.cskaoyan.mobile.application.MyApplication;

/**
 * Created by Lan on 2016/3/28.
 */
public class MyBootCompleteReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("MyBootCompleteReceiver",  intent.getAction());

        final boolean anti_theif  = MyApplication.configsp.getBoolean("anti_theif", true);

        if(anti_theif) {

            final String imsi_saved = MyApplication.configsp.getString("imsi", "");

            //如何判断两个sim卡不一样
            TelephonyManager mTelmanager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);

            String imsi_current = mTelmanager.getSimSerialNumber(); //IMSI


            Log.i("MyBootCompleteReceiver", imsi_current + "----" + imsi_saved);

            if (!imsi_saved.equals(imsi_current)){


                SmsManager smsManager = SmsManager.getDefault();

                final String  safenum  = MyApplication.configsp.getString("safenum", "");

                smsManager.sendTextMessage("5556",null,"你的手机被换卡了",null,null);


            }

        }
    }
}
