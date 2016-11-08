package com.lancan.cnblogs.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lancan.cnblogs.R;
import com.lancan.cnblogs.adapter.HotNewsAdapter;
import com.lancan.cnblogs.entity.HotNewsInfo;
import com.lancan.cnblogs.presenter.implPresenter.HotNewsPresenterImpl;
import com.lancan.cnblogs.presenter.implView.IHotNewsFragmentPresenter;
import com.lancan.cnblogs.util.NetWorkUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lan on 16-11-1.
 */

public class HotNewsFragment extends BaseFragment implements IHotNewsFragmentPresenter,SwipeRefreshLayout.OnRefreshListener{


    private final static int UPDATE_HOTNEWS_LIST_DATA = 0x01;
    private final static int NETWORT_UNAVAILABLE = 0x02;
    @Bind(R.id.rv_hotnews)
    RecyclerView rvHotNews;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    private View rootView = null;
    private Context mContext = null ;
    private boolean isLoadingData = false;
    private LinearLayoutManager linearLayoutManager = null;
    private HotNewsAdapter hotNewsAdapter = null;
    private HotNewsPresenterImpl mHotNewsPresenter = null;
    private List<HotNewsInfo> mHotNewsInfoList = null;
    private int index = 12;//初始加载新闻条数
    private Snackbar snackbar = null;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_HOTNEWS_LIST_DATA:
                    hotNewsAdapter.addItems(mHotNewsInfoList);
                    isLoadingData = false;
                    refreshLayout.setRefreshing(false);
                    break;
                case NETWORT_UNAVAILABLE:
                    if (snackbar == null){
                       snackbar .make(rootView, "当前网络不可用，请检查网络是否已连接。", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else{
                        if (!snackbar.isShown()){
                            snackbar.setText("当前网络不可用，请检查网络是否已连接。");
                            snackbar.setDuration(Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                    if (refreshLayout.isRefreshing()){
                        refreshLayout.setRefreshing(false);
                    }
                    break;

            }
        }
    };
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        rootView = inflater.inflate(R.layout.hotnews_fragment_layout,container,false);
        ButterKnife.bind(this,rootView);
        Log.d("CNBlog"," HotNewsFragment onCreateView>>>");
        return rootView;
    }

    private void initData(){
        mHotNewsPresenter = new HotNewsPresenterImpl(mContext,this);
        hotNewsAdapter = new HotNewsAdapter(mContext);
    }

    private void initView(){

        linearLayoutManager = new LinearLayoutManager(mContext);
        refreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW);
        refreshLayout.setDistanceToTriggerSync(200);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(this);
        rvHotNews.setLayoutManager(linearLayoutManager);
        rvHotNews.setHasFixedSize(true);
//        rvHotNews.addItemDecoration();
        rvHotNews.setItemAnimator(new DefaultItemAnimator());
        rvHotNews.setAdapter(hotNewsAdapter);
        rvHotNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0){
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (!isLoadingData && (pastVisibleItems + visibleItemCount) >= totalItemCount){
                        if (NetWorkUtil.getInstance().isNetworkConnected()){
                            isLoadingData = true;
                            index += 10;//滑到底部加载更多时，每次多加载10条新闻
                            mHotNewsPresenter.getHotNewsData(index);
                    }else{
                            handler.sendEmptyMessage(NETWORT_UNAVAILABLE);
                        }
                    }
                }
            }
        });
        Log.d("CNBlog"," HotNewsFragment initView>>>");
        if (NetWorkUtil.getInstance().isNetworkConnected()){
            mHotNewsPresenter.getHotNewsData(index);
        }else{
            handler.sendEmptyMessage(NETWORT_UNAVAILABLE);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("CNBlog"," HotNewsFragment onViewCreated>>>");
        initData();
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onRefresh() {
        Log.d("CnBlogs"," HotNewsFragment onRefresh>>>");
        if (NetWorkUtil.getInstance().isNetworkConnected()){
        if (!isLoadingData){
            isLoadingData = true;
            index = 12;
            mHotNewsPresenter.getHotNewsData(index);
        }
        }else{
            handler.sendEmptyMessage(NETWORT_UNAVAILABLE);
        }
    }

    @Override
    public void updateHotNewsListData(List<HotNewsInfo> hotNewsInfoList) {
        mHotNewsInfoList = hotNewsInfoList;
        handler.sendEmptyMessage(UPDATE_HOTNEWS_LIST_DATA);
    }
}
