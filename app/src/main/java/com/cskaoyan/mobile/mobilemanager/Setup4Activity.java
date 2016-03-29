package com.cskaoyan.mobile.mobilemanager;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cskaoyan.mobile.application.MyApplication;
import com.cskaoyan.mobile.receiver.MyDeviceAdminReceiver;

public class Setup4Activity extends SetupBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
    }

    public void previous(View v){
        startActivity(new Intent(this, Setup3Activity.class));

    }

    public void next(View v){


        startActivity(new Intent(this,PhoneSafeActivity.class));


    }

    public void active(View v){


        // Launch the activity to have the user enable our admin.
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

        ComponentName mDeviceAdminSample = new ComponentName(this,MyDeviceAdminReceiver.class);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,mDeviceAdminSample  );
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "hello,kitty");
        startActivityForResult(intent, 100);

    }


}
