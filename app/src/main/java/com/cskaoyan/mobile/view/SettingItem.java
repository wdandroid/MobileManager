package com.cskaoyan.mobile.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cskaoyan.mobile.application.MyApplication;
import com.cskaoyan.mobile.mobilemanager.R;

/**
 * Created by Lan on 2016/3/25.
 */
public class SettingItem extends RelativeLayout implements View.OnClickListener{


    private static final String TAG = "SettingItem";
    private TextView tv_setting_autoupdate;
    private TextView tv_setting_updatestatus;
    private CheckBox cb_setting_update;
    private SharedPreferences.Editor editor;
    private String itemtitle;
    private String onstring;
    private String offstring;
    private String sp_keyname;

    //
    private MyOnclickListen mylistener;

    public SettingItem(Context context) {
        super(context);
        init(null);
    }

    public SettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    //在init里将三个控件加入到当前布局;
    private void init(AttributeSet attrs) {

        editor=MyApplication.configsp.edit();
        View v=        View.inflate(getContext(), R.layout.update_item,null);
        tv_setting_autoupdate = (TextView) v.findViewById(R.id.tv_setting_autoupdate);
        tv_setting_updatestatus = (TextView)v.findViewById(R.id.tv_setting_updatestatus);
        cb_setting_update = (CheckBox) v.findViewById(R.id.cb_setting_update);


        if (attrs!=null){

            itemtitle = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "itemtitle");
            onstring = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "onstring");
            offstring = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "offstring");
            //如果没有设置该属性，sp_keyname 得到null
            sp_keyname = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "sp_keyname");


            //初始化该控件内的子控件

            tv_setting_autoupdate.setText(itemtitle);


            if ( sp_keyname!=null){
                if(MyApplication.configsp.getBoolean(sp_keyname,true)){
                    tv_setting_updatestatus.setText(onstring);
                    cb_setting_update.setChecked(true);

                }
                else{
                    tv_setting_updatestatus.setText(offstring);
                    cb_setting_update.setChecked(false);

                }
            }


        }

        addView(v);
        setOnClickListener(null);

    }


    public interface MyOnclickListen{

       void  myCheckOnclick();
       void  myCancleOnclick();
    }


    public void setMyOnclickListener(MyOnclickListen l){
        mylistener =l;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {

        super.setOnClickListener(this);
    }

    public void setCheckBox(boolean flag){

        if (flag){

            cb_setting_update.setChecked(true);
            tv_setting_updatestatus.setText(onstring);

        }else
        {
            tv_setting_updatestatus.setText(offstring);
            cb_setting_update.setChecked(false);

        }

    }

    @Override
    public void onClick(View v) {

         //这里获取的就是当前的状态
        boolean checked = cb_setting_update.isChecked();
        Log.i(TAG, checked + "");
        if (checked){
            cb_setting_update.setChecked(false);
            tv_setting_updatestatus.setText(offstring);
            Log.i(TAG, checked + "取消");
            editor.putBoolean(sp_keyname,false);
            editor.commit();
            if (mylistener!=null){
                mylistener.myCancleOnclick();
            }


        }
        else {

            cb_setting_update.setChecked(true);
            tv_setting_updatestatus.setText(onstring);
            Log.i(TAG, checked + "开启");
            editor.putBoolean(sp_keyname, true);
            editor.commit();


            if (mylistener!=null){
                mylistener.myCheckOnclick();
            }
        }


    }
}
