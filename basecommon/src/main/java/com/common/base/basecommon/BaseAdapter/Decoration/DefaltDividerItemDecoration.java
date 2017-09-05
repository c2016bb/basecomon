package com.common.base.basecommon.BaseAdapter.Decoration;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.common.base.basecommon.R;

/**
 * Created by User on 2017/9/4.
 */

public class DefaltDividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[] {android.R.attr.listDivider };
    public static final int HORIZONTAL = OrientationHelper.HORIZONTAL;
    public static final int VERTICAL = OrientationHelper.VERTICAL;

    private Drawable mDivider;
    SparseArrayCompat<Drawable> drawables = new SparseArrayCompat();


    public  static class DividerType{
        public static  final int LLBOTTOM=1;
        public static  final int LLBOTTOM_NOLAST=2;
        public static  final int GRIDCOMOM=3;
    }
    private  int  itemType;
    public void setItemType(int  itemType){
        Log.i(TAG, "setItemType: itemType---->"+itemType);
        if (itemType>0) {this.itemType=itemType;}
    }

    public DefaltDividerItemDecoration(Context context)
    {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        drawables.put(0,mDivider);
        decoratioDelegeteManager=new DecoratioDelegeteManager();
        setOrientation(VERTICAL);
        a.recycle();
    }

    public void addDividerType(DecoratioDelegete delegete){
         decoratioDelegeteManager.addDelegate(delegete);
    }
    private final Rect mBounds = new Rect();
    @SuppressLint("NewApi")
    private void drawLLVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

         int childCount = parent.getChildCount();
        if (itemType==ItemDecorationType.NOLASTITEM){
            childCount=childCount-1;
        }
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
            final int top = bottom - mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    @SuppressLint("NewApi")
    private void drawLLHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top;
        final int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

         int childCount = parent.getChildCount();
        if (itemType==ItemDecorationType.NOLASTITEM){
            childCount=childCount-1;
        }
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(ViewCompat.getTranslationX(child));
            final int left = right - mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }
    
    
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        RecyclerView.LayoutManager layoutManager=parent.getLayoutManager();
      if (layoutManager instanceof LinearLayoutManager){
          if (mOrientation == VERTICAL) {
              drawLLVertical(c, parent);
          } else {
              drawLLHorizontal(c, parent);
          }
      }else {
          drawHorizontal(c, parent);
          drawVertical(c, parent);
      }
    }

    private int getSpanCount(RecyclerView parent)
    {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    /**
     * Sets the {@link Drawable} for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
        drawables.put(drawables.size(),drawable);
    }

    public void drawHorizontal(Canvas c, RecyclerView parent)
    {

        Log.i(TAG, "drawHorizontal: 画横轴");
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + mDivider.getIntrinsicWidth();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent)
    {
        final int childCount = parent.getChildCount();
        Log.i(TAG, "drawVertical: 画纵轴");
        for (int i = 0; i < childCount; i++)
        {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount)
    {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {
            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL)
            {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else
            {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }
DecoratioDelegeteManager decoratioDelegeteManager;

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                                                                           int childCount)
    {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL)
            {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0)
                {
                    return true;
                }
            }
        }
        return false;
    }
    private int mOrientation;

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    private static final String TAG = "cc";
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int childCount = parent.getAdapter().getItemCount();
        int itemPosition = parent.getChildLayoutPosition(view);
        if (layoutManager instanceof LinearLayoutManager) {
            if (mOrientation == VERTICAL) {
                if (itemType==ItemDecorationType.NOLASTITEM&&itemPosition+1==childCount){
                }else {
                    outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
                }
            } else {
                if (itemType==ItemDecorationType.NOLASTITEM&&itemPosition+1==childCount){
                }else {
                    outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
                }
            }
        } else {
            int spanCount = getSpanCount(parent);
//    DecoratioDelegete delegete=decoratioDelegeteManager.getItemType(itemPosition);
//              outRect.set(delegete.getItemLeftWidth(),delegete.getItemTopWidth(),delegete.getItemRightWidth(),delegete.getItemBottomWidth());

            Log.i(TAG, "getItemOffsets: itemPosition---->" + itemPosition);

            if (isLastRaw(parent, itemPosition, spanCount, childCount))// 如果是最后一行，则不需要绘制底部
            {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            } else if (isLastColum(parent, itemPosition, spanCount, childCount))// 如果是最后一列，则不需要绘制右边
            {
                Log.i(TAG, "isLastColum--->true");
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                Log.i(TAG, "isLastColum--->fasle");
                outRect.set(0, 0, mDivider.getIntrinsicWidth(),
                        mDivider.getIntrinsicHeight());
            }
        }
    }
}
