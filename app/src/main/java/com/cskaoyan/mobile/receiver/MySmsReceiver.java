package com.cskaoyan.mobile.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.telephony.SmsMessage;
import android.util.Log;

import com.cskaoyan.mobile.application.MyApplication;
import com.cskaoyan.mobile.mobilemanager.R;
import com.cskaoyan.mobile.service.MyUpdateLocationService;

import java.io.IOException;

/**
 * Created by Lan on 2016/3/28.
 */
public class MySmsReceiver extends BroadcastReceiver{
    private static final String TAG ="MySmsReceiver" ;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG,"onReceive ");

        Object[] objs = (Object[]) intent.getExtras().get("pdus");
        for (Object obj : objs) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
            String body = smsMessage.getMessageBody();
            String sender = smsMessage.getOriginatingAddress();

            Log.i(TAG,"onReceive " +body);

            if (body.equals("*#alarm#*")){
                 playalarm(context);
            }
            else if (body.equals("*#location#*")){
                getlocation(context);
            }else if (body.equals("*#wipedata#*")){
                wipedata();
            }else if (body.equals("*#lockscreen#*")){
                lockscreen();
            }
        }

    }

    //激活管理员权限。

    private void lockscreen() {
        Log.i(TAG,"lockscrenn");
    }

    private void wipedata() {
        Log.i(TAG,"wipedata");

    }

    private void getlocation(Context ctx) {
        Log.i(TAG, "getlocation");
        //

        ctx.startService(new Intent(ctx, MyUpdateLocationService.class));


        final String longitude = MyApplication.configsp.getString("longitude", "");

        final String latitude = MyApplication.configsp.getString("latitude", "");

        Log.i("getlocation",longitude+"--"+latitude);

        //通过smsManager 把位置信息发给安全号码。

    }

    private void playalarm(Context ctx) {
        Log.i(TAG,"playalarm");

        MediaPlayer mediaPlayer =   MediaPlayer.create(ctx, R.raw.alarm);
        //让硬件开始播放音频
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }


}
