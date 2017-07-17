package com.common.base.basecommon.BaseAdapter;

import android.view.View;

import com.common.base.basecommon.BaseAdapter.Base.ViewHolder;


/**
 * Created by User on 2017/7/13.
 */

public interface OnItemAdapterClickListener {
    void onItemClick(View view, ViewHolder holder, int position, int viewType);
}
