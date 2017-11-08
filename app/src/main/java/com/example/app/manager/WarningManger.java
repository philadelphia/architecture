package com.example.app.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.app.entity.SendMessage;

import java.util.HashMap;
import java.util.Map;



public class WarningManger {

    private static final String TAG = "WarningManger";
    private Map<String, Class> maps = new HashMap<>();
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
    public void addWarning(String type, Class mClass) {
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


    public void removeWarning(String type) {
        maps.remove(type);
    }

    public Class getWaringClass(String Type) {
        if (!maps.keySet().contains(Type)) {
            return null;
        }
        return maps.get(Type);
    }

    public Map<String, Class> getMaps() {
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
            String message = intent.getStringExtra(Constant.WARNING_MESSAGE);
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
            IntentFilter filter = new IntentFilter(Constant.WARNING_RECEIVE);
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
