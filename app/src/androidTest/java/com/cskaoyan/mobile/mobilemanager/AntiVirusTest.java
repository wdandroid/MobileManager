package com.cskaoyan.mobile.mobilemanager;

import android.test.AndroidTestCase;
import android.util.Log;

import com.cskaoyan.mobile.dao.AntiVirusDao;
import com.cskaoyan.mobile.utils.Md5Utils;

/**
 * Created by Lan on 2016/4/4.
 */
public class AntiVirusTest extends AndroidTestCase{

    public void testIsVirus(){
       assertFalse(AntiVirusDao.isVirusApp(getContext(), "30f8c5d2cc445273e959b2a49fc8e9"));

    }


    public void testApkMd5(){


        String md5=   Md5Utils.getAppMd5Digest("/storage/sdcard/moblie.apk");
        Log.i("testApkMd5",md5);
    }
}
