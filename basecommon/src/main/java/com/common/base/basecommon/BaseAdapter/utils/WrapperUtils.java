package com.common.base.basecommon.BaseAdapter.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Created by zhy on 16/6/28.
 */
public class WrapperUtils
{
    private static final String TAG = "cc";
    public interface SpanSizeCallback
    {
        int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position);
    }

    public static void onAttachedToRecyclerView(RecyclerView.Adapter innerAdapter, RecyclerView recyclerView, final SpanSizeCallback callback)
    {
        innerAdapter.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {
            Log.i(TAG, "onAttachedToRecyclerView: 属于GridLayoutManager");
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
            {
                @Override
                public int getSpanSize(int position)
                {
                    return callback.getSpanSize(gridLayoutManager, spanSizeLookup, position);
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }else{
            Log.i(TAG, "onAttachedToRecyclerView: 不属于GridLayoutManager");
            
        }
    }

    public static void setFullSpan(RecyclerView.ViewHolder holder)
{
    ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

    if (lp != null
            && lp instanceof StaggeredGridLayoutManager.LayoutParams)
    {

        StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

        p.setFullSpan(true);
    }
}
}
