package com.delta.smt.service.warningService;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.delta.WebSocketLibs.BaseWebSocketStrategy;
import com.delta.WebSocketLibs.WsManager;
import com.delta.WebSocketLibs.WsStatusListener;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.smt.entity.SendMessage;
import com.delta.smt.manager.ActivityMonitor;
import com.delta.smt.manager.WarningManger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @description : 定制Presenter 负责 client 与 WarningManger之间的交互
 * @autHor :  V.Wenju.Tian
 * @date : 2017/3/6 15:16
 */


public class WarningSocketPresenter extends WsStatusListener implements ActivityMonitor.OnAppStateChangeListener, WarningManger.OnRegister {

    private static final String TAG = "WarningSocketPresenter";
    private WsManager wsManager;
    private WarningManger warningManger;
    private boolean foreground;
    private Context context;
    private BaseWebSocketStrategy baseWebSocketStrategy;
    private List<OnReceiveListener> onReceiveListeners = new ArrayList<>();
    JSONArray jsonArray = new JSONArray();
    private ActivityMonitor activityMonitor;

    public WarningSocketPresenter(WsManager wsManager, WarningManger warningManger, Context context, BaseWebSocketStrategy baseWebSocketStrategy, ActivityMonitor activityMonitor) {
        this.wsManager = wsManager;
        this.warningManger = warningManger;
        this.context = context;
        this.baseWebSocketStrategy = baseWebSocketStrategy;
        this.activityMonitor = activityMonitor;
        activityMonitor.registerAppStateChangeListener(this);
        ActivityMonitor.setStrictForeground(true);
        warningManger.setOnRegister(this);
        wsManager.setBaseWebSocketStrategy(baseWebSocketStrategy);
        baseWebSocketStrategy.setWsStatusListener(this);
    }

    public void setConsume(boolean isConsume) {
        warningManger.setConsume(isConsume);
    }

    public void startConnect() {
        wsManager.startConnect();
    }

    public WarningSocketPresenter(WarningManger warningManger, WsManager wsManager) {
        this.warningManger = warningManger;
        this.wsManager = wsManager;
        warningManger.setOnRegister(this);
        wsManager.setBaseWebSocketStrategy(baseWebSocketStrategy);
        activityMonitor.registerAppStateChangeListener(this);
        ActivityMonitor.setStrictForeground(true);
        baseWebSocketStrategy.setWsStatusListener(this);
    }

    public void addOnReceiveListener(OnReceiveListener onRecieveLisneter) {
        onReceiveListeners.add(onRecieveLisneter);
    }

    public void removeOnRecieveLisneter(OnReceiveListener onRecieveLisneter) {
        onReceiveListeners.remove(onRecieveLisneter);
    }

    @Override
    public void onAppStateChange(boolean foreground) {

        Activity topActivity = ActivityMonitor.getInstance().getTopActivity();
        Log.e("-----", "onAppStateChange: " + foreground + topActivity.getClass().getName());
        this.foreground = foreground;
    }

    @Override
    public void register(SendMessage sendMessage) {
        String s = GsonTools.createGsonString(sendMessage);
        Log.e(TAG, "register: " + s);
        wsManager.sendMessage(s);
    }

    public Activity getTopActivity() {
        return activityMonitor.getTopActivity();
    }


    @Override
    public void onMessage(String text) {
        super.onMessage(text);
        {
            Log.e(TAG, "onMessage() called with: text = [" + text + "]");
            if (!TextUtils.isEmpty(text)) {
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    String type = jsonObject.getString("type");
                    //1.首先判断栈顶是不是有我们的预警页面
                    //2.其次判断是否是在前台如果是前台就发送广播如果是后台就弹出dialog
                    Activity topActivity = activityMonitor.getTopActivity();
                    Log.e(TAG, "onMessage: " + topActivity.getClass().getName());

                    if (topActivity != null) {
                        Log.e(TAG, "onMessage: " + topActivity.getClass().equals(warningManger.getWaringClass(type)));
                        String[] split = type.split("_");
                        type = split[0];
                        if (topActivity.getClass().equals(warningManger.getWaringClass(type))||activityMonitor.getPenultActivity().equals(warningManger.getWaringClass(type))) {
                            //WarningMessage warningMessage = GsonTools.changeGsonToBean(text, WarningMessage.class);
                            if (warningManger.isConsume()) {
                                jsonArray = null;
                                jsonArray = new JSONArray();
                                warningManger.setConsume(false);
                            }
                            if (!GsonTools.containsJson(jsonArray, text)) {
                                jsonArray.put(jsonObject);
                            }
                            //contents.add(warningMessage);
                            String st_content = jsonArray.toString();
                            if (foreground) {
                                for (OnReceiveListener onReceiveListener : onReceiveListeners) {
                                    onReceiveListener.OnForeground(st_content);
                                }
                            } else {
                                for (OnReceiveListener onReceiveListener : onReceiveListeners) {
                                    onReceiveListener.OnBackground(st_content);
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    interface OnReceiveListener {

        void OnForeground(String text);

        void OnBackground(String text);

    }
}
