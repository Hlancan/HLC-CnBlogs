package com.lancan.cnblogs.presenter.implPresenter;

import android.content.Context;
import android.util.Log;

import com.lancan.cnblogs.Constants;
import com.lancan.cnblogs.entity.HotNewsInfo;
import com.lancan.cnblogs.presenter.implView.IHotNewsFragmentPresenter;
import com.lancan.cnblogs.presenter.implView.IHotNewsPresenter;
import com.lancan.cnblogs.util.OkHttpUtil;
import com.lancan.cnblogs.util.SAXUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lan on 16-11-1.
 */

public class HotNewsPresenterImpl extends BasePresenterImpl implements IHotNewsPresenter {

    private final static String TAG = "CnBlogs";
    private IHotNewsFragmentPresenter mIHotNewsFragmentPresenter = null;
    private List<HotNewsInfo> hotNewsInfos = null;
    public HotNewsPresenterImpl(Context context, IHotNewsFragmentPresenter iHotNewsFragmentPresenter) {
        mIHotNewsFragmentPresenter = iHotNewsFragmentPresenter;
    }


    @Override
    public void getHotNewsData(int index) {
        Log.d("CNBlog"," HotNewsPresenterImpl getHotNewsData>>>");
            try {
                Request request = new Request.Builder().url(Constants.HOTNEWS_URL + index).build();
                OkHttpUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG," HotNewsPresenterImpl getHotNewsData>>>onFailure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        InputStream inputStream = response.body().byteStream();
                        try {
                            hotNewsInfos = SAXUtil.getHotNewsList(inputStream);
                            Log.d(TAG,"onResponse>>>list.size" + hotNewsInfos.size() );
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(TAG," HotNewsPresenterImpl parserException>>>" + e.toString());
                        }
                        mIHotNewsFragmentPresenter.updateHotNewsListData(hotNewsInfos);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG," HotNewsPresenterImpl getHotNewsData  okHttpException>>>" + e.toString());
            }

    }
}
