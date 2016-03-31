package com.cskaoyan.mobile.utils;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Lan on 2016/3/31.
 */
public abstract  class MyAsyncTast {


    public  void preBackgroud(){};


    public abstract void doinBackgroud();

/*    {

        //耗时操作在子线程。
    }*/


    public abstract void doafterbackgroud();

/*    {

        //耗时操作完成之后才能执行的操作。
    }*/


    public void execute(){


         preBackgroud();
         new Thread(){

             @Override
             public void run() {
                 super.run();

                   doinBackgroud();
                 Message msg= myHandler.obtainMessage();
                 msg.what=1;
                  myHandler.sendMessage(msg);
             }
         }.start();


    }


    Handler myHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            if (msg.what==1)
                doafterbackgroud();

            super.handleMessage(msg);
        }
    };
}
