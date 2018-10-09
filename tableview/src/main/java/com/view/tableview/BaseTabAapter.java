package com.view.tableview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.view.tableview.listener.OnItemClickListenter;
import com.view.tableview.listener.OnItemLongClickListenter;
import com.view.tableview.listener.OnItemSelectedListenter;
import com.view.pinnedheader.AdapterStick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaolin2633 on 2018/9/29.
 */

public abstract class BaseTabAapter<T> extends RecyclerView.Adapter implements AdapterStick {
    /**
     * Item点击事件
     */
    protected OnItemClickListenter mOnItemClickListenter;

    /**
     * Item长按事件
     */
    protected OnItemLongClickListenter mOnItemLongClickListenter;

    /**
     * Item项被选中监听(处理被选中的效果)
     */
    protected OnItemSelectedListenter mOnItemSelectedListenter;

    protected List<T> mData;

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<Integer> viewWiths;
    protected ViewAttribute mAttr;

    public BaseTabAapter(Context context, List<T> data) {
        this.mData = data;
        this.mContext = context;
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mInflater = LayoutInflater.from(context);
        if (mAttr == null)
            mAttr = new ViewAttribute(context);
    }

    public BaseTabAapter(Context context) {
        this.mData = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public List<T> getData() {
        return mData;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getHeaderCount() {
        return 0;
    }


    public void setOnItemClickListenter(OnItemClickListenter mOnItemClickListenter) {
        this.mOnItemClickListenter = mOnItemClickListenter;
    }

    public void setOnItemLongClickListenter(OnItemLongClickListenter mOnItemLongClickListenter) {
        this.mOnItemLongClickListenter = mOnItemLongClickListenter;
    }

    public void setOnItemSelectedListenter(OnItemSelectedListenter mOnItemSelectedListenter) {
        this.mOnItemSelectedListenter = mOnItemSelectedListenter;
    }

    public void setViewWiths(List<Integer> titleWiths) {
        this.viewWiths = titleWiths;
    }

    public int getViewWith(int pos) {
        return viewWiths.get(pos);
    }


    public void setViewAttribute(ViewAttribute attribute) {
        this.mAttr = attribute;
    }
}
