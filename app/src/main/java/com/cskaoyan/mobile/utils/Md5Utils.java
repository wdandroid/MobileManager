package com.cskaoyan.mobile.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Lan on 2016/3/26.
 */
public class Md5Utils {


    public  static String  getMd5Digest(String password){

        String afterencyp ="";

        try {
            MessageDigest md= MessageDigest.getInstance("md5");

            byte[] digest = md.digest(password.getBytes());

            StringBuffer result= new StringBuffer();//变长的string

            for (byte b : digest) {


                int ret = b&0xFF;

                String hexstring = Integer.toHexString(ret);
                System.out.println( hexstring);

                if (hexstring.length()==1) {
                    result.append("0");
                }

                result.append(hexstring);

            }


            System.out.println(result);
            afterencyp=result.toString();




        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return afterencyp;

    }


    public  static String  getAppMd5Digest(String apklocation){

        String afterencyp ="";
        byte[] digest = null;
        try {
            MessageDigest md= MessageDigest.getInstance("md5");


            File file = new File(apklocation);

            if (file.exists()){


                try {
                    FileInputStream fis = new FileInputStream(file);

                    byte[] buffer = new byte[1024];
                    int len =-1;

                    while((len=fis.read(buffer,0,1024))!=-1){
                        md.update(buffer,0,len);
                    }


                     fis.close();


                     digest = md.digest();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


            if (digest!=null){

                StringBuffer result= new StringBuffer();//变长的string

                for (byte b : digest) {


                    int ret = b&0xFF;

                    String hexstring = Integer.toHexString(ret);
//                    System.out.println( hexstring);

                    if (hexstring.length()==1) {
                        result.append("0");
                    }

                    result.append(hexstring);

                }


                System.out.println(result);
                afterencyp=result.toString();

            }






        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return afterencyp;

    }


}
