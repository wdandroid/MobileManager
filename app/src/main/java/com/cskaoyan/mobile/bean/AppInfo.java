package com.cskaoyan.mobile.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Lan on 2016/3/31.
 */
public class AppInfo {

    String appname;
    Drawable icon;
    boolean isSdcard;  //true 表示装在sdcard false 表示装在ROM中


    public AppInfo(String appname, Drawable icon, boolean isSdcard) {
        this.appname = appname;
        this.icon = icon;
        this.isSdcard = isSdcard;
    }

    public String getAppname() {

        return appname;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appname='" + appname + '\'' +
                ", icon=" + icon +
                ", isSdcard=" + isSdcard +
                '}';
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isSdcard() {
        return isSdcard;
    }

    public void setIsSdcard(boolean isSdcard) {
        this.isSdcard = isSdcard;
    }
}
