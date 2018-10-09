package com.view.tableview.listener;

import android.widget.HorizontalScrollView;

/**
 * Created by zhaolin2633 on 2018/9/29.
 */

public interface OnTableViewRangeListener {
    /**
     * 说明 最左侧
     * 创建时间 2017/12/14 下午4:45
     *
     * @param view
     */
    void onLeft(HorizontalScrollView view);

    /**
     * 说明 最右侧
     * 创建时间 2017/12/14 下午4:45
     *
     * @param view
     */
    void onRight(HorizontalScrollView view);
}
