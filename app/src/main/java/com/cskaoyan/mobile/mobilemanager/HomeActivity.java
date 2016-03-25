package com.cskaoyan.mobile.mobilemanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
                    Toast.makeText(HomeActivity.this,titles[position],Toast.LENGTH_LONG).show();

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
                    Toast.makeText(HomeActivity.this,titles[position],Toast.LENGTH_LONG).show();
                    break;


            }

        }
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
