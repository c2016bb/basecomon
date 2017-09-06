package com.common.base.basecommon.BaseAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;


import com.common.base.basecommon.BaseAdapter.Base.ItemViewDelegate;
import com.common.base.basecommon.BaseAdapter.Base.RvViewHolder;
import com.common.base.basecommon.BaseAdapter.listener.OnItemAdapterClickListener;

import java.util.List;

/**
 * Created by User on 2017/5/19.
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public CommonAdapter(final Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        setOnItemAdapterClickListener(new OnItemAdapterClickListener() {
            @Override
            public void onItemClick(View view, RvViewHolder holder, int position, int viewType) {
                CommonAdapter.this.onItemClick(view,holder,position);
            }
        });

        addItemViewDelegate(new ItemViewDelegate<T>()
        {

            @Override
            public int getItemViewLayoutId()
            {
//                LogUtils.D("layoutId");
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
//                LogUtils.D("isForViewType");
                return true;
            }

            @Override
            public void convert(RvViewHolder holder, T t, int position)
            {
//                LogUtils.D("convert");
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }
    protected abstract void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

    protected abstract void convert(RvViewHolder holder, T t, int position);
}
