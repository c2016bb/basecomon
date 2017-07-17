package com.common.base.basecommon.Common;

/**
 * Created by User on 2017/7/17.
 */

public class CommonUtils {
    //activity 跳转参数
    public static String  getActivityKey1(Class clazz){
        return "key:"+clazz.getSimpleName()+":one";
    }
}
