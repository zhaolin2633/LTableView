package com.view.tableview.listener;

import com.view.tableview.CustomHorizontalScrollView;

/**
 * 表格创建完成回调
 */
public interface OnTableViewCreatedCompletedListener {
    /**
     * 返回当前横向滚动视图给上个界面
     */
    void onTableViewCreatedCompleted(CustomHorizontalScrollView mScrollView);
}
