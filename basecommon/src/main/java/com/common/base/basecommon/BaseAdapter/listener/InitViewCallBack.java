package com.common.base.basecommon.BaseAdapter.listener;

import com.common.base.basecommon.BaseAdapter.Base.ViewHolder;

/**
 * Created by User on 2017/8/21.
 */

public interface InitViewCallBack<T> {
    void convert(ViewHolder holder, T t, int position);
}
