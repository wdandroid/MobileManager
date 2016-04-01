package com.cskaoyan.mobile.mobilemanager;

import com.cskaoyan.mobile.dao.LockAppDao;
import com.cskaoyan.mobile.db.MyLockAppDBHelper;

/**
 * Created by Lan on 2016/4/1.
 */
public class MyDBTest extends  MyAndroidTestCase{

    public void testApplockDb(){

        MyLockAppDBHelper helper = new MyLockAppDBHelper(getContext(),"locakapp.db",null,1);
        helper.getReadableDatabase();

    }


    public void testInsert(){

        LockAppDao dao = new LockAppDao(getContext());
        dao.inserttoDb("com.cskaoyan.myapp");
        //assertEquals(1, dao.inserttoDb("com.cskaoyan.myapp"));

        assertFalse(dao.isLocked("com.cskaoyan.myapp"));
    }

    public void testDelete(){

        LockAppDao dao = new LockAppDao(getContext());

        assertEquals(1, dao.deleteFromDb("com.cskaoyan.myapp"));


    }
}
