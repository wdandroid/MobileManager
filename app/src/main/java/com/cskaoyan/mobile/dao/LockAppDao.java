package com.cskaoyan.mobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cskaoyan.mobile.db.MyLockAppDBHelper;

/**
 * Created by Lan on 2016/4/1.
 */
public class LockAppDao {


    //加入到该db

    private MyLockAppDBHelper helper;
    final SQLiteDatabase db;
    public LockAppDao(Context ctx) {


        helper = new MyLockAppDBHelper(ctx,"lockapp.db",null,1);
        db = helper.getReadableDatabase();
    }

    public long  inserttoDb(String pkg){

        ContentValues cv=new ContentValues();
        cv.put("packagename", pkg);
        return db.insert("lockapp",null,cv);

    }


    //从该db删除

    public int deleteFromDb(String pkg){

        String[] args={pkg};
        return   db.delete("lockapp","packagename=?",args);
    }


    public boolean isLocked(String pkg){

        boolean flag =false;

        final Cursor cursor = db.rawQuery("select * from lockapp where packagename = ?", new String[]{pkg});

        if (cursor.moveToNext())
            flag = true;

        return flag;
    }

}
