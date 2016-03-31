package com.cskaoyan.mobile.mobilemanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cskaoyan.mobile.bean.AppInfo;
import com.cskaoyan.mobile.utils.PackageUtils;

import java.util.List;

public class PackageManagerActivity extends ActionBarActivity {

    private ListView lv_package_appinfo;
    private List<AppInfo> appinfolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_manager);

        TextView  tv_pacakge_rom = (TextView) findViewById(R.id.tv_pacakge_rom);
        TextView  tv_pacakge_sdcard = (TextView) findViewById(R.id.tv_pacakge_sdcard);

        tv_pacakge_rom.setText("手机ROM剩余空间:\r\n"+ bytetoMega(PackageUtils.getAvailableROMSize()));
        tv_pacakge_sdcard.setText("手机SDCARD剩余空间:\r\n" + bytetoMega(PackageUtils.getAvailableSDcardSize()));

        lv_package_appinfo = (ListView) findViewById(R.id.lv_package_appinfo);

        //获取显示的数据，封装到一个list
        appinfolist= PackageUtils.getAllAppInfo(this);


        lv_package_appinfo.setAdapter(new MyAdapter());

    }

    class MyAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }


    public String bytetoMega(long bytesize){


        return Formatter.formatFileSize(this,bytesize);
    }
}
