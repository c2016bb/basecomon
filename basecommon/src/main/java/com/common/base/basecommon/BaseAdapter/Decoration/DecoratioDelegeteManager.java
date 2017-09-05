package com.common.base.basecommon.BaseAdapter.Decoration;

import android.support.v4.util.SparseArrayCompat;

import com.common.base.basecommon.BaseAdapter.Base.ItemViewDelegate;

/**
 * Created by User on 2017/9/4.
 */

public class DecoratioDelegeteManager {

    SparseArrayCompat<DecoratioDelegete> delegates = new SparseArrayCompat();

    public int getDecoratioDelegeteCount()
    {
        return delegates.size();
    }

    public DecoratioDelegeteManager addDelegate(DecoratioDelegete delegate)
    {
        int viewType = delegates.size();
        if (delegate != null)
        {
            delegates.put(viewType, delegate);
            viewType++;
        }
        return this;
    }

    public DecoratioDelegete getItemType(int position)
    {
        int delegatesCount = delegates.size();
//        LogUtils.D("delegatesCount--->"+delegatesCount);
        for (int i = delegatesCount - 1; i >= 0; i--)
        {
            DecoratioDelegete delegate = delegates.valueAt(i);
//            LogUtils.D("delegate--->"+delegate.toString());
//            LogUtils.D("delegate.isForViewType( item, position)---->"+delegate.isForViewType( item, position));
            if (delegate.isForDivider(position))
            {
//                LogUtils.D("delegates.keyAt(i)---->"+delegates.keyAt(i));
                return delegates.valueAt(i); //通过位置，查找键的值
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }
}
