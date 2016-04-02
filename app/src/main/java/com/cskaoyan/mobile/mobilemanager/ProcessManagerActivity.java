package com.cskaoyan.mobile.mobilemanager;

import android.app.ActivityManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.cskaoyan.mobile.bean.ProcessInfo;
import com.cskaoyan.mobile.utils.ProcessUtils;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class ProcessManagerActivity extends ActionBarActivity {

    private static final String TAG ="ProcessManagerActivity" ;
    private ListView lv_process_appinfo;

    List<ProcessInfo> processInfoList;
    List<ProcessInfo> userprocessInfoList;
    List<ProcessInfo> systemprocessInfoList;

    private MyAdpater myAdpater;

    boolean onlyshowuser=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_manager);

        //进程数
        final TextView tv_process_num = (TextView) findViewById(R.id.tv_process_num);
        //RAM 情况
        final TextView tv_process_ram = (TextView) findViewById(R.id.tv_process_ram);


        tv_process_num.setText("当前手机的进程数为:\r\n"+ ProcessUtils.getRunningProcessCount(this)+"个");

        tv_process_ram.setText(" 可用ram/总ram 为:\r\n" + byteToMega(ProcessUtils.getAvailableRam(this)) + "/" +
                byteToMega(ProcessUtils.geTotalRam(this)));


        lv_process_appinfo = (ListView) findViewById(R.id.lv_process_appinfo);


        processInfoList =ProcessUtils.getAllProcInfo(this);
        systemprocessInfoList = new ArrayList<>();
        userprocessInfoList = new ArrayList<>();

        for (ProcessInfo pp:processInfoList) {

            if (pp.isSystem())
                systemprocessInfoList.add(pp);
            else
                userprocessInfoList.add(pp);

        }

        myAdpater = new MyAdpater();
        lv_process_appinfo.setAdapter(myAdpater);

        lv_process_appinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i(TAG,"onItemClick");
                ProcessInfo current_processInfo;
                if (onlyshowuser)
                    current_processInfo =userprocessInfoList.get(position);
                else
                    current_processInfo = processInfoList.get(position);

                CheckBox cb_processlist_check = (CheckBox) view.findViewById(R.id.cb_processlist_check);


                Log.i(TAG,"onItemClick"+current_processInfo.isCheck());

                if (current_processInfo.isCheck()){

                    current_processInfo.setIsCheck(false);
                    cb_processlist_check.setChecked(false);
                }else{

                    current_processInfo.setIsCheck(true);
                    cb_processlist_check.setChecked(true);

                }



            }
        });

    }


    class MyAdpater extends BaseAdapter{


        @Override
        public int getCount() {

            int count =0;
            if (onlyshowuser)
                count =userprocessInfoList.size();
            else
                count = processInfoList.size();

            return count;
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


            final ProcessInfo processInfo ;//= processInfoList.get(position);

            if (onlyshowuser)
                processInfo =userprocessInfoList.get(position);
            else
                processInfo = processInfoList.get(position);



            final View view = View.inflate(ProcessManagerActivity.this, R.layout.item_processlist, null);

            ImageView iv_processlist_icon = (ImageView) view.findViewById(R.id.iv_processlist_icon);
            TextView tv_processlist_appname = (TextView) view.findViewById(R.id.tv_processlist_appname);
            TextView tv_processlist_ram = (TextView) view.findViewById(R.id.tv_processlist_ram);
            CheckBox cb_processlist_check = (CheckBox) view.findViewById(R.id.cb_processlist_check);


            iv_processlist_icon.setImageDrawable(processInfo.getAppicon());
            tv_processlist_appname.setText(processInfo.getAppname());
            tv_processlist_ram.setText(processInfo.getAppram() + "KB");
            cb_processlist_check.setChecked(processInfo.isCheck());

            return view;
        }
    }



    public String byteToMega(long bytes){


        return Formatter.formatFileSize(this,bytes);
    }


    private void updateallcheckbox(boolean isCheck) {
        for (ProcessInfo pp : processInfoList) {
            if (pp.getPackagename().equals(getPackageName()))
                continue;
            pp.setIsCheck(isCheck);
        }

        if (myAdpater != null)
            myAdpater.notifyDataSetChanged();
    }


    public void selectall(View v){

        updateallcheckbox(true);
    }



    public void deselectall(View v){
        updateallcheckbox(false);

    }

    public void showuser(View v){
        if (onlyshowuser==false)
            onlyshowuser =true;
        else
            onlyshowuser=false;

        if (myAdpater!=null)
            myAdpater.notifyDataSetChanged();
    }

    public void killselect(View v){


        final ActivityManager ams = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        List<ProcessInfo> deleteProclist = new ArrayList<>();
        if (onlyshowuser){
            for (ProcessInfo pp : userprocessInfoList) {
                if (pp.isCheck()){

                    ams.killBackgroundProcesses(pp.getPackagename());
//                    userprocessInfoList.remove(pp);
                    deleteProclist.add(pp);

                }
            }

            for (ProcessInfo p:deleteProclist){

                userprocessInfoList.remove(p);
            }
        }
        else{
            for (ProcessInfo pp : processInfoList) {
                if (pp.isCheck()){
                    ams.killBackgroundProcesses(pp.getPackagename());
//                  processInfoList.remove(pp);
                    deleteProclist.add(pp);
                }
            }

            for (ProcessInfo p:deleteProclist){
                processInfoList.remove(p);
            }

        }


        myAdpater.notifyDataSetChanged();



    }

}
