package com.lancan.cnblogs;

import android.app.Application;
import android.content.Context;

import com.lancan.cnblogs.util.NetWorkUtil;

/**
 * Created by lan on 16-10-31.
 */

public class CNBlogsApplication extends Application{
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        NetWorkUtil.getInstance().init(mContext);
        NetWorkUtil.getInstance().registerNetWorkReceiver();
    }

    public static Context getContext(){
        return mContext;
    }
}
