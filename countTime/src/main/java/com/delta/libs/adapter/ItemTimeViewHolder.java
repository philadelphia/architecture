package com.delta.libs.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.delta.libs.CountTimeView;
import com.zhy.autolayout.utils.AutoUtils;


public class ItemTimeViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews = new SparseArray<>();
    ;
    public CountTimeView mCountdownViewTest;

    private TimeEntity mItemInfo;
    private boolean isCountUp;
    public View itemView ;

    public boolean isCountUp() {
        return isCountUp;
    }

    public void setCountUp(boolean countUp) {
        isCountUp = countUp;
    }

    public ItemTimeViewHolder(View itemView, int countViewId) {
        super(itemView);
        AutoUtils.auto(itemView);
        this.itemView =itemView;
        //AutoUtils.autoSize(itemView);
        mCountdownViewTest = getView(countViewId);
        isCountUp = mCountdownViewTest.isCountUp();
    }

    public void bindData(TimeEntity itemInfo) {
        mItemInfo = itemInfo;
        refreshTime(System.currentTimeMillis());
    }

    public void refreshTime(long curTimeMillis) {
        if (null == mItemInfo) {
            return;
        }
        if (isCountUp) {
            if (curTimeMillis - mItemInfo.getCreat_time() < 0) {
                resetZero();
                return;
            }
            mCountdownViewTest.updateShow(curTimeMillis - mItemInfo.getCreat_time());
        } else {
            if (mItemInfo.getEnd_time() - curTimeMillis <= 0) {
                resetZero();
                return;
            }
            mCountdownViewTest.updateShow(mItemInfo.getEnd_time() - curTimeMillis);
        }
    }

    public void resetZero() {
        mCountdownViewTest.allShowZero();
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
        view.setText(value+"");

        return this;
    }

    public TimeEntity getBean() {
        return mItemInfo;
    }
}
