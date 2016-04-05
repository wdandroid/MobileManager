package com.cskaoyan.mobile.mobilemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cskaoyan.mobile.dao.BlackNumberDao;
import com.cskaoyan.mobile.service.BlackNumberService;

import java.util.List;

public class TelephoneManagerActivity extends ActionBarActivity {

    private Button bt_phonemanager_add;
    private BlackNumberDao dao ;
    private ListView lv_phonemanager_blacknum;

    private List<listitem> blacknumberlist;
    private MyBlacknumberListAdapter myBlacknumberListAdapter;


    public static class listitem{

        public String blacknum;
        public   int mode;

        public listitem(String blacknum, int mode) {
            this.blacknum = blacknum;
            this.mode = mode;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone_manager);
        bt_phonemanager_add = (Button) findViewById(R.id.bt_phonemanager_add);
        lv_phonemanager_blacknum = (ListView) findViewById(R.id.lv_phonemanager_blacknum);



        dao = new BlackNumberDao(this);
        blacknumberlist = dao.getallBlacknumber();
        myBlacknumberListAdapter = new MyBlacknumberListAdapter();
        lv_phonemanager_blacknum.setAdapter(myBlacknumberListAdapter);

        bt_phonemanager_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View view = View.inflate(TelephoneManagerActivity.this, R.layout.dialog_add_blacknum, null);

                final EditText et_blacknum_numer = (EditText) view.findViewById(R.id.et_blacknum_numer);
                final Button bt_blacknum_confirm = (Button) view.findViewById(R.id.bt_blacknum_confirm);
                final Button bt_blacknum_cancle = (Button) view.findViewById(R.id.bt_blacknum_cancle);
                final RadioGroup rg_addblacknumber_mode = (RadioGroup) view.findViewById(R.id.rg_addblacknumber_mode);


                final AlertDialog alertDialog = new AlertDialog.Builder(TelephoneManagerActivity.this)
                        .setView(view)
                        .create();

                alertDialog.show();


                bt_blacknum_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //拿到当前view上的号码跟模式，并插入到数据库
                        final String blacknumber = et_blacknum_numer.getText().toString();

                        final int rb_id = rg_addblacknumber_mode.getCheckedRadioButtonId();
                        Log.i("bt_blacknum_confirm", rb_id + "---" + blacknumber);

                        int mode = -1;
                        if (rb_id == R.id.rb_blacknum_call) {

                            mode = 1;
                        } else if (rb_id == R.id.rb_blacknum_sms) {

                            mode = 2;
                        } else if (rb_id == R.id.rb_blacknum_all) {

                            mode = 3;
                        }
                        //更新数据库
                        if (-1 != dao.insertBlackNumber(blacknumber, mode)) {
                            //更新UI
                            blacknumberlist.add(new listitem(blacknumber, mode));
                            myBlacknumberListAdapter.notifyDataSetChanged();

                        }


                        Log.i("bt_blacknum_confirm", mode + "---" + blacknumber);

                        alertDialog.dismiss();

                    }
                });

                bt_blacknum_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //dismiss
                        alertDialog.dismiss();

                    }
                });


            }
        });


        lv_phonemanager_blacknum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final listitem listitem = blacknumberlist.get(position);
                final int mypostion = position;

                new AlertDialog.Builder(TelephoneManagerActivity.this)
                        .setTitle("确认要删除吗?")
                        .setMessage("当前号码" + listitem.blacknum)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (1 == dao.deleteBlackNumber(listitem.blacknum)) {

                                    blacknumberlist.remove(mypostion);
                                    myBlacknumberListAdapter.notifyDataSetChanged();
                                }

                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();


            }
        });


        lv_phonemanager_blacknum.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                final listitem currentlistitem = blacknumberlist.get(position);
                final int mypostion = position;

                new AlertDialog.Builder(TelephoneManagerActivity.this)
                        .setTitle("修改拦截模式?")
                        .setSingleChoiceItems(new String[]{"拦截电话", "拦截短信", "拦截全部"}, currentlistitem.mode - 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (1 == dao.updateMode(currentlistitem.blacknum, which + 1)) {

                                    currentlistitem.mode = which + 1;
                                    myBlacknumberListAdapter.notifyDataSetChanged();

                                    Log.i("onItemLongClick", currentlistitem.blacknum + ":" + (which + 1));

                                    dialog.dismiss();
                                }
                            }
                        })
                        .show();

                return true;
            }
        });


        startService(new Intent(this, BlackNumberService.class));
    }



    class MyBlacknumberListAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return blacknumberlist.size();
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

            final View view = View.inflate(TelephoneManagerActivity.this, R.layout.item_blacknumberlist, null);

            TextView tv_blacknum_number= (TextView) view.findViewById(R.id.tv_blacknum_number);
            TextView tv_blacknum_mode= (TextView)   view.findViewById(R.id.tv_blacknum_mode);

            final listitem listitem = blacknumberlist.get(position);

            tv_blacknum_number.setText(listitem.blacknum);
            tv_blacknum_mode.setText(listitem.mode + "");


            return view;
        }
    }
}
