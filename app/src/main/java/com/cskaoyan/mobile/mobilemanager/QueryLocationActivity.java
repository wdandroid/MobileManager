package com.cskaoyan.mobile.mobilemanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cskaoyan.mobile.dao.NumberLoactionDao;

public class QueryLocationActivity extends ActionBarActivity {

    private static final String TAG ="QueryLocationActivity" ;
    private EditText et_querylocation_num;
    private TextView tv_queryloction_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_location);

        et_querylocation_num = (EditText) findViewById(R.id.et_querylocation_num);
        tv_queryloction_result = (TextView) findViewById(R.id.tv_queryloction_result);

        et_querylocation_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG,"beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                final String numberLocation = NumberLoactionDao.getNumberLocation(s.toString(), QueryLocationActivity.this);
                tv_queryloction_result.setText(numberLocation);

                Log.i(TAG,"onTextChanged");

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG,"afterTextChanged");

            }
        });
    }






    public void query(View v){

        final String s = et_querylocation_num.getText().toString();

        final String numberLocation = NumberLoactionDao.getNumberLocation(s, this);

        if (numberLocation.isEmpty())
            Toast.makeText(QueryLocationActivity.this, "号码格式有误", Toast.LENGTH_SHORT).show();

        else
            tv_queryloction_result.setText(numberLocation);
           //Toast.makeText(QueryLocationActivity.this, numberLocation, Toast.LENGTH_SHORT).show();

    }
}
