package com.common.base.basecommon.baseDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.common.base.basecommon.R;

import java.util.List;

/**
 * Created by User on 2017/6/12.
 */

public abstract class FullCommonDialog extends Dialog {
    private View dialogView;
    private int layoutId;
    private Context context;
    private Window window;
    private SparseArray<View> mViews;


    public FullCommonDialog(Context context, int layoutId, int animationStyle) {
      super(context, R.style.commondialog);
        this.context = context;
        this.layoutId = layoutId;
        mViews = new SparseArray<View>();
        onitView(animationStyle);
    }

    private void onitView(int animationStyle) {
        dialogView = LayoutInflater.from(context).inflate(layoutId, null);
        //获得dialog的window窗口
        Window window = getWindow();

        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
//        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        if (animationStyle>=0) {
            window.setWindowAnimations(animationStyle);
        }
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        setContentView(dialogView);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onitView(dialogView);
    }

    public abstract void onitView(View view);


    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {

    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnSureClickListener {
        void onClick(View v);
    }

    public OnSureClickListener onSureClickListener;

    public void setOnSureClickListener(OnSureClickListener onSureClickListener) {
        this.onSureClickListener = onSureClickListener;
    }


    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = dialogView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

}
