package com.cskaoyan.mobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cskaoyan.mobile.db.MyBlackNumberDBHelper;
import com.cskaoyan.mobile.db.MyLockAppDBHelper;
import com.cskaoyan.mobile.mobilemanager.TelephoneManagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lan on 2016/4/5.
 */
public class BlackNumberDao {


    private MyBlackNumberDBHelper helper;
    private SQLiteDatabase db;

    public BlackNumberDao(Context ctx) {

        helper= new MyBlackNumberDBHelper(ctx,null,null,1);
        db = helper.getReadableDatabase();

    }
    //增  1 ,2 ,3

    public long insertBlackNumber(String number,int mode){

        if (number==null||number.isEmpty()||mode>3||mode<1){

            return  -1;
        }

        ContentValues cv = new ContentValues();
        cv.put("number",number);
        cv.put("mode", mode);

        final long ret = db.insert("blacknumber", null, cv);


        return  ret;
    }

    //删
    public int deleteBlackNumber (String number){

        final int ret = db.delete("blacknumber", "number = ?", new String[]{number});

        return  ret;
    }



    // 改
    public int updateMode(String number,int mode){

        if (number==null||number.isEmpty()||mode>3||mode<1){

            return  -1;
        }

        ContentValues cv = new ContentValues();
        cv.put("mode", mode);

        final int ret = db.update("blacknumber", cv, "number = ?", new String[]{number});

        return  ret;
    }

    // 查
    public int queryMode(String number){


        final Cursor cursor = db.rawQuery("select mode from blacknumber where number = ?", new String[]{number});

        if (cursor.moveToNext()){

            final int mode = cursor.getInt(0);

            return  mode;
        }

        return  -1;
    }

    //回显所有已经加入到黑名单的数据

    public List<TelephoneManagerActivity.listitem> getallBlacknumber(){

        List<TelephoneManagerActivity.listitem> blacklist = new ArrayList<>();

        final Cursor cursor = db.rawQuery("select * from blacknumber  ", null);

        while (cursor.moveToNext()){

            final String number = cursor.getString(1);
            final int mode = cursor.getInt(2);



            blacklist.add(new TelephoneManagerActivity.listitem(number,mode) );

         }
        return  blacklist;
    }


}
