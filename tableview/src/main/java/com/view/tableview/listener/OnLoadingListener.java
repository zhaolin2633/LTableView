package com.view.tableview.listener;

import com.view.precyclerview.PRecyclerView;

import java.util.ArrayList;

/**
 * Created by zhaolin2633 on 2018/9/29.
 */

public interface OnLoadingListener<T> {
    /**
     * 说明 下拉刷新
     * 创建时间 2017/9/17 下午1:54
     *
     * @param mPRecyclerView
     * @param list
     */
    void onRefresh(PRecyclerView mPRecyclerView, ArrayList<T> list);

    /**
     * 说明 上拉加载
     * 创建时间 2017/9/17 下午1:55
     *
     * @param mPRecyclerView
     * @param list
     */
    void onLoadMore(PRecyclerView mPRecyclerView, ArrayList<T> list);
}
