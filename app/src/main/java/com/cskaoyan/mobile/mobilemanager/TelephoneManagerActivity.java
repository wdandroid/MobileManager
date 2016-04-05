package com.cskaoyan.mobile.mobilemanager;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.cskaoyan.mobile.dao.BlackNumberDao;

import java.util.List;

public class TelephoneManagerActivity extends ActionBarActivity {

    private Button bt_phonemanager_add;
    private BlackNumberDao dao ;
    private ListView lv_phonemanager_blacknum;

    private List<listitem> blacknumberlist;


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

        lv_phonemanager_blacknum.setAdapter(new MyBlacknumberListAdapter());

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

                        final int mode = rg_addblacknumber_mode.getCheckedRadioButtonId();

                        dao.insertBlackNumber(blacknumber,mode);

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
    }



    class MyBlacknumberListAdapter extends BaseAdapter{


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
}
