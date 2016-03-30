package com.cskaoyan.mobile.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.cskaoyan.mobile.application.MyApplication;
import com.cskaoyan.mobile.dao.NumberLoactionDao;
import com.cskaoyan.mobile.mobilemanager.R;

public class MyNumberLocationService extends Service {
    private static final String TAG = "MyNumberLocationService";
    private WindowManager mWM;
    private View v;

    public MyNumberLocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {

        final TelephonyManager telmar = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        telmar.listen(new MyPhoneStateListner(),PhoneStateListener.LISTEN_CALL_STATE);

        Log.i(TAG,"onCreate");

        super.onCreate();
    }


    class MyPhoneStateListner extends PhoneStateListener{

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            switch (state){
                case  TelephonyManager.CALL_STATE_IDLE:

                    hideLocationVIew();
                    break;
                case  TelephonyManager.CALL_STATE_RINGING:

                    String location =NumberLoactionDao.getNumberLocation(incomingNumber,MyNumberLocationService.this);
                    Log.i(TAG,location);
                    //第二部，我们需要把这个号码显示出来。
//                    Toast.makeText(MyNumberLocationService.this, location, Toast.LENGTH_SHORT).show();

                    showLocationView(location);
                    break;
                case  TelephonyManager.CALL_STATE_OFFHOOK:

                    break;
            }

        }
    }

    private void showLocationView(String location) {


        LayoutInflater inflate = (LayoutInflater)
                  getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflate.inflate(R.layout.mynumberlocation, null);
        v.setBackgroundResource(R.drawable.call_locate_blue);


        TextView message = (TextView) v.findViewById(R.id.message);
        message.setText(location);

        mWM = (WindowManager)getSystemService(Context.WINDOW_SERVICE);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();;

        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;


        params.gravity= Gravity.LEFT|Gravity.TOP;



        params.x=           MyApplication.configsp.getInt("toastx",200);
        params.y=           MyApplication.configsp.getInt("toasty",300)+50;


//        params.windowAnimations = com.android.internal.R.style.Animation_Toast;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;


        mWM.addView(v, params);

    }

    private void hideLocationVIew() {

        if (mWM!=null){

            mWM.removeView(v);

        }
    }
}
