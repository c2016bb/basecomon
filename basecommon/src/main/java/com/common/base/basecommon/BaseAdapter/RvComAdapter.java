package com.common.base.basecommon.BaseAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.common.base.basecommon.BaseAdapter.Base.ItemViewDelegate;
import com.common.base.basecommon.BaseAdapter.Base.ViewHolder;
import com.common.base.basecommon.BaseAdapter.Decoration.DefaltDividerItemDecoration;
import com.common.base.basecommon.BaseAdapter.Decoration.ItemDecorationType;
import com.common.base.basecommon.BaseAdapter.listener.InitViewCallBack;
import com.common.base.basecommon.BaseAdapter.listener.OnEmptyListener;
import com.common.base.basecommon.BaseAdapter.listener.OnItemAdapterClickListener;
import com.common.base.basecommon.BaseAdapter.listener.OnLoadMoreListener;
import com.common.base.basecommon.R;

import java.util.List;

/**
 * Created by User on 2017/8/17.
 */

public class RvComAdapter<T> extends MultiItemTypeAdapter<T> {
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;
    public static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 10;
    private Context context;
    private int mLayoutId;
//    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemDecoration decoration;
    private int spanCount;

    public void setmLayoutId(int mLayoutId) {
        this.mLayoutId = mLayoutId;
    }

    public void setDecoration(RecyclerView.ItemDecoration decoration) {
        this.decoration = decoration;
    }
    private OnEmptyListener onEmptyListener;

    public static final int LINEARLAYOUTMANAGER = 1;

    public static final int GRIDLAYOUTMANAGER = 2;

    private int mEmptyLayoutId;

    private int layoutManagerType;

    private int mOrientation = OrientationHelper.VERTICAL;

    public OnItemAdapterClickListener onItemClickListener;

    public OnLoadMoreListener onLoadMoreListener;
    private  int itemDecorationType;
    private Drawable dividerDrawable;

    public void setDividerDrawable(Drawable dividerDrawable) {
        this.dividerDrawable = dividerDrawable;
    }

    public void setItemDecorationType(int itemDecorationType) {
        this.itemDecorationType = itemDecorationType;
    }

    public void setmEmptyLayoutId(int mEmptyLayoutId) {
        this.mEmptyLayoutId = mEmptyLayoutId;
    }

    private int mLoadMoreLayoutId;

    private RecyclerView mRecyclerView;

    public void setOnEmptyListener(OnEmptyListener onEmptyListener) {
        this.onEmptyListener = onEmptyListener;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    public void setLayoutManagerType(int layoutManagerType) {
        this.layoutManagerType = layoutManagerType;
    }

    public void setOrientation(int mOrientation) {
        this.mOrientation = mOrientation;
    }

    public void setOnItemClickListener(OnItemAdapterClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setmLoadMoreLayoutId(int mLoadMoreLayoutId) {
        this.mLoadMoreLayoutId = mLoadMoreLayoutId;
    }

    public RvComAdapter(Context context, List<T> datas) {
        super(context, datas);
        this.context=context;
    }

    private void createAdapter() {
        if (layoutManagerType==0||layoutManagerType == LINEARLAYOUTMANAGER) {
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
        if (onItemClickListener != null) {
            setOnItemAdapterClickListener(onItemClickListener);
        }
        mRecyclerView.setAdapter(this);
        if (decoration!=null){
            mRecyclerView.addItemDecoration(decoration);
            if (decoration instanceof DefaltDividerItemDecoration){
                ((DefaltDividerItemDecoration) decoration).setOrientation(mOrientation);
                if (dividerDrawable!=null) {
                    ((DefaltDividerItemDecoration) decoration).setDrawable(dividerDrawable);
                }
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        Log.i(TAG, "getItemViewType: position---." + position);
        if (isEmpty) {
            return ITEM_TYPE_EMPTY;
        }

        if (isLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: viewType---->" + viewType);
        if (viewType == ITEM_TYPE_EMPTY) {
            Log.i(TAG, "onCreateViewHolder: mEmptyLayoutId-->" + mEmptyLayoutId);
            ViewHolder hodler = ViewHolder.createViewHolder(this.mContext, parent, mEmptyLayoutId);
            if (onEmptyListener != null) {
                onEmptyListener.onEmpty(hodler);
            }
            return hodler;
        }
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            ViewHolder hodler = ViewHolder.createViewHolder(this.mContext, parent, mLoadMoreLayoutId);
            if (onLoadMoreListener != null) {
                onLoadMoreListener.onLoadMore(hodler);
            }
            return hodler;
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isEmpty) {
            return;
        }
        if (!isLoadMore(position)) {
            super.onBindViewHolder(holder, position);
        }
    }

    private boolean isLoadMore(int position) {
        return hasLoadMore() && position >= getItemCount() - 1 ? true : false;
    }

    private boolean hasLoadMore() { //加载更多的条件
        return mLoadMoreLayoutId != 0;
    }

    private boolean isEmpty(int count) {
        return hasEmpty() && count == 0;
    }

    private boolean hasEmpty() {
        return mEmptyLayoutId != 0;
    }

    private boolean isEmpty = false;


    public RvComAdapter into(RecyclerView recyclerView, final InitViewCallBack initView) {
        this.mRecyclerView = recyclerView;
        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return mLayoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                if (initView != null) {
                    initView.convert(holder, t, position);
                }
            }
        });
        createAdapter();
        return this;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            GridLayoutManager griManager = (GridLayoutManager) manager;
            final int spanSize = griManager.getSpanCount();
            griManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) {
                    if (isLoadMore(position)) {
                        return spanSize;
                    }
                    return 1;
                }
            });
        }
    }

    private static final String TAG = "cc";

    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        if (isEmpty(count)||hasLoadMore()||itemDecorationType== ItemDecorationType.NOLASTITEM){
            if(decoration instanceof DefaltDividerItemDecoration){
                ((DefaltDividerItemDecoration) decoration).setItemType(ItemDecorationType.NOLASTITEM);
            }
        }
        if (isEmpty(count)) {
            Log.i(TAG, "getItemCount: 1");
            isEmpty = true;
            return 1;
        }
        isEmpty = false;
        if (count > 0) {
            return hasLoadMore() ? count + 1 : count;
        } else {
            return 0;
        }

    }

