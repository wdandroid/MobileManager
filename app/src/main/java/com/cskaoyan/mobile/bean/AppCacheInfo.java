package com.cskaoyan.mobile.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Lan on 2016/4/4.
 */
public class AppCacheInfo {

    String name ;
    Drawable icon;
    long size;

    public AppCacheInfo(long size, Drawable icon, String name) {
        this.size = size;
        this.icon = icon;
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
