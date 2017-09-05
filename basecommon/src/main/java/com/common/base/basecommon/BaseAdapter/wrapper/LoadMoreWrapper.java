package com.common.base.basecommon.BaseAdapter.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.common.base.basecommon.BaseAdapter.Base.ViewHolder;
import com.common.base.basecommon.BaseAdapter.utils.WrapperUtils;


/**
 * Created by User on 2017/5/22.
 */

public class LoadMoreWrapper<T> extends RecyclerView.Adapter<ViewHolder> {
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private RecyclerView.Adapter mInnerAdapter;
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;

    private ViewHolder mLoadMoreHolder;

    public void setLoadMoreHolder(ViewHolder mLoadMoreHolder) {
        this.mLoadMoreHolder = mLoadMoreHolder;
    }

    public ViewHolder getLoadMoreHolder() {
        return mLoadMoreHolder;
    }

    public LoadMoreWrapper(RecyclerView.Adapter mInnerAdapter) {
        this.mInnerAdapter = mInnerAdapter;
    }


    private boolean hasLoadMore()
    { //加载更多的条件
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }

    private boolean isShowLoadMore(int position)
    {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isShowLoadMore(position))
        {
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.i(TAG, "onCreateViewHolder: ");
        if (viewType == ITEM_TYPE_LOAD_MORE)
        {
            ViewHolder holder;
            if (mLoadMoreView != null)
            {
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else
            {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            }
            setLoadMoreHolder(holder);
            return holder;
        }
        return (ViewHolder) mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    private static final String TAG = "cc";
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Log.i(TAG, "onBindViewHolder: ");
        if (isShowLoadMore(position))
        {
            if (mOnLoadMoreListener != null)
            {
                mOnLoadMoreListener.onLoadMoreRequested(holder);
            }
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        Log.i(TAG, "onAttachedToRecyclerView: ");
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback()
        {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position)
            {
                if (isShowLoadMore(position))
                {
                    Log.i(TAG, "getSpanSize: 加载更多的"+layoutManager.getSpanCount());
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                {
                    Log.i(TAG, "getSpanSize: 旧的");
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }


    @Override
    public void onViewAttachedToWindow(ViewHolder holder)
    {
        Log.i(TAG, "onViewAttachedToWindow: ");
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getLayoutPosition()))
        {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(ViewHolder holder)
    {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams)
        {
            Log.i(TAG, "setFullSpan: StaggeredGridLayoutManager");
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount()
    {
        return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }


    public interface OnLoadMoreListener
    {

        void onLoadMoreRequested(ViewHolder holder);
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener)
    {
        if (loadMoreListener != null)
        {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(View loadMoreView)
    {
        mLoadMoreView = loadMoreView;
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(int layoutId)
    {
        mLoadMoreLayoutId = layoutId;
        return this;
    }
}
