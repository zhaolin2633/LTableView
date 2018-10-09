package com.view.tableview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.view.entitiy.ParentEntity;
import com.view.tableview.listener.OnItemClickListenter;
import com.view.tableview.listener.OnItemLongClickListenter;
import com.view.tableview.listener.OnTableViewCreatedCompletedListener;
import com.view.tableview.listener.OnTableViewListener;
import com.view.tableview.listener.OnTableViewRangeListener;
import com.view.pinnedsection.PinnedHeaderIClicktemDecoration;
import com.view.pinnedsection.callback.OnHeaderClickAdapter;
import com.view.utils.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明
 * 创建时间 2017/9/17.
 */

public class TableViewAdapter extends RecyclerView.Adapter<TableViewAdapter.TableViewHolder> {
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 表格数据
     */
    private ArrayList<ParentEntity> mTableDatas;
    /**
     * 标题栏宽度集合
     */
    private List<Integer> titleWiths;
    /**
     * 表格横向滚动监听事件
     */
    private OnTableViewListener mTableViewListener;

    /**
     * 表格横向滚动到边界监听事件
     */
    private OnTableViewRangeListener mTableViewRangeListener;

    /**
     * Item点击事件
     */
    private OnItemClickListenter mOnItemClickListenter;

    /**
     * Item长按事件
     */
    private OnItemLongClickListenter mOnItemLongClickListenter;

    /**
     * 表格视图加载完成监听事件
     */
    private OnTableViewCreatedCompletedListener mOnTableViewCreatedListener;


    /**
     * 锁定视图Adapter
     */
    private LeftColumnAdapter mLockAdapter;

    /**
     * 未锁定视图Adapter
     */
    private ContentColumnAdapter mUnLockAdapter;

    private TableViewHolder mTableViewHolder;

    private ViewAttribute mAttr;

