package com.cskaoyan.mobile.mobilemanager;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cskaoyan.mobile.application.MyApplication;
import com.cskaoyan.mobile.utils.Md5Utils;

public class HomeActivity extends ActionBarActivity {

    private TextView tv_home_welcome;
    private GridView gv_home_content;

    private int[] iconarray ={R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app
,    R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
    R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings};

    private  String[] titles={"手机防盗","通讯卫士","软件管理",
    "进程管理","流量统计","手机杀毒",
    "缓存清理","高级工具","设置中心"};

    private final int  CONTENT_NUM = 9;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //final ActionBar actionBar = getActionBar();
        final android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();

        tv_home_welcome = (TextView) findViewById(R.id.tv_home_welcome);
        tv_home_welcome.setText("欢迎您,新用户,我们的应用可以保卫您手机的安全！");
        //方法2,让控件处于选中状态
        //tv_home_welcome.setSelected(true);


        //初始化九宫格
        //ListView
        gv_home_content = (GridView) findViewById(R.id.gv_home_content);
        gv_home_content.setAdapter(new MyAdpter());
        gv_home_content.setOnItemClickListener(new MyItemOnClickListener());

    }

    class MyItemOnClickListener implements AdapterView.OnItemClickListener{


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            switch (position){
                case 0:
                    //Toast.makeText(HomeActivity.this,titles[position],Toast.LENGTH_LONG).show();

                    //设置一个密码，输入密码正确之后才可以进入设置
                    //第一次进入的时候让他设置密码，保存到sp里。之后第二次以后才验证密码

                   String phonesafe_pwd=  MyApplication.configsp.getString("phonesafe_pwd","");
                   if (phonesafe_pwd.isEmpty()){
                       //表明没有设置过，此时弹出一个框让用户去设置

                       showSetpwdDialog();

                   }
                    else
                   {
                       //看看用户输入的密码跟之前保存的是否一样
                       showinputpwdDialog();

                   }



                   // startActivity(new Intent(HomeActivity.this,PhoneSafeActivity.class));

                    break;
                case 1:
                    Toast.makeText(HomeActivity.this,titles[position],Toast.LENGTH_LONG).show();

                    break;
                case 2:
                    Toast.makeText(HomeActivity.this,titles[position],Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(HomeActivity.this,titles[position],Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    Toast.makeText(HomeActivity.this,titles[position],Toast.LENGTH_LONG).show();
                    break;
                case 5:
                    Toast.makeText(HomeActivity.this,titles[position],Toast.LENGTH_LONG).show();
                    break;
                case 6:
                    Toast.makeText(HomeActivity.this,titles[position],Toast.LENGTH_LONG).show();
                    break;
                case 7:
                    Toast.makeText(HomeActivity.this,titles[position],Toast.LENGTH_LONG).show();
                    break;
                case 8:
                    //Toast.makeText(HomeActivity.this,titles[position],Toast.LENGTH_LONG).show();
                    startActivity(new Intent(HomeActivity.this,SettingActivity.class));
                    break;


            }

        }
    }


    private void showinputpwdDialog() {


        AlertDialog.Builder  builder = new  AlertDialog.Builder(this);

        View v= View.inflate(this,R.layout.inpwd_dialog,null);
        final TextView et_condialog_pwd= (TextView) v.findViewById(R.id.et_condialog_pwd);

        Button  bt_conpwddialog_cancle = (Button) v.findViewById(R.id.bt_conpwddialog_cancle);
        Button  bt_conpwddialog_confirm = (Button) v.findViewById(R.id.bt_conpwddialog_confirm);

        builder.setView(v);
        final AlertDialog dialog = builder.create();
        dialog.show();

        bt_conpwddialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pwd = et_condialog_pwd.getText().toString();

                if (!pwd.isEmpty()){

                   String pwddigest =  Md5Utils.getMd5Digest(pwd);

                    String savepwd=   MyApplication.configsp.getString("phonesafe_pwd", "");

                    //savepwd 还是可能为空的。时间差
                    if (!savepwd.isEmpty()){
                        if(savepwd.equals(pwddigest)){
                            dialog.dismiss();
                            startActivity(new Intent(HomeActivity.this,PhoneSafeActivity.class));
                        }
                        else
                            Toast.makeText(HomeActivity.this, "输入密码有误，请重输！", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(HomeActivity.this, "输入密码不能为空，请重输！", Toast.LENGTH_SHORT).show();







            }
        });

        bt_conpwddialog_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    private void showSetpwdDialog() {
        AlertDialog.Builder  builder = new  AlertDialog.Builder(this);

        View v= View.inflate(this,R.layout.setpwd_dialog,null);
        final TextView et_dialog_pwd= (TextView) v.findViewById(R.id.et_dialog_pwd);
        final TextView et_dialog_pwdcon= (TextView) v.findViewById(R.id.et_dialog_pwdcon);

        Button  bt_setpwddialog_confirm = (Button) v.findViewById(R.id.bt_setpwddialog_confirm);
        Button  bt_setpwddialog_cancle = (Button) v.findViewById(R.id.bt_setpwddialog_cancle);

        builder.setView(v);
        final AlertDialog dialog = builder.create();
        dialog.show();


        bt_setpwddialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pwd = et_dialog_pwd.getText().toString();
                String pwdcon = et_dialog_pwdcon.getText().toString();

                if (!pwd.isEmpty() && !pwdcon.isEmpty()) {
                    //进入判断

                    if (pwd.equals(pwdcon)) {

                         /*MyApplication.editor.putString("phonesafe_pwd",pwd);
                         MyApplication.editor.commit();*/

                        MyApplication.setConfigValue("phonesafe_pwd", Md5Utils.getMd5Digest(pwd));
                        dialog.dismiss();

                    } else {

                        Toast.makeText(HomeActivity.this, "用户名或者密码不一致，请重新输入!", Toast.LENGTH_SHORT).show();

                    }


                } else {

                    Toast.makeText(HomeActivity.this, "用户名或者密码不能为空!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        bt_setpwddialog_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    class MyAdpter extends BaseAdapter{

        @Override
        public int getCount() {
            return CONTENT_NUM;
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

            //GridView 优化
            // 方法1 ：复用convertview
            // 方法2 ：viewholder

            View v = View.inflate(HomeActivity.this,R.layout.item_gridview,null);
            ImageView iv_gv_icon = (ImageView) v.findViewById(R.id.iv_gv_icon);
            TextView tv = (TextView) v.findViewById(R.id.tv_gv_name);

            iv_gv_icon.setImageResource(iconarray[position]);
            tv.setText(titles[position]);
            return v;
        }
    }
}
