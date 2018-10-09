package com.view.tableview;

import android.content.Context;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.view.entitiy.ParentEntity;
import com.view.precyclerview.PRecyclerView;
import com.view.precyclerview.ProgressStyle;
import com.view.tableview.listener.OnItemClickListenter;
import com.view.tableview.listener.OnItemLongClickListenter;
import com.view.tableview.listener.OnLoadingListener;
import com.view.tableview.listener.OnTableViewCreatedCompletedListener;
import com.view.tableview.listener.OnTableViewListener;
import com.view.tableview.listener.OnTableViewRangeListener;
import com.view.utils.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明 下拉刷新tabview
 * 创建时间 2017/3/29.
 */

public class TableViewRefresh {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 表格父视图
     */
    private ViewGroup mContentView;

    /**
     * 表格数据，每一行为一条数据，从表头计算
     */
    private ArrayList<ParentEntity> mTableDatas = new ArrayList<>();
    /**
     * 表格视图
     */
    private View mTableView;

    /**
     * 表格横向滚动监听事件
     */
    private OnTableViewListener mTableViewListener;

    /**
     * 表格横向滚动到边界监听事件
     */
    private OnTableViewRangeListener mTableViewRangeListener;

    /**
     * 表格上拉刷新、下拉加载监听事件
     */
    private OnLoadingListener mOnLoadingListener;
    /**
     * Item点击事件
     */
    private OnItemClickListenter mOnItemClickListenter;
    /**
     * Item长按事件
     */
    private OnItemLongClickListenter mOnItemLongClickListenter;


    //表格数据
    /**
     * 表格第一行数据,不包括第一个元素
     */
    private ArrayList<String> mTableFristData = new ArrayList<>();
    /**
     * 表格第一列数据，不包括第一个元素
     */
    private ArrayList<String> mTableColumnDatas = new ArrayList<>();

    /**
     * 表格每一行数据，不包括第一行和第一列
     */
    private ArrayList<ArrayList<String>> mTableRowDatas = new ArrayList<ArrayList<String>>();

    /**
     * 把所有的滚动视图放图列表，后面实现联动效果
     */
    private ArrayList<HorizontalScrollView> mScrollViews = new ArrayList<HorizontalScrollView>();
    /**
     * 所有title的宽度
     */
    private ArrayList<Integer> titleWiths = new ArrayList<Integer>();

    /**
     * 标题栏分割线
     */
    private View mHeadViewLine;
    /**
     * 表格左上角视图
     */
    private View mLockHeadViewLine;
    /**
     * 表格左上角视图
     */
    private TextView mColumnTitleView;
    /**
     * 第一行布局（锁状态）
     */
    private LinearLayout mLockHeadView;

    /**
     * 第一行滚动视图（锁状态）
     */
    private CustomHorizontalScrollView mLockScrollView;

    /**
     * 表格主视图
     */
    private PRecyclerView mTableScrollView;
    /**
     * 列表适配器
     */
    private TableViewAdapter mTableViewAdapter;
    private ViewAttribute mAttribute;

    /**
     * 构造方法
     *
     * @param mContext          上下文
     * @param mContentView      表格父视图
     * @param mTableColumnDatas 表格标题数据
     * @param mTableDatas       表格数据
     */
    public TableViewRefresh(Context mContext, ViewGroup mContentView, ArrayList<String> mTableColumnDatas, ArrayList<ParentEntity> mTableDatas) {
        this.mContext = mContext;
        this.mContentView = mContentView;
        this.mTableDatas = mTableDatas != null ? mTableDatas : new ArrayList<ParentEntity>();
        this.mTableColumnDatas = mTableColumnDatas != null ? mTableColumnDatas : new ArrayList<String>();
        initAttrs();
    }

    /**
     * 初始化属性
     */
    private void initAttrs() {
        mTableView = LayoutInflater.from(mContext).inflate(R.layout.tableview_refresh, null);
        mAttribute = new ViewAttribute(mContext);
    }

    /**
     * 展现视图
     */
    public void show() {
        initView();
        mContentView.removeAllViews();//清空视图
        mContentView.addView(mTableView);
    }


