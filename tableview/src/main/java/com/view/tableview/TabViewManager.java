package com.view.tableview;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by zhaolin2633 on 2018/9/30.
 */

public class TabViewManager {
    /**
     * 竖线
     *
     * @param context
     * @param attribute
     * @return
     */
    public static View splitVerticalView(Context context, ViewAttribute attribute) {
        //画分隔线
        View splitView = new View(context);
        ViewGroup.LayoutParams splitViewParmas = new ViewGroup.LayoutParams(attribute.mLineHeight, LinearLayout.LayoutParams.MATCH_PARENT);
        splitView.setLayoutParams(splitViewParmas);
        splitView.setBackgroundColor(attribute.mHeaderAadTitleLineColor);
        return splitView;
    }

    /**
     * 竖线
     *
     * @param context
     * @param attribute
     * @return
     */
    public static View splitContentVerticalView(Context context, ViewAttribute attribute) {
        //画分隔线
        View splitView = new View(context);
        ViewGroup.LayoutParams splitViewParmas = new ViewGroup.LayoutParams(attribute.mLineHeight, LinearLayout.LayoutParams.MATCH_PARENT);
        splitView.setLayoutParams(splitViewParmas);
        splitView.setBackgroundColor(attribute.mContentLineColor);
        return splitView;
    }

    /**
     * //构造单元格
     *
     * @param text
     * @param context
     * @param attribute
     * @return
     */
    public static TextView tabTitleView(Context context, ViewAttribute attribute, String text) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);
        textView.setTextColor(attribute.mTableHeadTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, attribute.mTitleAndHederTextViewSize);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setPadding(attribute.mCellPaddingLeftAndRight, 0, attribute.mCellPaddingLeftAndRight, 0);
        return textView;
    }

    /**
     * //构造单元格
     *
     * @param text
     * @param context
     * @param attribute
     * @return
     */
    public static TextView tabContentView(Context context, ViewAttribute attribute, String text, int width) {
        TextView textView = new TextView(context);
        textView.setTextColor(attribute.mContentTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, attribute.mContentTextViewSize);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(width+attribute.mCellPaddingLeftAndRight,
                LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(textViewParams);
        return textView;
    }
}
