package com.delta.commonlibs.widget.autolayout;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;

import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Created by Lin.Hou on 2017-01-04.
 */

public class AutoLinearLayoutCompat extends LinearLayoutCompat{
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);
    public AutoLinearLayoutCompat(Context context) {
        super(context);
    }

    public AutoLinearLayoutCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoLinearLayoutCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public AutoLinearLayoutCompat.LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new AutoLinearLayoutCompat.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (!isInEditMode())
        {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
    }

}
