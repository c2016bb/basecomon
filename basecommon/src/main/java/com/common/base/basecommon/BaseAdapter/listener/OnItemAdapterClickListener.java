package com.common.base.basecommon.BaseAdapter.listener;

import android.view.View;

import com.common.base.basecommon.BaseAdapter.Base.RvViewHolder;


/**
 * Created by User on 2017/7/13.
 */

public interface OnItemAdapterClickListener {
    void onItemClick(View view, RvViewHolder holder, int position, int viewType);
}
