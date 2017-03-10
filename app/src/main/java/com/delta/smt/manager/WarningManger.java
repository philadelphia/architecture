package com.delta.smt.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.delta.smt.Constant;
import com.delta.smt.entity.SendMessage;

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
    private boolean isReceive = true;
    private OnWarning onWarning;
    private boolean isConsume;
    private OnRegister onRegister;

    public void setOnRegister(OnRegister onRegister) {
        this.onRegister = onRegister;
    }

    public OnRegister getOnRegister() {
        return onRegister;
    }

    public void setConsume(boolean consume) {
        isConsume = consume;
    }

    public boolean isConsume() {
        return isConsume;
    }

    private WarningBroadcastReceiver mBroadcastReceiver;

    public void setOnWarning(OnWarning onWarning) {
        this.onWarning = onWarning;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
    }

    public boolean isReceived() {
        return isReceive;
    }

    /**
     * 添加预警管理
     *
     * @param type   预警类型
     * @param mClass 预警类
     */
    public void addWarning(int type, Class mClass) {
        maps.put(type, mClass);
    }

    /**
     * 服务器订阅相关信息
     *
     * @param sendMessage 订阅的信息
     */
    public void sendMessage(SendMessage sendMessage) {

        if (onRegister != null) {
            onRegister.register(sendMessage);
        }
    }


    public void removeWarning(int type) {
        maps.remove(type);
    }

    public Class getWaringClass(int Type) {
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


    class WarningBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(Constant.WARNINGMESSAGE);
            Log.e(TAG, "onReceive: " + message);

            if (WarningManger.getInstance().isReceived()) {
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
                mBroadcastReceiver = new WarningBroadcastReceiver();
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
    public void unregisterWReceiver(Context context) {
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

    public interface OnRegister {
        void register(SendMessage type);
    }
}
