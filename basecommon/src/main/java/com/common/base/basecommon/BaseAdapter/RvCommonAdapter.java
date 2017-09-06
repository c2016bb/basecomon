package com.common.base.basecommon.BaseAdapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.*;
import android.util.Log;


import com.common.base.basecommon.BaseAdapter.Base.ItemViewDelegate;
import com.common.base.basecommon.BaseAdapter.Base.RvViewHolder;
import com.common.base.basecommon.BaseAdapter.listener.InitViewCallBack;
import com.common.base.basecommon.BaseAdapter.listener.OnItemAdapterClickListener;
import com.common.base.basecommon.BaseAdapter.listener.OnLoadMoreListener;
import com.common.base.basecommon.BaseAdapter.wrapper.LoadMoreWrapper;
import com.common.base.basecommon.R;

import java.util.List;

/**
 * 此 recycleView adapter 默认为无加载更多   垂直方向
 * OrientationHelper.VERTICAL
 *
 */

public class RvCommonAdapter<T> extends MultiItemTypeAdapter<T> {
    private InitViewCallBack callBack;
    private int layoutId;
    private RecyclerView mRecyclerView;


    public RvCommonAdapter(Context context, final @LayoutRes int layoutId, List<T> datas, final InitViewCallBack callBack, final OnItemAdapterClickListener onItemClickListener, RecyclerView mRecyclerView) {
        super(context, datas);
        this.layoutId = layoutId;
        this.callBack = callBack;
        this.mRecyclerView = mRecyclerView;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(RvViewHolder holder, T t, int position) {
                callBack.convert(holder, t, position);
            }
        });

        if (onItemClickListener != null) {
            setOnItemAdapterClickListener(onItemClickListener);
        }
    }


    public void notifyDataSetChangedRv() { //更新数据
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public RvViewHolder getLoadMoreView(){
        if (mRecyclerView.getAdapter() instanceof LoadMoreWrapper){
            return  ((LoadMoreWrapper) mRecyclerView.getAdapter()).getLoadMoreHolder();
        }
        return null;
    }

    public static class Builder<T> {
        private Context context;
        private int mLayoutId;
        private List<T> mDatas;
        private RecyclerView.LayoutManager layoutManager;
       private RecyclerView.ItemDecoration decoration;

        public Builder setDecoration(RecyclerView.ItemDecoration decoration) {
            this.decoration = decoration;
            return this;
        }



        private int spanCount = 1;

        public  static  final int LINEARLAYOUTMANAGER=1;

        public  static  final int GRIDLAYOUTMANAGER=2;
        private  int layoutManagerType=0;

        private int mOrientation = OrientationHelper.VERTICAL;

        public OnItemAdapterClickListener onItemClickListener;

        public OnLoadMoreListener onLoadMoreListener;

        private int mLoadMoreLayoutId ;

        protected LoadMoreWrapper mLoadMoreAdapter;

        public Builder setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
            this.mLoadMoreLayoutId= R.layout.layout_more_progress_text;
            return  this;
        }

        public Builder setOnItemAdapterClickListener(OnItemAdapterClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
              return  this;
        }

        public Builder setLayoutManagerType(int layoutManagerType) {
            this.layoutManagerType=layoutManagerType;
            return this;
        }

        public Builder setLayoutManagerType(int layoutManagerType, int spanCount) {
            this.layoutManagerType=layoutManagerType;
            this.spanCount = spanCount;
            return this;
        }

        public Builder(Context context,@LayoutRes int mLayoutId, List<T> mDatas) {
            this.context = context;
            this.mLayoutId = mLayoutId;
            this.mDatas = mDatas;
        }




        public Builder setLoadMoreListener(OnLoadMoreListener onLoadMoreListener,@LayoutRes int mLoadMoreLayoutId) {
            this.onLoadMoreListener = onLoadMoreListener;
            this.mLoadMoreLayoutId = mLoadMoreLayoutId;
            return this;
        }

        public Builder setOrientation(int mOrientation) {
            this.mOrientation = mOrientation;
            return this;
        }

        private static final String TAG = "cc";
        public RvCommonAdapter createAdapter(RecyclerView mRecyclerView, final InitViewCallBack<T> callBack) {

            if (layoutManagerType == 0 || layoutManagerType==LINEARLAYOUTMANAGER) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(mOrientation);
                mRecyclerView.setLayoutManager(linearLayoutManager);
            } else if (layoutManagerType == GRIDLAYOUTMANAGER) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
                gridLayoutManager.setOrientation(mOrientation);
                mRecyclerView.setLayoutManager(gridLayoutManager);
            } else {
                throw new IllegalArgumentException("LayoutManager must is LinearLayoutManager or GridLayoutManager ");
            }
            final RvCommonAdapter sinpleRVAdapter = new RvCommonAdapter(context, mLayoutId, mDatas, callBack, onItemClickListener, mRecyclerView);
            if (onLoadMoreListener != null) {
                Log.i(TAG, "createAdapter: 加载更多");
                mLoadMoreAdapter = new LoadMoreWrapper(sinpleRVAdapter);
                mLoadMoreAdapter.setLoadMoreView(mLoadMoreLayoutId);
                mLoadMoreAdapter.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested(RvViewHolder holder) {
                        if (onLoadMoreListener != null) {
                            Log.i(TAG, "onLoadMoreRequested: 加载更多");
                            onLoadMoreListener.onLoadMore(holder);
                        }
                    }
                });
                mRecyclerView.setAdapter(mLoadMoreAdapter);
            } else {
                mRecyclerView.setAdapter(sinpleRVAdapter);
            }
            if (decoration!=null) {
                mRecyclerView.addItemDecoration(decoration);
            }
            return sinpleRVAdapter;
        }
    }
}
