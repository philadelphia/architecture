package com.delta.smt.common.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delta.smt.common.ItemOnclick;
import com.delta.smt.entity.CountDownEntity;
import com.delta.smt.ui.hand_add.mvp.HandAddActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/5 12:26
 */


public abstract class ItemCountdownViewAdapter<T extends CountDownEntity> extends RecyclerView.Adapter<ItemTimeViewHolder> {
    private LayoutInflater mLayoutinflater;
    private List<T> mList;
    private Context mContext;
    private SparseArray<ItemTimeViewHolder> mCountdownVHList;
    private Handler mHandler = new Handler();
    private Timer mTimer;
    private boolean isCancel = true;

    private ItemOnclick itemTimeOnclck;

    public void setOnItemTimeOnclck(ItemOnclick itemTimeOnclck) {
        this.itemTimeOnclck = itemTimeOnclck;

    }

    public ItemCountdownViewAdapter(Context context, List<T> list) {
        this.mList = list;
        mLayoutinflater = LayoutInflater.from(context);
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
        View view = mLayoutinflater.inflate(getLayoutId(), parent, false);
        ItemTimeViewHolder itemRimeViewHolder = new ItemTimeViewHolder(view);
        return itemRimeViewHolder;
    }

    protected abstract int getLayoutId();

    @Override
    public void onBindViewHolder(final ItemTimeViewHolder holder, final int position) {
        CountDownEntity curItemInfo = mList.get(position);
        holder.bindData(curItemInfo);

        // 处理倒计时
        if (curItemInfo.getCountdown() > 0) {
            synchronized (mCountdownVHList) {
                mCountdownVHList.put(curItemInfo.getId(), holder);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemTimeOnclck != null) {
                    itemTimeOnclck.onItemClick(holder.itemView, position);
                }
            }
        });
        convert(holder, mList.get(position), position);


    }

    protected abstract void convert(ItemTimeViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onViewRecycled(ItemTimeViewHolder holder) {
        super.onViewRecycled(holder);

        CountDownEntity curAnnounceGoodsInfo = holder.getBean();
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

