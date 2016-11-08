package com.lancan.cnblogs.presenter.implView;

import com.lancan.cnblogs.entity.HotNewsInfo;

import java.util.List;

/**
 * Created by lan on 16-11-1.
 */

public interface IHotNewsFragmentPresenter {

    void updateHotNewsListData(List<HotNewsInfo> hotNewsInfoList);
}
