package com.cskaoyan.mobile.mobilemanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LockAppActivity extends ActionBarActivity {

    private EditText et_lockapp_password;
    private String packagename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_app);


        final TextView tv_applock_packagename = (TextView) findViewById(R.id.tv_applock_packagename);
        et_lockapp_password = (EditText) findViewById(R.id.et_lockapp_password);

        final Intent intent = getIntent();
        packagename = intent.getStringExtra("packagename");

        tv_applock_packagename.setText(packagename);

    }

    @Override
    public void onBackPressed() {
//      super.onBackPressed();

//        这里点击back回到桌面

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);

    }

    public void check(View v){

        final String s = et_lockapp_password.getText().toString();

        if (s.equals("456")){

            //解锁
            //临时解锁0--》 告诉service我们的应用是临时解锁的应用，不要加锁

            Intent applock = new Intent();
            applock.setAction("com.cskaoyan.mobilemanager.tempunlock");
            applock.putExtra("package",packagename);
            sendBroadcast(applock);

            finish();
        }


    }
}
