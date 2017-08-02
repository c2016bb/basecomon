package com.common.base.basecommon.BaseAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.common.base.basecommon.BaseAdapter.Base.ItemViewDelegate;
import com.common.base.basecommon.BaseAdapter.Base.ItemViewDelegateManager;
import com.common.base.basecommon.BaseAdapter.Base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2017/5/19.
 */

public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;
    private boolean isAddViewType = false;
    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemAdapterClickListener mOnItemClickListener;
    protected OnItemAdapterLongClickListener mOnItemLongClickListener;

    public void setOnItemAdapterClickListener(OnItemAdapterClickListener mOnItemAdapterClickListener) {
        this.mOnItemClickListener = mOnItemAdapterClickListener;
    }

    protected void setOnItemAdapterLongClickListener(OnItemAdapterLongClickListener mOnItemAdapterLongClickListener) {
        this.mOnItemLongClickListener = mOnItemAdapterLongClickListener;
    }

    public MultiItemTypeAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
        this.mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    public int getItemViewType(int position) {
        return !this.useItemViewDelegateManager()?super.getItemViewType(position):this.mItemViewDelegateManager.getItemViewType(this.mDatas.get(position), position);
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = this.mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(this.mContext, parent, layoutId);
        this.onViewHolderCreated(holder, holder.getConvertView());
        this.setListener(parent, holder, viewType);
        return holder;
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {
    }

    public void convert(ViewHolder holder, T t) {
        this.mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    protected void setListener(ViewGroup parent, final ViewHolder viewHolder, final int viewType) {
        if(this.isEnabled(viewType)) {
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(MultiItemTypeAdapter.this.mOnItemClickListener != null) {
                        int position = viewHolder.getAdapterPosition();
                        MultiItemTypeAdapter.this.mOnItemClickListener.onItemClick(v, viewHolder, position, viewType);
                    }

                }
            });
            viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    if(MultiItemTypeAdapter.this.mOnItemClickListener != null) {
                        int position = viewHolder.getAdapterPosition();
                        return MultiItemTypeAdapter.this.mOnItemLongClickListener.onItemLongClick(v, viewHolder, position, viewType);
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        this.convert(holder, this.mDatas.get(position));
    }

    public int getItemCount() {
        int itemCount = this.mDatas.size();
        return itemCount;
    }

    public List<T> getDatas() {
        return this.mDatas;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        this.mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        this.isAddViewType = true;
        this.mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return this.mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(this.isAddViewType) {
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if(manager instanceof GridLayoutManager) {
                GridLayoutManager griManager = (GridLayoutManager)manager;
                griManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    public int getSpanSize(int position) {
                        return MultiItemTypeAdapter.this.getItemViewType(position);
                    }
                });
            }
        }

    }
}



