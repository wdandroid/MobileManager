package com.cskaoyan.mobile.mobilemanager;

import com.cskaoyan.mobile.dao.BlackNumberDao;

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
}