//    public static Builder from(Context context, final @LayoutRes int mLayoutId, List<T> datas){
//     return new Builder(context,mLayoutId,datas);
//    }


    public static class Builder<T> {
        private Context context;
        private int mLayoutId;
        private List<T> mDatas;
        private OnLoadMoreListener onLoadMoreListener;
        private int mLoadMoreLayoutId;
        private OnItemAdapterClickListener onItemAdapterClickListener;
        private int layoutManagerType=0;
        private int spanCount=2;
        private int mEmptyLayoutId;
        private OnEmptyListener onEmptyListener;
        private RecyclerView.ItemDecoration decoration;
        private int mOrientation = OrientationHelper.VERTICAL;
        private  int itemDecorationType=0;
        private Drawable dividerDrawable;

        public void setDividerDrawable(Drawable dividerDrawable) {
            this.dividerDrawable = dividerDrawable;
        }

        public Builder setOrientation(int mOrientation) {
            this.mOrientation = mOrientation;
            return this;
        }

        public Builder setDecoration(RecyclerView.ItemDecoration decoration) {
            this.decoration = decoration;
            return this;
        }
        public Builder setDefaltDecoration() {
            this.decoration = new DefaltDividerItemDecoration(context);
            return this;
        }
        public Builder setDefaltDecoration(int itemDecorationType,@DrawableRes int drawableId) {
            this.decoration = new DefaltDividerItemDecoration(context);
            if (itemDecorationType>0) {
                this.itemDecorationType = itemDecorationType;
            }
            if (drawableId>0) {
                try {
                    this.dividerDrawable = context.getResources().getDrawable(drawableId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return this;
        }
        public Builder setOnEmptyListener(OnEmptyListener onEmptyListener) {
            this.onEmptyListener = onEmptyListener;
            return this;
        }

        public Builder setEmptyLayoutId(@LayoutRes int mEmptyLayoutId) {
            this.mEmptyLayoutId = mEmptyLayoutId;
            return this;
        }

        public Builder(Context context, @LayoutRes int mLayoutId, List<T> mDatas) {
            this.context = context;
            this.mLayoutId = mLayoutId;
            this.mDatas = mDatas;
        }

        public Builder setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
            this.mLoadMoreLayoutId = R.layout.layout_more_progress_text;
            return this;
        }

        public Builder setOnItemAdapterClickListener(OnItemAdapterClickListener onItemAdapterClickListener) {
            this.onItemAdapterClickListener = onItemAdapterClickListener;
            return this;
        }


        public Builder setLayoutManagerType(int layoutManagerType) {
            this.layoutManagerType = layoutManagerType;
            return this;
        }

        public Builder setLayoutManagerType(int layoutManagerType, int spanCount) {
            this.layoutManagerType = layoutManagerType;
            this.spanCount = spanCount;
            return this;
        }

        public RvComAdapter into(RecyclerView recyclerView, InitViewCallBack initView) {
            RvComAdapter rvComAdapter = new RvComAdapter(context, mDatas);
            rvComAdapter.setOnLoadMoreListener(onLoadMoreListener);
            rvComAdapter.setmLoadMoreLayoutId(mLoadMoreLayoutId);
            rvComAdapter.setLayoutManagerType(layoutManagerType);
            rvComAdapter.setSpanCount(spanCount);
            rvComAdapter.setOnItemAdapterClickListener(onItemAdapterClickListener);
            rvComAdapter.setmLayoutId(mLayoutId);
            rvComAdapter.setmEmptyLayoutId(mEmptyLayoutId);
            rvComAdapter.setOnEmptyListener(onEmptyListener);
            rvComAdapter.setDecoration(decoration);
            rvComAdapter.setItemDecorationType(itemDecorationType);
            rvComAdapter.setOrientation(mOrientation);
            rvComAdapter.setDividerDrawable(dividerDrawable);
            return rvComAdapter.into(recyclerView, initView);
        }

    }
}
