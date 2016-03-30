package com.cskaoyan.mobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Lan on 2016/3/29.
 */
public class NumberLoactionDao {



    public static  String getNumberLocation(String number ,Context ctx){

        String  result ="";

        //电话号码的正则表达式
        // 130 131 132 133 134 135 136 137 138 139
        // 170
        // 18
        // 15
        //
      if(number.matches("1[3,7,8,5]\\d{9}")){
          String subnum = number.substring(0,7);

          SQLiteDatabase db =  SQLiteDatabase.openDatabase("data/data/" + ctx.getPackageName() + "/location.db", null,0);


          Cursor cursor= db.rawQuery("  select city , cardtype from address_tb where _id = ( select  outkey  from numinfo where mobileprefix =" + subnum + ")", null);


          while(cursor.moveToNext()){

              final int city = cursor.getColumnIndex("city");
              final int cardtype = cursor.getColumnIndex("cardtype");

              final String citystring = cursor.getString(city);
              final String cardtypestring = cursor.getString(cardtype);
              result=cardtypestring;
          }



      }



      return  result ;

    }
}
