package com.delta.smt.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.delta.smt.R;
import com.delta.smt.entity.CountDownEntity;
import com.zhy.autolayout.utils.AutoUtils;

import cn.iwgang.countdownview.CountdownView;

public class ItemTimeViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    public CountdownView mCountdownViewTest;

    private CountDownEntity mItemInfo;

    public ItemTimeViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        AutoUtils.autoSize(itemView);
        mCountdownViewTest = (CountdownView) itemView.findViewById(R.id.cv_countdownViewTest);
    }

    public void bindData(CountDownEntity itemInfo) {
        mItemInfo = itemInfo;

        if (itemInfo.getCountdown() > 0) {
            refreshTime(System.currentTimeMillis());
        } else {
            mCountdownViewTest.allShowZero();
        }
        
    }

    public void refreshTime(long curTimeMillis) {
        if (null == mItemInfo || mItemInfo.getCountdown() <= 0) return;

        mCountdownViewTest.updateShow(mItemInfo.getEndTime() - curTimeMillis);
    }

    public <TView extends View> TView getView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }
        return (TView) view;
    }

    public ItemTimeViewHolder setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);

        return this;
    }

    public CountDownEntity getBean() {
        return mItemInfo;
    }
}
