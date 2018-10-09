package com.view.tableview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.view.entitiy.ParentEntity;
import com.view.utils.MyLayoutManager;

import java.util.List;

/**
 * 说明
 * 创建时间 2017/9/17.
 */

public class LeftColumnAdapter extends BaseTabAapter<ParentEntity> {


    public LeftColumnAdapter(Context mContext, List<ParentEntity> list) {
        super(mContext, list);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == ParentEntity.TYPE_TITLE) {
            viewHolder = new LockViewHolder(LayoutInflater.from(mContext).inflate(R.layout.left_item_header, parent, false));
        } else {
            viewHolder = new LockChildViewHolder(LayoutInflater.from(mContext).inflate(R.layout.left_item, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ParentEntity entity = mData.get(position);
        if (getItemViewType(position) == ParentEntity.TYPE_TITLE) {
            bindData((LockViewHolder) holder, entity);
        } else {
            bindChildData((LockChildViewHolder) holder, entity.childs);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ParentEntity entity = mData.get(position);
        return entity.getItemType();
    }

    public void bindChildData(LockChildViewHolder holder, final List<ParentEntity> childs) {
        holder.mChildRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new LockViewHolder(LayoutInflater.from(mContext).inflate(R.layout.left_item_header, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                LockViewHolder viewHolder = (LockViewHolder) holder;
                viewHolder.mIvDown.setVisibility(View.GONE);
                bindData(viewHolder, childs.get(position));
            }

            @Override
            public int getItemCount() {
                return childs != null ? childs.size() : 0;
            }
        });
    }

    public void bindData(LockViewHolder holder, final ParentEntity item) {
        final int position = holder.getAdapterPosition();
        ImageView mIvDown = holder.mIvDown;
        TextView mTextView = holder.mTextView;
        LinearLayout mLinearLayout = holder.mLinearLayout;
        if (mAttr.leftContentDirectionImgResId > 0) {
            mIvDown.setVisibility(View.VISIBLE);
            mIvDown.setImageResource(mAttr.leftContentDirectionImgResId);
        } else {
            mIvDown.setVisibility(View.GONE);
        }
        mTextView.setText(item.name);
        mTextView.setTextSize(mAttr.mContentTextViewSize);
        mTextView.setTextColor(mAttr.mContentTextColor);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) mLinearLayout.getLayoutParams();
        layoutParams.width = mAttr.mHeaderMaxWidth;
        layoutParams.height = mAttr.mContentMaxHeight;
        mLinearLayout.setGravity(Gravity.CENTER);
        mLinearLayout.setLayoutParams(layoutParams);
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

    class LockViewHolder extends RecyclerView.ViewHolder {
        ImageView mIvDown;
        TextView mTextView;
        LinearLayout mLinearLayout;


        public LockViewHolder(View itemView) {
            super(itemView);
            mIvDown = (ImageView) itemView.findViewById(R.id.iv_down);
            mTextView = (TextView) itemView.findViewById(R.id.lock_text);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.lock_linearlayout);
        }
    }

    class LockChildViewHolder extends RecyclerView.ViewHolder {
        RecyclerView mChildRecyclerView;

        public LockChildViewHolder(View itemView) {
            super(itemView);
            mChildRecyclerView = (RecyclerView) itemView.findViewById(R.id.rl_childRecyclerView);
            MyLayoutManager myLayoutManager = new MyLayoutManager(mContext);
            myLayoutManager.setScrollEnabled(false);
            mChildRecyclerView.setLayoutManager(myLayoutManager);
            mChildRecyclerView.setNestedScrollingEnabled(false);
            mChildRecyclerView.setFocusableInTouchMode(false);
        }
    }
}
