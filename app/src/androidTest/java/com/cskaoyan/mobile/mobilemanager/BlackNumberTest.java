package com.cskaoyan.mobile.mobilemanager;

import android.util.Log;

import com.cskaoyan.mobile.dao.BlackNumberDao;

import java.util.List;

/**
 * Created by Lan on 2016/4/5.
 */
public class BlackNumberTest extends  MyAndroidTestCase {


    //测试增加
    public void testInsert(){

        BlackNumberDao dao = new BlackNumberDao(getContext());
        assertEquals(1, dao.insertBlackNumber("5556",2));

    }

    //测试修改


    public void testupdate(){

        BlackNumberDao dao = new BlackNumberDao(getContext());
        assertEquals(-1, dao.updateMode("5556", 4));

    }


    //测试删除
    public void testDelete(){

        BlackNumberDao dao = new BlackNumberDao(getContext());
        assertEquals(1, dao.deleteBlackNumber("5556"));

    }


    //测试查询
    public void testQuery(){

        BlackNumberDao dao = new BlackNumberDao(getContext());
        dao.insertBlackNumber("5556",3);
        assertEquals(3, dao.queryMode("5556"));

    }

    //测试部分查询
    public void testQueryPart(){

        BlackNumberDao dao = new BlackNumberDao(getContext());

/*
        for (int i=0;i<100;i++){

            dao.insertBlackNumber("record_"+i,2);
        }*/

        final List<TelephoneManagerActivity.listitem> listitems = dao.getallPartBlacknumber(10, 20);

        for ( TelephoneManagerActivity.listitem l:listitems) {
            Log.i("TEST",l.toString());
        }
    }

    //
    public void testGetCount(){
        BlackNumberDao dao = new BlackNumberDao(getContext());


        assertEquals(200,dao.getTotalRecordNumber());
    }
}
