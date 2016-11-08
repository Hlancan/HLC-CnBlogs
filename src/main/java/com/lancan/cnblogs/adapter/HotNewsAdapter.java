package com.lancan.cnblogs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lancan.cnblogs.R;
import com.lancan.cnblogs.entity.HotNewsInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lan on 16-11-1.
 */

public class HotNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = "CnBlogs";
    private final static int REFRESH_DATA = -1;
    private final static int NOMAL_ITEM = 1;
    private Context context;
    private List<HotNewsInfo> mHotNewsInfos = new ArrayList<HotNewsInfo>();

    public HotNewsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG,"HotNewsAdapter onCreateViewHolder >>> viewType = " + viewType);
        switch (viewType){
           case REFRESH_DATA:
           case NOMAL_ITEM:
               return new HotNewsViewHolder(LayoutInflater.from(context).inflate(R.layout.hotnews_item,parent,false));
       }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        Log.d(TAG,"HotNewsAdapter onBindViewHolder >>> viewType = " + viewType);
        switch (viewType){
            case REFRESH_DATA:

                break;
            case NOMAL_ITEM:
                bindNomalViewHolder((HotNewsViewHolder) holder,position);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mHotNewsInfos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return NOMAL_ITEM;
//        if (position < getDataItemCount() && position > 0){
//            return NOMAL_ITEM;
//        }
//        return REFRESH_DATA;//需要修改
    }

    private int getDataItemCount(){
        return mHotNewsInfos.size();
    }

    private void bindNomalViewHolder(HotNewsViewHolder holder, int position){
        HotNewsInfo hotNewsInfo = mHotNewsInfos.get(holder.getAdapterPosition());
//        Log.d(TAG,"HotNewsAdapter bindNomalViewHolder hotNewsInfo>>>" + hotNewsInfo.toString());
        holder.tvTitle.setText(hotNewsInfo.getTitle());
        holder.tvSouceName.setText(hotNewsInfo.getSourceName());
        holder.tvSummary.setText(hotNewsInfo.getSummary());
        holder.tvPublished.setText(hotNewsInfo.getPublished());
        holder.tvViews.setText(String.valueOf(hotNewsInfo.getViews()));
        holder.tvComments.setText(String.valueOf(hotNewsInfo.getComments()));

    }

    public void addItems(List<HotNewsInfo> hotNewsInfos){
//      mHotNewsInfos.addAll(hotNewsInfos);
        mHotNewsInfos = hotNewsInfos;
        notifyDataSetChanged();
        Log.d(TAG,"HotNewsAdapter addItems");

    }


    class HotNewsViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_hotnews_title)
        TextView tvTitle;
        @Bind(R.id.tv_hotnews_source_name)
        TextView tvSouceName;
        @Bind(R.id.tv_hotnews_summary)
        TextView tvSummary;
        @Bind(R.id.tv_hotnews_published)
        TextView tvPublished;
        @Bind(R.id.tv_hotnews_views)
        TextView tvViews;
        @Bind(R.id.tv_hotnews_comments)
        TextView tvComments;
        @Bind(R.id.hot_news_item)
        LinearLayout hotNewsItem;

        public HotNewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
