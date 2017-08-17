package com.common.base.basecommon.BaseAdapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.common.base.basecommon.BaseAdapter.Base.ViewHolder;
import com.common.base.basecommon.BaseAdapter.wrapper.LoadMoreWrapper;
import com.common.base.basecommon.R;

import java.util.List;

/**
 * Created by User on 2017/8/17.
 */

public class RvComAdapter<T> extends MultiItemTypeAdapter<T> {
    public RvComAdapter(Context context, List<T> datas) {
        super(context, datas);
    }


    public void from(Context context, final @LayoutRes int layoutId, List<T> datas){
      new Builder(context,layoutId,datas);
    }

  public   class Builder {
        private Context context;
        private int mLayoutId;
        private List<T> mDatas;

        public Builder(Context context,@LayoutRes int mLayoutId, List<T> mDatas) {
            this.context = context;
            this.mLayoutId = mLayoutId;
            this.mDatas = mDatas;
        }
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

        public Builder setLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
            this.mLoadMoreLayoutId= R.layout.layout_more_progress_text;
            return this;
        }


        public Builder setLoadMoreListener(OnLoadMoreListener onLoadMoreListener, @LayoutRes int mLoadMoreLayoutId) {
            this.onLoadMoreListener = onLoadMoreListener;
            this.mLoadMoreLayoutId = mLoadMoreLayoutId;
            return this;
        }

        public Builder setOrientation(int mOrientation) {
            this.mOrientation = mOrientation;
            return this;
        }

      public  void intoRecycleView(RecyclerView recyclerView){






      }


    }

}
