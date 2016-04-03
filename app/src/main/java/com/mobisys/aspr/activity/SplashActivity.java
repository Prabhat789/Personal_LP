package com.mobisys.aspr.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;
import com.pktworld.aspr.R;

import io.fabric.sdk.android.Fabric;


/**
 * Created by ubuntu1 on 10/3/16.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        Handler threadHandler = new Handler(Looper.getMainLooper());
        threadHandler.postDelayed(new Runnable() {

            public void run() {
                // TODO Auto-generated method stub
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000L);
    }
}
