package com.view.tableview.listener;

import android.view.View;

/**
 * Created by zhaolin2633 on 2018/9/29.
 */

public interface OnItemClickListenter {
    /**
     * @param item     点击项
     * @param position 点击位置
     */
    void onItemParentClick(View item, int position);

    /**
     * @param item     点击项
     * @param position 点击位置
     */
    void onItemChildClick(View item, int position);
}
