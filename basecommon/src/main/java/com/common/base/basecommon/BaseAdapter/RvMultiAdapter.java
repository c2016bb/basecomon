package com.common.base.basecommon.BaseAdapter;

import android.content.Context;

import java.util.List;

/**
 * Created by User on 2017/9/6.
 */

public class RvMultiAdapter<T> extends MultiItemTypeAdapter<T> {
    public RvMultiAdapter(Context context, List<T> datas) {
        super(context, datas);
    }



}
