package com.lancan.cnblogs.util;

import okhttp3.OkHttpClient;

/**
 * Created by lan on 16-11-1.
 */

public class OkHttpUtil {

    private final static String TAG = "OkHttpUtil";
    private static OkHttpClient okHttpClient = null;

    public static OkHttpClient getOkHttpClient(){
        if (okHttpClient == null){
            synchronized (OkHttpClient.class){
                if (okHttpClient == null){
                    okHttpClient = new OkHttpClient();
                }
            }
        }
        return okHttpClient;
    }
}
