package com.cskaoyan.mobile.mobilemanager;

import android.test.AndroidTestCase;
import android.util.Log;

import com.cskaoyan.mobile.bean.ProcessInfo;
import com.cskaoyan.mobile.utils.ProcessUtils;

import java.util.List;

/**
 * Created by Lan on 2016/4/2.
 */
public class ProcessTest extends AndroidTestCase{

   public void testGetCount(){


       final int runningProcessCount = ProcessUtils.getRunningProcessCount(getContext());
       Log.i("runningProcessCount",runningProcessCount+"");

       final Long availableRam = ProcessUtils.getAvailableRam(getContext());
       Log.i("availableRam",availableRam+"");

       final Long totalRam = ProcessUtils.geTotalRam(getContext());
       Log.i("totalRam",totalRam+"");

   }


    public void testGetAvail(){


        final Long availableRam = ProcessUtils.getAvailableRam(getContext());
        Log.i("availableRam",availableRam+"");


    }


    public void testGetTotal(){


        final Long totalRam = ProcessUtils.geTotalRam(getContext());
        Log.i("totalRam",totalRam+"");


    }


    public void testGetProcInfo(){


        final List<ProcessInfo> allProcInfo = ProcessUtils.getAllProcInfo(getContext());

        for (ProcessInfo pp:allProcInfo
             ) {
            Log.i("testGetProcInfo",pp.toString()+"");

        }


    }


}
