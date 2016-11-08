package com.lancan.cnblogs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.lancan.cnblogs.R;

/**
 * Created by lan on 16-10-28.
 */

public class SplashScreenActivity extends Activity {

    private final static String TAG = "CnBlogs";
    private final static int DELAYED_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"SplashScreenActivity onCreate");
        setContentView(R.layout.spalsh_screen_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(intent);
                SplashScreenActivity.this.finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                Log.d(TAG,"SplashScreenActivity run");
            }
        },DELAYED_TIME);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
