package com.delta.commonlibs.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/3/31 15:11
 */


public class RecycleViewUtils {

    public static void scrollToMiddle(LinearLayoutManager mLinearLayoutManager, int n, RecyclerView mRecyclerView) {
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        mLinearLayoutManager.scrollToPositionWithOffset(n, mRecyclerView.getChildAt((lastItem - firstItem) / 2).getTop());
    }
}
