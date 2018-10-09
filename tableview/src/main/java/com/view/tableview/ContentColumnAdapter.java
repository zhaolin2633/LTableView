package com.view.tableview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.view.entitiy.ParentEntity;
import com.view.utils.MyLayoutManager;

import java.util.List;

/**
 * 说明
 * 创建时间 2017/9/17.
 */

public class ContentColumnAdapter extends BaseTabAapter<ParentEntity> {

    /**
     * 构造方法
     *
     * @param mContext
     * @param list
     */
    public ContentColumnAdapter(Context mContext, List<ParentEntity> list) {
        super(mContext, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == ParentEntity.TYPE_TITLE) {
            viewHolder = new UnLockViewHolder(mInflater.inflate(R.layout.content_item_header, parent, false));
        } else {
            viewHolder = new UnLockChildViewHolder(mInflater.inflate(R.layout.content_item, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ParentEntity entity = mData.get(position);
        if (getItemViewType(position) == ParentEntity.TYPE_TITLE) {
            bindView((UnLockViewHolder) holder, entity);
        } else {
            bindChildData((UnLockChildViewHolder) holder, entity.childs);
        }

    }

    @Override
    public int getItemViewType(int position) {
        ParentEntity entity = mData.get(position);
        return entity.getItemType();
    }

    public void bindChildData(UnLockChildViewHolder holder, final List<ParentEntity> childs) {

        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new UnLockViewHolder(LayoutInflater.from(mContext).inflate(R.layout.content_item_header, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                UnLockViewHolder viewHolder = (UnLockViewHolder) holder;
                bindView(viewHolder, childs.get(position));

            }

            @Override
            public int getItemCount() {
                return childs != null ? childs.size() : 0;
            }
        };
        holder.mChildRecyclerView.setAdapter(adapter);
    }


    public void bindView(UnLockViewHolder helper, final ParentEntity item) {
        final int position = helper.getAdapterPosition();
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, mAttr.mContentMaxHeight);
        helper.itemView.setLayoutParams(params);
        List<String> datas = item.itemEntity;
        LinearLayout mLinearLayout = helper.mLinearLayout;
        createView(mLinearLayout, datas);
        //添加事件
        if (mOnItemClickListenter != null) {
            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemSelectedListenter != null) {
                        mOnItemSelectedListenter.onItemSelected(v, position);
                    }
                    if (item.getItemType() == ParentEntity.TYPE_TITLE) {
                        if (mOnItemClickListenter != null) {
                            mOnItemClickListenter.onItemParentClick(v, position);
                        }
                    } else {
                        if (mOnItemClickListenter != null) {
                            mOnItemClickListenter.onItemChildClick(v, position);
                        }
                    }
                }
            });
        }
        if (mOnItemLongClickListenter != null) {
            mLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemSelectedListenter != null) {
                        mOnItemSelectedListenter.onItemSelected(v, position);
                    }
                    if (item.getItemType() == ParentEntity.TYPE_TITLE) {
                        mOnItemLongClickListenter.onItemLongParentClick(v, position);
                    } else {
                        mOnItemLongClickListenter.onItemLongChildClick(v, position);
                    }
                    return true;
                }
            });
        }
        //如果没有设置点击事件和长按事件
        if (mOnItemClickListenter == null && mOnItemLongClickListenter == null) {
            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemSelectedListenter != null) {
                        mOnItemSelectedListenter.onItemSelected(v, position);
                    }
                }
            });
            mLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemSelectedListenter != null) {
                        mOnItemSelectedListenter.onItemSelected(v, position);
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public boolean isPinnedViewType(int viewType) {
        return viewType == ParentEntity.TYPE_TITLE;
    }


    class UnLockViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;

        public UnLockViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.unlock_linearlayout);
        }
    }

    class UnLockChildViewHolder extends RecyclerView.ViewHolder {
        RecyclerView mChildRecyclerView;

        public UnLockChildViewHolder(View itemView) {
            super(itemView);
            mChildRecyclerView = (RecyclerView) itemView.findViewById(R.id.rl_childRecyclerView);
            MyLayoutManager myLayoutManager = new MyLayoutManager(mContext);
            myLayoutManager.setScrollEnabled(false);
            mChildRecyclerView.setLayoutManager(myLayoutManager);
            mChildRecyclerView.setNestedScrollingEnabled(false);
            mChildRecyclerView.setFocusableInTouchMode(false);
        }
    }

    /**
     * 构造每行数据视图
     *
     * @param linearLayout
     * @param datas
     */
    public void createView(LinearLayout linearLayout, List<String> datas) {
        linearLayout.removeAllViews();
        for (int i = 0; i < datas.size(); i++) {
            linearLayout.addView(TabViewManager.tabContentView(mContext, mAttr, datas.get(i), getViewWith(i)));
            //画分隔线
            if (i != datas.size() - 1) {
                linearLayout.addView(TabViewManager.splitContentVerticalView(mContext, mAttr));
            }
        }
    }


}
