package com.cskaoyan.mobile.mobilemanager;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cskaoyan.mobile.bean.AppInfo;
import com.cskaoyan.mobile.dao.LockAppDao;
import com.cskaoyan.mobile.service.MyLockAppService;
import com.cskaoyan.mobile.utils.MyAsyncTast;
import com.cskaoyan.mobile.utils.MyAsyncTast2;
import com.cskaoyan.mobile.utils.PackageUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PackageManagerActivity extends ActionBarActivity implements View.OnClickListener {

    private ListView lv_package_appinfo;
    private List<AppInfo> appinfolist;
    private List<AppInfo> userAppinfolist;
    private List<AppInfo> systemAppinfolist;
    private TextView tv_applist_apptype;
    private PopupWindow popupWindow;
    private AppInfo current_click_appInfo;
    private MyAsyncTast3 mytask;
    private MyAdapter listadapter;
    private LockAppDao dao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_manager);

        TextView  tv_pacakge_rom = (TextView) findViewById(R.id.tv_pacakge_rom);
        TextView  tv_pacakge_sdcard = (TextView) findViewById(R.id.tv_pacakge_sdcard);

        tv_pacakge_rom.setText("手机ROM剩余空间:\r\n"+ bytetoMega(PackageUtils.getAvailableROMSize()));
        tv_pacakge_sdcard.setText("手机SDCARD剩余空间:\r\n" + bytetoMega(PackageUtils.getAvailableSDcardSize()));

        tv_applist_apptype = (TextView) findViewById(R.id.tv_applist_apptype);

        lv_package_appinfo = (ListView) findViewById(R.id.lv_package_appinfo);



        userAppinfolist = new ArrayList<>();
        systemAppinfolist = new ArrayList<>();

        dao = new LockAppDao(this);

       /* new MyAsyncTast(){
            @Override
            public void doinBackgroud() {
                //获取显示的数据，封装到一个list
                appinfolist= PackageUtils.getAllAppInfo(PackageManagerActivity.this);
            }

            @Override
            public void doafterbackgroud() {
                lv_package_appinfo.setAdapter(new MyAdapter());

            }
        }.execute();*/


        refresh();
