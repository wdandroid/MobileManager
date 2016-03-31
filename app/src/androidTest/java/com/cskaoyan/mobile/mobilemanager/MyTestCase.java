package com.cskaoyan.mobile.mobilemanager;

import android.test.AndroidTestCase;
import android.util.Log;
import android.widget.Toast;

import com.cskaoyan.mobile.bean.AppInfo;
import com.cskaoyan.mobile.utils.PackageUtils;
import com.cskaoyan.mobile.utils.RunningServiceUtils;

import java.util.List;

/**
 * Created by Lan on 2016/3/28.
 */
public class MyTestCase extends AndroidTestCase{

    public  void  test(){

          Toast.makeText(getContext(),"ok",Toast.LENGTH_LONG).show();
          Log.i("tag", "tag");
    }

    public void testRunning(){

        final boolean running = RunningServiceUtils.isRunning(getContext(), "com.cskaoyan.mobile.service.MyNumberLocationService");

        assertFalse(running);
    }

    public void testStatFS(){

       Long size = PackageUtils.getAvailableSDcardSize();
       Log.i("tag",size+"");
    }

    public void testStatFS2(){

        Long size = PackageUtils.getAvailableROMSize();
        Log.i("tag",size+"");
    }


    public void testgetAppinfolist(){

        final List<AppInfo> allAppInfo = PackageUtils.getAllAppInfo(getContext());

        for (AppInfo app:allAppInfo) {
            Log.i("tag",app.toString());

        }
    }
}
