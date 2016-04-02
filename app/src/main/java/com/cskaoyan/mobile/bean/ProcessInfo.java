package com.cskaoyan.mobile.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Lan on 2016/4/2.
 */
public class ProcessInfo {


    String appname;
    Drawable appicon;
    long appram;
    String packagename;
    boolean isCheck;

    public boolean isSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    boolean isSystem;

    public ProcessInfo(Drawable appicon, String appname, long appram, String packagename) {
        this.appicon = appicon;
        this.appname = appname;
        this.appram = appram;
        this.packagename = packagename;
        isCheck=false;
    }

    public ProcessInfo(Drawable appicon, String appname, long appram, String packagename,boolean issys) {
        this.appicon = appicon;
        this.appname = appname;
        this.appram = appram;
        this.packagename = packagename;
        isCheck=false;
        isSystem=issys;
    }

    public Drawable getAppicon() {
        return appicon;
    }

    public void setAppicon(Drawable appicon) {
        this.appicon = appicon;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public long getAppram() {
        return appram;
    }

    public void setAppram(long appram) {
        this.appram = appram;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public String toString() {
        return "ProcessInfo{" +
                "appicon=" + appicon +
                ", appname='" + appname + '\'' +
                ", appram=" + appram +
                ", packagename='" + packagename + '\'' +
                '}';
    }
}
