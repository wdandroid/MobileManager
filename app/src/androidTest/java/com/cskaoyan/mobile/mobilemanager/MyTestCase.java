package com.cskaoyan.mobile.mobilemanager;

import android.test.AndroidTestCase;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Lan on 2016/3/28.
 */
public class MyTestCase extends AndroidTestCase{

    public  void  test(){

          Toast.makeText(getContext(),"ok",Toast.LENGTH_LONG).show();
          Log.i("tag", "tag");
    }
}
