package com.cskaoyan.mobile.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Lan on 2016/3/31.
 */
public class AppInfo {

    String appname;
    Drawable icon;
    boolean isSdcard;  //true 表示装在sdcard false 表示装在ROM中
    boolean isSystem;  //true 表示系统应用， false 表示用户自己安装的应用
    String packagename;

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public AppInfo(String appname, Drawable icon, boolean isSdcard) {
        this.appname = appname;
        this.icon = icon;
        this.isSdcard = isSdcard;
    }

    public AppInfo(String appname, Drawable icon, boolean isSdcard, boolean isSystem) {
        this.appname = appname;
        this.icon = icon;
        this.isSdcard = isSdcard;
        this.isSystem = isSystem;
    }


    public AppInfo(String appname, Drawable icon, boolean isSdcard, boolean isSystem, String packagename) {
        this.appname = appname;
        this.icon = icon;
        this.isSdcard = isSdcard;
        this.isSystem = isSystem;
        this.packagename = packagename;
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
                ", isSystem=" + isSystem +
                '}';
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
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
