package com.cskaoyan.mobile.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.cskaoyan.mobile.dao.BlackNumberDao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BlackNumberService extends Service {

    private TelephonyManager mTlemanager;
    private static final String TAG ="BlackNumberService" ;
    private BlackNumberDao dao;

    public BlackNumberService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //去监听来电状态

        dao = new BlackNumberDao(this);
        mTlemanager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        mTlemanager.listen(new MyPhoneStateListener(),PhoneStateListener.LISTEN_CALL_STATE);


        //动态注册拦截的短信广播

        final MyBlackNumberSmsReceiver myBlackNumberSmsReceiver = new MyBlackNumberSmsReceiver();

        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(Integer.MAX_VALUE); //1000 -1000
        registerReceiver(myBlackNumberSmsReceiver, filter);

        return super.onStartCommand(intent, flags, startId);
    }


    class MyPhoneStateListener extends PhoneStateListener{



        @Override
        public void onCallStateChanged(int state, String incomingNumber) {


            switch (state){

                case TelephonyManager.CALL_STATE_RINGING:

                    Log.i(TAG, incomingNumber);

                    final int mode = dao.queryMode(incomingNumber);

                    if (mode==1||mode==3){
                        endCall();
                    }

                    break;

            }

            super.onCallStateChanged(state, incomingNumber);
        }
    }




    private void endCall() {

        Log.i(TAG, "需要拦截电话");

//           mTlemanager.endCall();
//           ITelephony.Stub.asInterface(ServiceManager.getService(Context.TELEPHONY_SERVICE));
//           ServiceManager.getService(Context.TELEPHONY_SERVICE)
//           ServiceManager.gets

        try {
            final Class<?> aClass = getClassLoader().loadClass("android.os.ServiceManager");

            final Method getService = aClass.getMethod("getService", String.class);

            final IBinder invoke = (IBinder) getService.invoke(null, Context.TELEPHONY_SERVICE);

            final ITelephony iTelephony = ITelephony.Stub.asInterface(invoke);

            iTelephony.endCall();


        } catch (ClassNotFoundException e) {
                e.printStackTrace();
        } catch (NoSuchMethodException e) {
                e.printStackTrace();
        } catch (InvocationTargetException e) {
                e.printStackTrace();
        } catch (IllegalAccessException e) {
                e.printStackTrace();
        } catch (RemoteException e) {
                e.printStackTrace();
        }


    }



    class MyBlackNumberSmsReceiver extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i(TAG,"onReceive ");

            Object[] objs = (Object[]) intent.getExtras().get("pdus");
            for (Object obj : objs) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
                String sender = smsMessage.getOriginatingAddress();

                Log.i(TAG, "onReceive " + sender);

                final int mode = dao.queryMode(sender);
                if (mode==1||mode==3){
                    abortBroadcast();//拦截有序广播
                    Log.i(TAG, "onReceive " + "拦截该短信");
                }


            }


        }
    }
}