    /**
     * 初始化表格视图
     */
    private void initView() {
        mHeadViewLine = (View) mTableView.findViewById(R.id.headView_line);
        mLockHeadViewLine = (View) mTableView.findViewById(R.id.lockHeadView_line);
        mColumnTitleView = (TextView) mTableView.findViewById(R.id.lockHeadView_Text);
        mLockHeadView = (LinearLayout) mTableView.findViewById(R.id.lockHeadView);
        mLockScrollView = (CustomHorizontalScrollView) mTableView.findViewById(R.id.lockHeadView_ScrollView);
        mTableScrollView = (PRecyclerView) mTableView.findViewById(R.id.table_scrollView);
        mLockHeadView.setBackgroundColor(mAttribute.mRowBackGroudColor);
        mLockHeadViewLine.setBackgroundColor(mAttribute.mHeaderAadTitleLineColor);
        mHeadViewLine.setBackgroundColor(mAttribute.mHeaderAadTitleLineColor);
        mLockHeadView.setVisibility(View.VISIBLE);
        creatHeadView();
        MyLayoutManager layoutManager = new MyLayoutManager(mContext, MyLayoutManager.VERTICAL, false);
        layoutManager.setScrollEnabled(false);
        mTableScrollView.setNestedScrollingEnabled(false);
        mTableScrollView.setFocusableInTouchMode(false);
        mTableScrollView.setLayoutManager(layoutManager);
        mTableScrollView.setArrowImageView(R.drawable.iconfont_downgrey);
        mTableScrollView.setRefreshProgressStyle(ProgressStyle.BallRotate);
        mTableScrollView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mTableScrollView.setLoadingListener(new PRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if (mOnLoadingListener != null) {
                    mOnLoadingListener.onRefresh(mTableScrollView, mTableDatas);
                }
            }

            @Override
            public void onLoadMore() {
                if (mOnLoadingListener != null) {
                    mOnLoadingListener.onLoadMore(mTableScrollView, mTableDatas);
                }
            }
        });
        mTableViewAdapter = new TableViewAdapter(mContext, mTableDatas);
        mTableViewAdapter.setViewAttribute(mAttribute);
        mTableViewAdapter.setTitleWiths(titleWiths);
        mTableViewAdapter.setHorizontalScrollView(new OnTableViewListener() {
            @Override
            public void onTableViewScrollChange(int x, int y) {
                changeAllScrollView(x, y);
            }
        });
        if (mOnItemClickListenter != null) {
            mTableViewAdapter.setOnItemClickListenter(mOnItemClickListenter);
        }
        if (mOnItemLongClickListenter != null) {
            mTableViewAdapter.setOnItemLongClickListenter(mOnItemLongClickListenter);
        }
        mTableViewAdapter.setTableViewRangeListener(new OnTableViewRangeListener() {
            @Override
            public void onLeft(HorizontalScrollView view) {
                if (mTableViewRangeListener != null) {
                    mTableViewRangeListener.onLeft(view);
                }
            }

            @Override
            public void onRight(HorizontalScrollView view) {
                if (mTableViewRangeListener != null) {
                    mTableViewRangeListener.onRight(view);
                }
            }
        });
        mTableViewAdapter.setOnTableViewCreatedListener(new OnTableViewCreatedCompletedListener() {
            @Override
            public void onTableViewCreatedCompleted(CustomHorizontalScrollView mScrollView) {
                mScrollViews.add(mScrollView);
            }
        });
        mTableScrollView.setAdapter(mTableViewAdapter);
    }

    /**
     * 创建头部视图
     */
    private void creatHeadView() {
        mColumnTitleView.setTextColor(mAttribute.mTableHeadTextColor);
        mColumnTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mAttribute.mTitleAndHederTextViewSize);
        mColumnTitleView.setText(mAttribute.mHeaderTitle);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mColumnTitleView.getLayoutParams();
        layoutParams.width = mAttribute.mHeaderMaxWidth;
        layoutParams.height = mAttribute.mHeaderMaxHeight;
        mColumnTitleView.setLayoutParams(layoutParams);
        createScollview(mLockScrollView, mTableColumnDatas);
        mScrollViews.add(mLockScrollView);
        mLockScrollView.setOnScrollChangeListener(new CustomHorizontalScrollView.onScrollChangeListener() {
            @Override
            public void onScrollChanged(HorizontalScrollView scrollView, int x, int y) {
                changeAllScrollView(x, y);
            }

            @Override
            public void onScrollFarLeft(HorizontalScrollView scrollView) {
                if (mTableViewRangeListener != null) {
                    mTableViewRangeListener.onLeft(scrollView);
                }
            }

            @Override
            public void onScrollFarRight(HorizontalScrollView scrollView) {
                if (mTableViewRangeListener != null) {
                    mTableViewRangeListener.onRight(scrollView);
                }
            }
        });
    }


    /**
     * 改变所有滚动视图位置
     *
     * @param x
     * @param y
     */
    private void changeAllScrollView(int x, int y) {
        if (mScrollViews.size() > 0) {
            if (mTableViewListener != null) {
                mTableViewListener.onTableViewScrollChange(x, y);
            }
            for (int i = 0; i < mScrollViews.size(); i++) {
                HorizontalScrollView scrollView = mScrollViews.get(i);
                scrollView.scrollTo(x, y);
            }
        }
    }


    /**
     * 根据文字计算textview的高度
     *
     * @param view
     * @param text
     * @return
     */
    private int getTextViewWidth(TextView view, String text) {
        if (view != null) {
            TextPaint paint = view.getPaint();
            return (int) paint.measureText(text);
        }
        return 0;
    }


    /**
     * 构造Title滚动视图
     *
     * @param scrollView
     * @param datas
     */
    private void createScollview(HorizontalScrollView scrollView, List<String> datas) {
        titleWiths = new ArrayList<>();
        LinearLayout linearLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < datas.size(); i++) {
            //构造单元格
            TextView textView = TabViewManager.tabTitleView(mContext, mAttribute, datas.get(i));
            titleWiths.add(getTextViewWidth(textView, datas.get(i)) + mAttribute.mCellPaddingLeftAndRight);
            linearLayout.addView(textView);
            //画分隔线
            if (i != datas.size() - 1) {
                linearLayout.addView(TabViewManager.splitVerticalView(mContext, mAttribute));
            }
        }
        scrollView.addView(linearLayout);
    }

    public TableViewRefresh setTableViewListener(OnTableViewListener mTableViewListener) {
        this.mTableViewListener = mTableViewListener;
        return this;
    }

    public TableViewRefresh setOnLoadingListener(OnLoadingListener mOnLoadingListener) {
        this.mOnLoadingListener = mOnLoadingListener;
        return this;
    }

    public TableViewRefresh setTableViewRangeListener(OnTableViewRangeListener mTableViewRangeListener) {
        this.mTableViewRangeListener = mTableViewRangeListener;
        return this;
    }

    public TableViewRefresh setOnItemClickListenter(OnItemClickListenter mOnItemClickListenter) {
        this.mOnItemClickListenter = mOnItemClickListenter;
        return this;
    }

    public TableViewRefresh setOnItemLongClickListenter(OnItemLongClickListenter mOnItemLongClickListenter) {
        this.mOnItemLongClickListenter = mOnItemLongClickListenter;
        return this;
    }


    public TableViewRefresh setViewAttribute(ViewAttribute mAttribute) {
        this.mAttribute = mAttribute;
        return this;
    }


    public LinearLayout getLockHeadView() {
        return mLockHeadView;
    }

    public PRecyclerView getTableScrollView() {
        return mTableScrollView;
    }

    public ArrayList<HorizontalScrollView> getScrollViews() {
        return mScrollViews;
    }

    /**
     * 说明 数据刷新时，重新设值
     * 创建时间 2017/9/17 下午2:33
     *
     * @param mTableDatas
     */
    public void setTableDatas(ArrayList<ParentEntity> mTableDatas) {
        this.mTableDatas = mTableDatas;
        mTableFristData.clear();
        mTableColumnDatas.clear();
        mTableRowDatas.clear();
        mTableViewAdapter.notifyDataSetChanged();
    }

    public void setIsOpen(int pos, List<ParentEntity> childData) {
        if (mTableDatas != null && pos < mTableDatas.size()) {
            ParentEntity entity = getItemData(pos);
            if (entity.isOpen) {
                entity.isOpen = false;
                mTableDatas.remove(pos + 1);
            } else {
                entity.isOpen = true;
                mTableDatas.set(pos, entity);
                ParentEntity tempEntity = new ParentEntity(ParentEntity.TYPE_NROMAL);
                tempEntity.childs = childData;
                mTableDatas.add(pos + 1, tempEntity);
            }
            mTableViewAdapter.notifyDataSetChanged();
        }
    }

    public ParentEntity getItemData(int pos) {
        if (mTableDatas != null && pos < mTableDatas.size())
            return mTableDatas.get(pos);
        return null;
    }
}
