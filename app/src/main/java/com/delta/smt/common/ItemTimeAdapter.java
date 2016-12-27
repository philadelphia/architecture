package com.delta.smt.common;


import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delta.smt.R;
import com.delta.smt.entity.ItemInfo;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.iwgang.countdownview.CountdownView;

/**
 * Created by Lin.Hou on 2016-12-27.
 */

public class ItemTimeAdapter extends RecyclerView.Adapter<ItemTimeViewHolder> {
    private  LayoutInflater mLayoutinflater;
    private  List<ItemInfo> mList;
    private Context mContext;
    private  SparseArray<ItemTimeViewHolder> mCountdownVHList;
    private Handler mHandler = new Handler();
    private Timer mTimer;
    private boolean isCancel = true;


    public ItemTimeAdapter(Context context, List<ItemInfo> list){
        this.mList=list;
        mLayoutinflater=LayoutInflater.from(context);
        mCountdownVHList = new SparseArray<>();
        startRefreshTime();
    }

    public void startRefreshTime() {
        if (!isCancel) return;

        if (null != mTimer) {
            mTimer.cancel();
        }

        isCancel = false;
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(mRefreshTimeRunnable);
            }
        }, 0, 10);


    }
    public void cancelRefreshTime() {
        isCancel = true;
        if (null != mTimer) {
            mTimer.cancel();
        }
        mHandler.removeCallbacks(mRefreshTimeRunnable);
    }

    @Override
    public ItemTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mLayoutinflater.inflate(R.layout.item_base,parent,false);
        ItemTimeViewHolder itemRimeViewHolder=new ItemTimeViewHolder(view);
        return itemRimeViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemTimeViewHolder holder, int position) {
        ItemInfo curItemInfo = mList.get(position);
        holder.bindData(curItemInfo);

        // 处理倒计时
        if (curItemInfo.getCountdown() > 0) {
            synchronized (mCountdownVHList) {
                mCountdownVHList.put(curItemInfo.getId(), holder);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    @Override
    public void onViewRecycled(ItemTimeViewHolder holder) {
        super.onViewRecycled(holder);

        ItemInfo curAnnounceGoodsInfo = holder.getBean();
        if (null != curAnnounceGoodsInfo && curAnnounceGoodsInfo.getCountdown() > 0) {
            mCountdownVHList.remove(curAnnounceGoodsInfo.getId());
        }
    }

    private Runnable mRefreshTimeRunnable = new Runnable() {
        @Override
        public void run() {
            if (mCountdownVHList.size() == 0) return;

            synchronized (mCountdownVHList) {
                long currentTime = System.currentTimeMillis();
                int key;
                for (int i = 0; i < mCountdownVHList.size(); i++) {
                    key = mCountdownVHList.keyAt(i);
                    ItemTimeViewHolder curMyViewHolder = mCountdownVHList.get(key);
                    if (currentTime >= curMyViewHolder.getBean().getEndTime()) {
                        // 倒计时结束
                        curMyViewHolder.getBean().setCountdown(0);
                        mCountdownVHList.remove(key);
                        notifyDataSetChanged();
                    } else {
                        curMyViewHolder.refreshTime(currentTime);
                    }

                }
            }
        }
    };


}
class ItemTimeViewHolder extends RecyclerView.ViewHolder{

    public  CountdownView mCountdownViewTest;
    public  TextView mTextContent;
    private ItemInfo mItemInfo;
    public ItemTimeViewHolder(View itemView) {
        super(itemView);
        mTextContent= (TextView) itemView.findViewById(R.id.content_text);
        mCountdownViewTest= (CountdownView) itemView.findViewById(R.id.countdownViewTest);
    }
    public void bindData(ItemInfo itemInfo) {
        mItemInfo = itemInfo;

        if (itemInfo.getCountdown() > 0) {
            refreshTime(System.currentTimeMillis());
        } else {
            mCountdownViewTest.allShowZero();
        }

        mTextContent.setText(itemInfo.getText());
    }

    public void refreshTime(long curTimeMillis) {
        if (null == mItemInfo || mItemInfo.getCountdown() <= 0) return;

        mCountdownViewTest.updateShow(mItemInfo.getEndTime() - curTimeMillis);
    }

    public ItemInfo getBean() {
        return mItemInfo;
    }
}
