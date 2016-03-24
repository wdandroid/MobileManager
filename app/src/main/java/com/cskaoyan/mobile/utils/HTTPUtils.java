package com.cskaoyan.mobile.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Lan on 2016/3/24.
 */
public class HTTPUtils {

    public static String getTextFromStream(InputStream is)   {

        String result="";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//？ 输出到哪里

        byte[] bytes = new byte[1024];
        int len =-1;
        try {
            while((len=is.read(bytes,0,1024))!=-1) {

              baos.write(bytes,0,len);

            }

            baos.close();

            result= baos.toString("GBK");


        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }

}
