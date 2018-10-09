package com.view.tableview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

/**
 * Created by zhaolin2633 on 2018/9/29.
 */

public class ViewAttribute {
    /**
     * 顶部Title最小宽度
     */
    public int mTitleMinWidth;
    /**
     * 顶部Title最大宽度
     */
    public int mTitleMaxWidth;
    /**
     * 顶部Title最大宽高度
     */
    public int mTitleMaxHeight;

    /**
     * 表格左上角数据
     */
    public String mHeaderTitle = "省份";

    /**
     * 左上角头部最大宽度
     */
    public int mHeaderMaxWidth;
    /**
     * 左上角头部最大高度
     */
    public int mHeaderMaxHeight;
    /**
     * 内容最大高度
     */
    public int mContentMaxHeight;

    /**
     * 左边第一行背景颜色
     */
    public int mRowBackGroudColor;
    /**
     * 设置标题栏背景颜色
     */
    public int mTitleAndHederBackGroudColor;
    /**
     * 设置内容背景颜色
     */
    public int mContentBackGroudColor;


    /**
     * 单元格字体大小
     */
    public int mContentTextViewSize;
    /**
     * 单元格字体大小
     */
    public int mTitleAndHederTextViewSize;

    /**
     * 表格Header字体颜色
     */
    public int mTableHeadTextColor;
    /**
     * 表格标题字体颜色
     */
    public int mTableTitleTextColor;
    /**
     * 表格最左边列表字体颜色
     */
    public int mTableRowTextColor;
    /**
     * 表格内容字体颜色
     */
    public int mContentTextColor;

    /**
     * Item选中样式
     */
    public int mOnItemSeletor;
    /**
     * 单元格左右两边内边距
     */
    public int mCellPaddingLeftAndRight;
    /**
     * 单元格底部和底部内边距
     */
    public int mCellPaddingTopAndbottom;
    /**
     * 标题栏分割线颜色
     */
    public int mHeaderAadTitleLineColor;
    /**
     * 内容分割线颜色
     */
    public int mContentLineColor;
    /**
     * 内容分割线高度
     */
    public int mLineHeight;
    /**
     * 设置左边内容标示图标
     */
    public int leftContentDirectionImgResId;

    Context mContext;

    @SuppressLint("ResourceType")
    public ViewAttribute(Context context) {
        mContext = context;
        mHeaderTitle = "省份";
        mHeaderMaxWidth = dip2px(120);
        mHeaderMaxHeight = dip2px(40);
        mTitleMaxWidth = dip2px(80);
        mTitleMaxHeight = dip2px(40);
        mTitleMinWidth = dip2px(60);
        mContentMaxHeight = dip2px(45);

        mTableHeadTextColor = Color.parseColor("#817F80");
        mTableTitleTextColor = Color.parseColor("#817F80");
        mContentTextColor = Color.parseColor("#817F80");
        mTableRowTextColor = Color.parseColor("#817F80");

        mRowBackGroudColor = Color.parseColor("#FFFFFF");
        mTitleAndHederBackGroudColor = Color.parseColor("#FFFFFF");
        mContentBackGroudColor = Color.parseColor("#FFFFFF");
        mTitleAndHederTextViewSize = 15;
        mContentTextViewSize = 15;
        mCellPaddingTopAndbottom = dip2px(8);
        mCellPaddingLeftAndRight = dip2px(25);

        mHeaderAadTitleLineColor = Color.parseColor("#e6e6e6");
        mContentLineColor = Color.parseColor("#e6e6e6");
        mLineHeight = dip2px(1);
        leftContentDirectionImgResId =R.drawable.icon_down;
    }

    private int dip2px(int dp) {
        return DisplayUtil.dip2px(mContext, dp);
    }

    private int getColor(int resColorId) {
        return ContextCompat.getColor(mContext, resColorId);
    }

    public ViewAttribute setLineHeight(int mlineHeight) {
        this.mLineHeight = dip2px(mlineHeight);
        return this;
    }

