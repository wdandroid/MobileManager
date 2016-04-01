package com.cskaoyan.mobile.mobilemanager;

import android.util.Log;

import com.cskaoyan.mobile.dao.LockAppDao;
import com.cskaoyan.mobile.db.MyLockAppDBHelper;

import java.util.List;

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

    public void testQueryall(){

        LockAppDao dao = new LockAppDao(getContext());

//        assertEquals(1, dao.deleteFromDb("com.cskaoyan.myapp"));

        final List<String> allLockedApp = dao.getAllLockedApp();
        for (String appname : allLockedApp) {
            Log.i("tag", appname);
        }


    }
}
