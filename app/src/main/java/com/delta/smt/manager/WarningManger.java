package com.delta.smt.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.delta.smt.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/27 15:00
 */


public class WarningManger {

    private static final String TAG = "WarningManger";
    private Map<Integer, Class> maps = new HashMap<>();
    private boolean isRecieve = true;
    private OnWarning onWarning;

    private WarningBroadcastReciever mBroadcastReceiver;

    public void setOnWarning(OnWarning onWarning) {
        this.onWarning = onWarning;
    }

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
            Log.i(TAG, "onReceive: ");
            //int intExtra = intent.getIntExtra(Constant.WARNINGTYPE, -1);
            String message = intent.getStringExtra(Constant.WARNINGMESSAGE);

                if (WarningManger.getInstance().isRecieve()) {
                    if (onWarning != null) {
                        onWarning.warningComing(message);
                    }
                }
        }
    }

    /**
     * 注册广播
     */
    public void registerWReceiver(Context context) {
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
    public void unregisterWReceriver(Context context) {
        if (mBroadcastReceiver == null) return;
        try {
            context.unregisterReceiver(mBroadcastReceiver);
            mBroadcastReceiver = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface OnWarning {
        void warningComing(String warningMessage);
    }
}
