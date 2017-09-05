package com.common.base.basecommon.BaseAdapter.listener;

import android.view.View;

import com.common.base.basecommon.BaseAdapter.Base.ViewHolder;


/**
 * Created by User on 2017/7/13.
 */

public interface OnItemAdapterLongClickListener {
    boolean onItemLongClick(View view, ViewHolder holder, int position, int viewType);
}
