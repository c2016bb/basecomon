package com.common.base.basecommon.BaseAdapter.Decoration;

/**
 * Created by User on 2017/9/4.
 */

public interface DecoratioDelegete {

    boolean isForDivider(int position);
    int getItemLeftWidth();
    int getItemTopWidth();
    int getItemRightWidth();
    int getItemBottomWidth();

}
