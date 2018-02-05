package com.common.base.basecommon.BaseAdapter.Base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.common.base.basecommon.R;


/**
 * Created by User on 2017/5/19.
 */

public class RvViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public RvViewHolder(Context context, View itemView)
    {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }


    public static RvViewHolder createViewHolder(Context context, View itemView)
    {
        RvViewHolder holder = new RvViewHolder(context, itemView);
        return holder;
    }

    public static RvViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId){
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        return new RvViewHolder(context, itemView);
    }


    public static RvViewHolder createViewHolder(Context context,
                                                ViewGroup parent, int layoutId,boolean isbackground)
    {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        if (isbackground==true){
        final Drawable drawable=context.getResources().getDrawable(R.drawable.shui_bowen);
            setItemBg(drawable,itemView);
        }
        return new RvViewHolder(context, itemView);
    }
    private static void setItemBg(Drawable drawable,View view){
        view.setBackground(drawable);
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView()
    {
        return mConvertView;
    }




    /****以下为辅助方法*****/

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public RvViewHolder setText(@IdRes  int viewId, String text)
    {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public RvViewHolder setImageResource(@IdRes int viewId, int resId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

//    //需要修改
//    public ViewHolder setImageUrl(int viewId, String url)
//    {
//        ImageView view = getView(viewId);
//        UtilGlide.loadImg(mContext,url,view);
//        return this;
//    }



    public RvViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap)
    {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public RvViewHolder setImageDrawable(@IdRes int viewId, Drawable drawable)
    {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public RvViewHolder setBackgroundColor(@IdRes int viewId, int color)
    {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public RvViewHolder setBackgroundRes(@IdRes int viewId, int backgroundRes)
    {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public RvViewHolder setTextColor(@IdRes int viewId, int textColor)
    {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public RvViewHolder setTextColorRes(@IdRes int viewId, int textColorRes)
    {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    public RvViewHolder setVisible(@IdRes int viewId, int visible)
    {
        getView(viewId).setVisibility(visible);
        return this;
    }






    @SuppressLint("NewApi")
    public RvViewHolder setAlpha(@IdRes int viewId, float value)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            getView(viewId).setAlpha(value);
        } else
        {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public RvViewHolder setVisible(@IdRes int viewId, boolean visible)
    {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public RvViewHolder linkify(@IdRes int viewId)
    {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public RvViewHolder setTypeface(Typeface typeface, int... viewIds)
    {
        for (int viewId : viewIds)
        {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public RvViewHolder setProgress(@IdRes int viewId, int progress)
    {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public RvViewHolder setProgress(@IdRes int viewId, int progress, int max)
    {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public RvViewHolder setMax(@IdRes int viewId, int max)
    {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public RvViewHolder setRating(@IdRes int viewId, float rating)
    {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public RvViewHolder setRating(@IdRes int viewId, float rating, int max)
    {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public RvViewHolder setTag(@IdRes int viewId, Object tag)
    {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public RvViewHolder setTag(@IdRes int viewId, int key, Object tag)
    {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public RvViewHolder setChecked(@IdRes int viewId, boolean checked)
    {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * 关于事件的
     */
    public RvViewHolder setOnClickListener(@IdRes int viewId,
                                           View.OnClickListener listener)
    {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public RvViewHolder setOnTouchListener(@IdRes int viewId,
                                           View.OnTouchListener listener)
    {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public int getViewVisibility(@IdRes int viewId)
    {
        View view = getView(viewId);
        return view.getVisibility();
    }
}
