package com.cskaoyan.mobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lan on 2016/4/1.
 */
public class MyLockAppDBHelper extends SQLiteOpenHelper {


     public MyLockAppDBHelper(Context ctx,String name,SQLiteDatabase.CursorFactory factory,int ver){
         super(ctx,name,factory,ver);
     }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //建表
        db.execSQL("create table lockapp( _id integer primary key autoincrement, packagename varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
