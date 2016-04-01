package com.cskaoyan.mobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.cskaoyan.mobile.db.MyLockAppDBHelper;
import com.cskaoyan.mobile.provider.MyAppLockProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lan on 2016/4/1.
 */
public class LockAppDao {


    //加入到该db

    private MyLockAppDBHelper helper;
    final SQLiteDatabase db;
    private Context ctx;

    MyAppLockProvider provider;
    public LockAppDao(Context ctx) {


        helper = new MyLockAppDBHelper(ctx,"lockapp.db",null,1);
        db = helper.getReadableDatabase();
        this.ctx=ctx;
    }

    public long  inserttoDb(String pkg){

       /* ContentValues cv=new ContentValues();
        cv.put("packagename", pkg);
        return db.insert("lockapp",null,cv);*/

        ContentValues cv=new ContentValues();
        cv.put("packagename", pkg);
         ctx.getContentResolver().insert(Uri.parse("content://com.cskaoyan.app"), cv);

        return  0;
    }


    //从该db删除

    public int deleteFromDb(String pkg){

        String[] args={pkg};
        return  ctx.getContentResolver().delete(Uri.parse("content://com.cskaoyan.app"), "packagename=?",args);
    }


    public boolean isLocked(String pkg){

        boolean flag =false;

        final Cursor cursor = db.rawQuery("select * from lockapp where packagename = ?", new String[]{pkg});

        if (cursor.moveToNext())
            flag = true;

        return flag;
    }


    public List<String> getAllLockedApp(){

        List<String> lockedapplist = new ArrayList<>();

        final Cursor cursor = db.rawQuery("select packagename from lockapp  ",null);

        while (cursor.moveToNext()){

            final String packagename = cursor.getString(0);

            lockedapplist.add(packagename);
        }


        return  lockedapplist;
    }

}