    public ViewAttribute setContentBackGroudColor(int mContentBackGroudColor) {
        this.mContentBackGroudColor = getColor(mContentBackGroudColor);
        return this;
    }

    public ViewAttribute setHeaderAadTitleLineColor(int mHeaderAadTitleLineColor) {
        this.mHeaderAadTitleLineColor = getColor(mHeaderAadTitleLineColor);
        return this;
    }

    public ViewAttribute setContentLineColor(int mContentLineColor) {
        this.mContentLineColor = mContentLineColor;
        return this;
    }

    public ViewAttribute setTitleMinWidth(int titleMinWidth) {
        this.mTitleMinWidth = dip2px(titleMinWidth);
        return this;
    }

    public ViewAttribute setTitleMaxWidth(int titleMaxWidth) {
        this.mTitleMaxWidth = dip2px(titleMaxWidth);
        return this;
    }

    public ViewAttribute setTitleMaxHeight(int titleMaxHeight) {
        this.mTitleMaxHeight = dip2px(titleMaxHeight);
        return this;
    }

    public ViewAttribute setHeaderMaxWidth(int headerMaxWidth) {
        this.mHeaderMaxWidth = dip2px(headerMaxWidth);
        return this;
    }

    public ViewAttribute setHeaderMaxHeight(int headerMaxHeight) {
        this.mHeaderMaxHeight = dip2px(headerMaxHeight);
        return this;
    }

    public ViewAttribute setContentMaxHeight(int contentMaxHeight) {
        this.mContentMaxHeight = dip2px(contentMaxHeight);
        return this;
    }

    public ViewAttribute setRowBackGroudColor(int mRowBackGroudColor) {
        this.mRowBackGroudColor = getColor(mRowBackGroudColor);
        return this;
    }

    public ViewAttribute setTitleAndHederBackGroudColor(int mTitleAndHederBackGroudColor) {
        this.mTitleAndHederBackGroudColor = getColor(mTitleAndHederBackGroudColor);
        return this;
    }

    public ViewAttribute setContentTextViewSize(int mTextViewSize) {
        this.mContentTextViewSize = mTextViewSize;
        return this;
    }

    public ViewAttribute setTitleAndHederTextViewSize(int mTextViewSize) {
        this.mTitleAndHederTextViewSize = mTextViewSize;
        return this;
    }

    public ViewAttribute setTableHeadTextColor(int mTableHeadTextColor) {
        this.mTableHeadTextColor = getColor(mTableHeadTextColor);
        return this;
    }

    public ViewAttribute setTableTitleTextColor(int mTableTitleTextColor) {
        this.mTableTitleTextColor = getColor(mTableTitleTextColor);
        return this;
    }

    public ViewAttribute setTableRowTextColor(int mTableRowTextColor) {
        this.mTableRowTextColor = getColor(mTableRowTextColor);
        return this;
    }

    public ViewAttribute setContentTextColor(int mContentTextColor) {
        this.mContentTextColor = getColor(mContentTextColor);
        return this;
    }

    public ViewAttribute setOnItemSeletor(int mOnItemSeletor) {
        this.mOnItemSeletor = getColor(mOnItemSeletor);
        return this;
    }

    public ViewAttribute setCellPaddingLeftAndRight(int mCellPaddingLeftAndRight) {
        this.mCellPaddingLeftAndRight = dip2px(mCellPaddingLeftAndRight);
        return this;
    }

    public ViewAttribute setCellPaddingTopAndbottom(int mCellPaddingTopAndbottom) {
        this.mCellPaddingTopAndbottom = dip2px(mCellPaddingTopAndbottom);
        return this;
    }

    public ViewAttribute setHeaderTitle(String mHeaderTitle) {
        this.mHeaderTitle = mHeaderTitle;
        return this;
    }

    public ViewAttribute setLeftContentDirectionImgResId(int resId) {
        this.leftContentDirectionImgResId = resId;
        return this;
    }
}
