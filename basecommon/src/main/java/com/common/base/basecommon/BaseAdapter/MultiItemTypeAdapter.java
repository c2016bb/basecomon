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
    private  boolean isAddViewType=false;//是否通过添加viewType添加数据的
    private  List<Integer>spans;


    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemAdapterClickListener mOnItemClickListener;
    protected OnItemAdapterLongClickListener mOnItemLongClickListener;

   public void  setOnItemAdapterClickListener(OnItemAdapterClickListener mOnItemAdapterClickListener){
       this.mOnItemClickListener=mOnItemAdapterClickListener;
   }
    protected void  setOnItemAdapterLongClickListener(OnItemAdapterLongClickListener mOnItemAdapterLongClickListener){
        this.mOnItemLongClickListener=mOnItemAdapterLongClickListener;
    }



    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
        spans=new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return  super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder,holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }
    public void onViewHolderCreated(ViewHolder holder,View itemView){

    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, final int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder , position,viewType);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemLongClickListener.onItemLongClick(v, viewHolder, position,viewType);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        int itemCount = mDatas.size();
        return itemCount;
    }


    public List<T> getDatas() {
        return mDatas;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        isAddViewType=true;
        spans.add(viewType);
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (isAddViewType) {
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager griManager = (GridLayoutManager) manager;
                griManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return spans.get(getItemViewType(position));
                    }
                });
            }
        }
    }
}



