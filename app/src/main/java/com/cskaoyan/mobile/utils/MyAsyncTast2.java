package com.cskaoyan.mobile.utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Lan on 2016/3/31.
 */



public    class MyAsyncTast2 extends AsyncTask<URL, Integer, Float>{


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Float doInBackground(URL...  params) {

        return null;
    }

    @Override
    protected void onPostExecute(Float aFloat) {
        super.onPostExecute(aFloat);
    }
}
