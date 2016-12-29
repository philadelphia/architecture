package com.delta.smt.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.delta.smt.Constant;

import java.util.HashMap;
import java.util.Map;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/27 15:00
 */


public class WarningManger {


    private Map<Integer, Class> maps = new HashMap<>();
    private boolean isRecieve = true;
    private OnWarning onWarning;

    public void setOnWarning(OnWarning onWarning) {
        this.onWarning = onWarning;
    }

    private WarningBroadcastReciever mBroadcastReceiver;

    public void setRecieve(boolean recieve) {
        isRecieve = recieve;
    }

    public boolean isRecieve() {
        return isRecieve;
    }

    public void addWarning(int type, Class mclass) {
        maps.put(type, mclass);
    }

    public void removeWarning(int type) {
        maps.remove(type);
    }

    public Class getWaringCalss(int Type) {
        if (!maps.keySet().contains(Type)) {
            return null;
        }
        return maps.get(Type);
    }

    public Map<Integer, Class> getMaps() {
        return maps;
    }

    public static WarningManger getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        static WarningManger sInstance = new WarningManger();
    }


    class WarningBroadcastReciever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


            int intExtra = intent.getIntExtra(Constant.WARNINGTYPE, -1);
            Class waringCalss = WarningManger.getInstance().getWaringCalss(intExtra);
            if (waringCalss != null) {

                Log.e(TAG, "onReceive: " + "-----------------------------" + waringCalss);
                if (WarningManger.getInstance().isRecieve()) {
                    if (onWarning != null) {
                        onWarning.warningComming();
                    }

                }
            }

            Log.e(TAG, "onReceive: " + intExtra);

        }
    }

    /**
     * 注册广播
     */
    public void registWReceiver(Context context) {
        try {
            if (mBroadcastReceiver == null) {
                mBroadcastReceiver = new WarningBroadcastReciever();
            }
            IntentFilter filter = new IntentFilter(Constant.WARNINGRECIEVE);
            context.registerReceiver(mBroadcastReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解除注册广播
     */
    public void unregistWReceriver(Context context) {
        if (mBroadcastReceiver == null) return;
        try {
            context.unregisterReceiver(mBroadcastReceiver);
            mBroadcastReceiver = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface OnWarning {
        void warningComming();
    }
}
