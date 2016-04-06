package com.cskaoyan.mobile.mobilemanager;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.SlidingDrawer;

import com.cskaoyan.mobile.bean.AppInfo;
import com.cskaoyan.mobile.utils.PackageUtils;

import java.util.List;

public class DataUsageActivity extends ActionBarActivity {

    private static final String TAG = "DataUsageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_usage);

        TrafficStats trafficStats = new TrafficStats();

        //Tx  transmit  上行 :  Rx Receive  下行

        final long mobileTxBytes = trafficStats.getMobileTxBytes();
        final long mobileRxBytes = trafficStats.getMobileRxBytes();

        Log.i(TAG," mobileTxBytes="+ Formatter.formatFileSize(this,mobileTxBytes));
        Log.i(TAG, " mobileRxBytes=" + Formatter.formatFileSize(this, mobileRxBytes));


        final PackageManager packageManager = getPackageManager();
        final List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(0);

        for (ApplicationInfo app:installedApplications) {


            final long uidRxBytes = trafficStats.getUidRxBytes(app.uid);
            final long uidTxBytes = trafficStats.getUidTxBytes(app.uid);
            trafficStats.getUidRxPackets(app.uid);
            Log.i(TAG, app.packageName+ " uidRxBytes="+ Formatter.formatFileSize(this,uidRxBytes));
            Log.i(TAG, app.packageName+ " uidTxBytes=" + Formatter.formatFileSize(this, uidTxBytes));


        }



    }
}
