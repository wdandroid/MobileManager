package com.cskaoyan.mobile.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.RemoteViews;

import com.cskaoyan.mobile.mobilemanager.R;
import com.cskaoyan.mobile.utils.ProcessUtils;

/**
 * Created by Lan on 2016/4/2.
 */
//广播接收者

public class MyAppWidgetProvider extends AppWidgetProvider{

    private static final String TAG ="MyAppWidgetProvider" ;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"onReceive");
        super.onReceive(context, intent);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.i(TAG,"onDeleted");
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        Log.i(TAG,"onDisabled");
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        Log.i(TAG, "onEnabled");


        super.onEnabled(context);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i(TAG,"onUpdate");


        //跨进程的更新

        //告诉系统要去更新那个widget
        ComponentName name = new ComponentName(context,MyAppWidgetProvider.class);

        RemoteViews   remoteview =  new RemoteViews("com.cskaoyan.mobile.mobilemanager", R.layout.processmanager_appwidget);

        final long availableRam = ProcessUtils.getAvailableRam(context);
        remoteview.setTextViewText(R.id.tv_processwidget_memory, "可用内存" + availableRam);

        final int runningProcessCount = ProcessUtils.getRunningProcessCount(context);
        remoteview.setTextViewText(R.id.tv_processwidget_count, "总进程数" + runningProcessCount);


        Intent intent = new Intent("com.cskaoyan.widgetupdate");
        PendingIntent pdintent = PendingIntent.getBroadcast(context,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        remoteview.setOnClickPendingIntent(R.id.btn_widget_clear,pdintent);


        appWidgetManager.updateAppWidget(name,remoteview);






        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


}