/*
        try {
            URL url = new URL("http://www.baidu.com/xx.mp3");

            mytask.execute(url);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/


        lv_package_appinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //要显示的view
/*
                TextView tv= new TextView(PackageManagerActivity.this);
                tv.setText("点我卸载");
                tv.setBackgroundColor(Color.RED);
*/


                if (position<userAppinfolist.size()+2){
                    current_click_appInfo = userAppinfolist.get(position-1);
                }else
                {
                    current_click_appInfo = systemAppinfolist.get(position-userAppinfolist.size()-2);
                }


                 View v =View.inflate(PackageManagerActivity.this,R.layout.popupwindow,null);
                LinearLayout  ll_popup_start = (LinearLayout) v.findViewById(R.id.ll_popup_start);
                LinearLayout  ll_popup_share = (LinearLayout) v.findViewById(R.id.ll_popup_share);
                LinearLayout  ll_popup_uninstall = (LinearLayout) v.findViewById(R.id.ll_popup_uninstall);

                ll_popup_start.setOnClickListener(PackageManagerActivity.this);
                ll_popup_share.setOnClickListener(PackageManagerActivity.this);
                ll_popup_uninstall.setOnClickListener(PackageManagerActivity.this);

                if (popupWindow==null){
                    /*popupWindow = new PopupWindow();
                    popupWindow.setContentView(v);
                    //此处要设置popupwindow的宽高，否则无法显示
                    popupWindow.setHeight(100);
                    popupWindow.setWidth(200);*/

                    popupWindow = new PopupWindow(v, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT);

                }else{
                    //让之前的popwindow消失掉
                    popupWindow.dismiss();
                }



                //让popupwindow显示在指定的坐标显示，就是当前view的位置
                int[] location= new int[2];
                view.getLocationOnScreen(location);


                popupWindow.showAtLocation(view, Gravity.TOP | Gravity.LEFT, location[0]+60, location[1]);


            }
        });

        lv_package_appinfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(PackageManagerActivity.this, "Long press", Toast.LENGTH_SHORT).show();

                final ImageView iv_applist_lock = (ImageView) view.findViewById(R.id.iv_applist_lock);


                if (position<userAppinfolist.size()+2){
                    current_click_appInfo = userAppinfolist.get(position-1);
                }else
                {
                    current_click_appInfo = systemAppinfolist.get(position-userAppinfolist.size()-2);
                }

                if (dao.isLocked(current_click_appInfo.getPackagename())){
                    //解锁

                    iv_applist_lock.setImageResource(R.drawable.unlock);
                    dao.deleteFromDb(current_click_appInfo.getPackagename());

                }
                else{
                    //加锁
                    iv_applist_lock.setImageResource(R.drawable.lock);
                    dao.inserttoDb(current_click_appInfo.getPackagename());

                }



                return true;
            }
        });



        //启动监听的service

        startService(new Intent(this, MyLockAppService.class));

    }

    private void refresh() {
        mytask = new MyAsyncTast3();
        mytask.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ll_popup_start:
//                Toast.makeText(PackageManagerActivity.this, "start", Toast.LENGTH_SHORT).show();
                start();
                break;
            case R.id.ll_popup_share:
//                Toast.makeText(PackageManagerActivity.this, "share", Toast.LENGTH_SHORT).show();
                share();
                break;
            case R.id.ll_popup_uninstall:
//                Toast.makeText(PackageManagerActivity.this, "uninstall", Toast.LENGTH_SHORT).show();
                uninstall();
                break;
        }
    }

    private void share() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "推荐一个好玩的app给你" + current_click_appInfo.getAppname() + "下载地址 http://www.baidu.com/xx.apk");
        startActivity(intent);
    }

    private void start() {
        //根据当前点击的app的包名，去获取该app的启动intent。
        Intent intent = getPackageManager().getLaunchIntentForPackage(current_click_appInfo.getPackagename());
        startActivity(intent);

    }

    private void uninstall() {


        if (getPackageName().equals(current_click_appInfo.getPackagename())){

            Toast.makeText(PackageManagerActivity.this, "无法卸载自己！", Toast.LENGTH_SHORT).show();
            return;
        }else if (current_click_appInfo.isSystem()){
            Toast.makeText(PackageManagerActivity.this, "无法卸载系统应用！", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + current_click_appInfo.getPackagename()));
        startActivityForResult(intent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Log.i("TAG","refreshlist"+requestCode+":"+resultCode);
        if (requestCode==100){
//            if (!(resultCode==RESULT_CANCELED)){
                Log.i("TAG","refreshlist");
                userAppinfolist.clear();
                systemAppinfolist.clear();
                refresh();
 //            }
        }
    }



    class MyAsyncTast3 extends AsyncTask<URL, Integer, Float> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Float doInBackground(URL...  params) {
            appinfolist= PackageUtils.getAllAppInfo(PackageManagerActivity.this);

            for (AppInfo app:appinfolist) {
                if (app.isSystem()){

                    systemAppinfolist.add(app);
                }else
                {
                    userAppinfolist.add(app);
                }
            }
//            publishProgress(10);
            return null;
        }

        @Override
        protected void onPostExecute(Float aFloat) {


            if (listadapter==null){
                listadapter=     new MyAdapter();
                lv_package_appinfo.setAdapter(listadapter);
            }else{
                listadapter.notifyDataSetChanged();
            }


            //页面加载的时候，系统设置setOnScrollListener 会call到一次onScroll。
            lv_package_appinfo.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                    if (popupWindow!=null){
                        popupWindow.dismiss();
                        popupWindow=null;
                    }

                    if (firstVisibleItem >= userAppinfolist.size() + 1) {

                        tv_applist_apptype.setText("系统应用，" + systemAppinfolist.size() + "个");
                    } else {
                        tv_applist_apptype.setText("用户应用，" + userAppinfolist.size() + "个");

                    }
                }
            });
             super.onPostExecute(aFloat);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }



    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return appinfolist.size();
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



           // final AppInfo appInfo = appinfolist.get(position);

            if (position==0){

                TextView tv = new TextView(PackageManagerActivity.this);
                int num= userAppinfolist.size();
                tv.setText("用户应用，共"+num+"个");
                tv.setTextColor(Color.RED);

                return tv;

            }else if(position==userAppinfolist.size()+1){

                TextView tv = new TextView(PackageManagerActivity.this);
                int num= systemAppinfolist.size();
                tv.setText("系统应用，共"+num+"个");
                tv.setTextColor(Color.RED);

                return tv;

            }


            AppInfo appInfo;
            if (position<userAppinfolist.size()+2){
                appInfo = userAppinfolist.get(position-1);
            }else
            {
                appInfo = systemAppinfolist.get(position-userAppinfolist.size()-2);
            }

              View item;
              ViewHolder holder;
            if (convertView!=null&&convertView instanceof RelativeLayout){

                item=convertView;
                holder = (ViewHolder) item.getTag();

            }else {
                  item = View.inflate(PackageManagerActivity.this, R.layout.item_applist, null);
                  ImageView iv_applist_icon = (ImageView) item.findViewById(R.id.iv_applist_icon);
                  TextView tv_applist_appname = (TextView) item.findViewById(R.id.tv_applist_appname);
                  TextView tv_applist_location = (TextView) item.findViewById(R.id.tv_applist_location);
                  ImageView iv_applist_lock = (ImageView) item.findViewById(R.id.iv_applist_lock);

                  holder = new ViewHolder();
                  holder.iv_applist_icon=iv_applist_icon;
                  holder.tv_applist_appname =tv_applist_appname;
                  holder.tv_applist_location=tv_applist_location;
                  holder.iv_applist_lock =iv_applist_lock;

                  item.setTag(holder);
            }




            holder.iv_applist_icon.setImageDrawable(appInfo.getIcon());
            holder.tv_applist_appname.setText(appInfo.getAppname());

            if (appInfo.isSdcard())
                holder.tv_applist_location.setText("安装在sdcard上");
            else
                holder.tv_applist_location.setText("安装在ROM上");

            if (dao.isLocked(appInfo.getPackagename())){
                holder.iv_applist_lock.setImageResource(R.drawable.lock);

            }else{
                holder.iv_applist_lock.setImageResource(R.drawable.unlock);
            }
            return item;
        }


        class ViewHolder {

            ImageView iv_applist_icon;
            TextView tv_applist_appname;
            TextView tv_applist_location;
            ImageView iv_applist_lock;
        }
    }


    public String bytetoMega(long bytesize){


        return Formatter.formatFileSize(this,bytesize);
    }
}
