package com.cskaoyan.mobile.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.cskaoyan.mobile.db.MyLockAppDBHelper;

import java.net.URI;

/**
 * Created by Lan on 2016/4/1.
 */
public class MyAppLockProvider extends ContentProvider {


     private MyLockAppDBHelper helper;
      SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        helper = new MyLockAppDBHelper(getContext(),"lockapp.db",null,1);
        db = helper.getReadableDatabase();

        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        db.insert("lockapp", null, values);
        getContext().getContentResolver().notifyChange(Uri.parse("content://com.cskaoyan.app"),null);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        db.delete("lockapp",selection,selectionArgs);


        getContext().getContentResolver().notifyChange(Uri.parse("content://com.cskaoyan.app"), null);

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
