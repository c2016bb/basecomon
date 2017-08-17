package com.common.base.basecommon.BaseAdapter.Base;


import android.support.annotation.LayoutRes;

/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T>
{

   @LayoutRes int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
