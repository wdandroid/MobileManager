package com.cskaoyan.mobile.mobilemanager;

import android.test.AndroidTestCase;
import android.util.Log;
import android.widget.Toast;

import com.cskaoyan.mobile.dao.NumberLoactionDao;

/**
 * Created by Lan on 2016/3/29.
 */
public class MyAndroidTestCase extends AndroidTestCase{




    //test开头
    public void testLo(){
         Log.i("Tag","123");
    }

    //测试本地离线号码归属地查询API是否OK


    public void testQuery(){


        final String numberLocation = NumberLoactionDao.getNumberLocation("13688889272", getContext());

        Toast.makeText(getContext(), numberLocation, Toast.LENGTH_SHORT).show();

        Log.i("TAG",numberLocation);
    }


}
