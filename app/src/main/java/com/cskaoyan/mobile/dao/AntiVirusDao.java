package com.cskaoyan.mobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Lan on 2016/4/4.
 */
public class AntiVirusDao {


    public static boolean isVirusApp(Context ctx, String appMD5){


        boolean isVirus =false;


        SQLiteDatabase db =  SQLiteDatabase.openDatabase("data/data/" + ctx.getPackageName() + "/antivirus.db", null,0);


        Cursor cursor= db.rawQuery("  select * from datable where md5 =  ?" , new String[]{appMD5});


        while(cursor.moveToNext()){


            isVirus=true;
        }



        return  isVirus;
    }
}