    /**
     * 构造方法
     *
     * @param mContext
     * @param mTableDatas
     */
    public TableViewAdapter(Context mContext, ArrayList<ParentEntity> mTableDatas) {
        this.mContext = mContext;
        this.mTableDatas = mTableDatas;
        if (mAttr == null) {
            mAttr = new ViewAttribute(mContext);
        }
    }
    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mTableViewHolder = new TableViewHolder(LayoutInflater.from(mContext).inflate(R.layout.table_content_view, null));
        if (mOnTableViewCreatedListener != null) {
            mOnTableViewCreatedListener.onTableViewCreatedCompleted(mTableViewHolder.mScrollView);
        }
        return mTableViewHolder;
    }

    @Override
    public void onBindViewHolder(final TableViewHolder holder, int position) {
        //构造锁定视图
        holder.mLockRecyclerView.setVisibility(View.VISIBLE);
        if (mLockAdapter == null) {
            mLockAdapter = new LeftColumnAdapter(mContext, mTableDatas);
            mLockAdapter.setViewAttribute(mAttr);
            if (mOnItemClickListenter != null) {
                mLockAdapter.setOnItemClickListenter(mOnItemClickListenter);
            }
            if (mOnItemLongClickListenter != null) {
                mLockAdapter.setOnItemLongClickListenter(mOnItemLongClickListenter);
            }
            PinnedHeaderIClicktemDecoration decoration = new PinnedHeaderIClicktemDecoration.Builder(ParentEntity.TYPE_TITLE)
                    .setClickIds(R.id.lock_text).disableHeaderClick(false).setHeaderClickListener(clickAdapter).create();
            holder.mLockRecyclerView.addItemDecoration(decoration);
            holder.mLockRecyclerView.setAdapter(mLockAdapter);
        } else {
            mLockAdapter.notifyDataSetChanged();
        }

        //构造主表格视图
        if (mUnLockAdapter == null) {
            mUnLockAdapter = new ContentColumnAdapter(mContext, mTableDatas);
            mUnLockAdapter.setViewAttribute(mAttr);
            mUnLockAdapter.setViewWiths(titleWiths);
            if (mOnItemClickListenter != null) {
                mUnLockAdapter.setOnItemClickListenter(mOnItemClickListenter);
            }
            if (mOnItemLongClickListenter != null) {
                mUnLockAdapter.setOnItemLongClickListenter(mOnItemLongClickListenter);
            }
            PinnedHeaderIClicktemDecoration decoration = new PinnedHeaderIClicktemDecoration.Builder(ParentEntity.TYPE_TITLE)
                    .setClickIds(R.id.unlock_linearlayout).disableHeaderClick(true).setHeaderClickListener(clickAdapter).create();
            holder.mMainRecyclerView.addItemDecoration(decoration);
            holder.mMainRecyclerView.setAdapter(mUnLockAdapter);
        } else {
            mUnLockAdapter.notifyDataSetChanged();
        }
    }

    OnHeaderClickAdapter clickAdapter = new OnHeaderClickAdapter() {

        @Override
        public void onHeaderClick(View view, int id, int position) {
            if (mOnItemClickListenter != null) {
                mOnItemClickListenter.onItemParentClick(view, position);
            }

        }
    };

    @Override
    public int getItemCount() {
        return 1;
    }

    public class TableViewHolder extends RecyclerView.ViewHolder {
        RecyclerView mLockRecyclerView;
        RecyclerView mMainRecyclerView;
        CustomHorizontalScrollView mScrollView;

        public TableViewHolder(View itemView) {
            super(itemView);
            mLockRecyclerView = (RecyclerView) itemView.findViewById(R.id.lock_recyclerview);
            mMainRecyclerView = (RecyclerView) itemView.findViewById(R.id.main_recyclerview);
            mLockRecyclerView.setFocusable(false);
            mMainRecyclerView.setFocusable(false);
            MyLayoutManager locklayoutManager = new MyLayoutManager(mContext);
            locklayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            locklayoutManager.setReverseLayout(false);
            locklayoutManager.setScrollEnabled(true);
            mLockRecyclerView.setLayoutManager(locklayoutManager);
            MyLayoutManager mainlayoutManager = new MyLayoutManager(mContext);
            mainlayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mainlayoutManager.setReverseLayout(false);
            mainlayoutManager.setScrollEnabled(true);
            mMainRecyclerView.setLayoutManager(mainlayoutManager);
            mScrollView = (CustomHorizontalScrollView) itemView.findViewById(R.id.lockScrollView_parent);
            mScrollView.setOnScrollChangeListener(new CustomHorizontalScrollView.onScrollChangeListener() {
                @Override
                public void onScrollChanged(HorizontalScrollView scrollView, int x, int y) {
                    if (mTableViewListener != null) {
                        mTableViewListener.onTableViewScrollChange(x, y);
                    }
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
            mLockRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                        mMainRecyclerView.scrollBy(0, dy);
                    }
                }
            });
            mMainRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                        mLockRecyclerView.scrollBy(0, dy);
                    }
                }
            });
            mMainRecyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mLockRecyclerView.stopScroll();
                    return false;
                }
            });
            mLockRecyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mMainRecyclerView.stopScroll();
                    return false;
                }
            });
        }
    }




    public void setHorizontalScrollView(OnTableViewListener mTableViewListener) {
        this.mTableViewListener = mTableViewListener;
    }

    public void setOnTableViewCreatedListener(OnTableViewCreatedCompletedListener mOnTableViewCreatedListener) {
        this.mOnTableViewCreatedListener = mOnTableViewCreatedListener;
    }

    public void setTableViewRangeListener(OnTableViewRangeListener mTableViewRangeListener) {
        this.mTableViewRangeListener = mTableViewRangeListener;
    }

    public void setOnItemClickListenter(OnItemClickListenter mOnItemClickListenter) {
        this.mOnItemClickListenter = mOnItemClickListenter;
    }

    public void setOnItemLongClickListenter(OnItemLongClickListenter mOnItemLongClickListenter) {
        this.mOnItemLongClickListenter = mOnItemLongClickListenter;
    }

    public void setTitleWiths(List<Integer> titleWiths) {
        this.titleWiths = titleWiths;
    }

    public void setViewAttribute(ViewAttribute mAttr) {
        this.mAttr = mAttr;
    }
}
