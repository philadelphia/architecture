package com.delta.smt.service.warningService;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.delta.commonlibs.utils.GsonTools;
import com.delta.WebSocketLibs.BaseWebSocketStrategy;
import com.delta.WebSocketLibs.WsManager;
import com.delta.WebSocketLibs.WsStatusListener;
import com.delta.smt.entity.WarningContent;
import com.delta.smt.manager.ActivityMonitor;
import com.delta.smt.manager.WarningManger;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * @description : 定制Presenter 负责 client 与 WarningManger之间的交互
 * @autHor :  V.Wenju.Tian
 * @date : 2017/3/6 15:16
 */


public class WarningSocketPresenter extends WsStatusListener implements ActivityMonitor.OnAppStateChangeListener, WarningManger.OnRegister {


    private WsManager wsManager;
    private WarningManger warningManger;
    private boolean foreground;
    private Context context;
    private BaseWebSocketStrategy baseWebSocketStrategy;
    private List<OnReceiveListener> onReceiveListeners = new ArrayList<>();

    private List<WarningContent> contents = new ArrayList<>();
    private ActivityMonitor activityMonitor;

    public WarningSocketPresenter(WsManager wsManager, WarningManger warningManger, Context context, BaseWebSocketStrategy baseWebSocketStrategy, ActivityMonitor activityMonitor) {
        this.wsManager = wsManager;
        this.warningManger = warningManger;
        this.context = context;
        this.baseWebSocketStrategy = baseWebSocketStrategy;
        this.activityMonitor = activityMonitor;
        warningManger.setOnRegister(this);
        wsManager.setBaseWebSocketStrategy(baseWebSocketStrategy);
        baseWebSocketStrategy.setWsStatusListener(this);
        wsManager.startConnect();
    }

    public WarningSocketPresenter(WarningManger warningManger, WsManager wsManager) {
        this.warningManger = warningManger;
        this.wsManager = wsManager;
        warningManger.setOnRegister(this);
        wsManager.setBaseWebSocketStrategy(baseWebSocketStrategy);
        baseWebSocketStrategy.setWsStatusListener(this);
    }

    public void addOnRecieveLisneter(OnReceiveListener onRecieveLisneter) {
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
    public void register(int type) {
        if (wsManager.isWsConnected()) {
            Map<String, String> maps = new HashMap<>();
            maps.put("type", String.valueOf(type));
            String s = GsonTools.createGsonString(maps);
            Log.e(TAG, "register: " + s);
            wsManager.sendMessage(s);
        }

    }

    @Override
    public void onMessage(String text) {
        super.onMessage(text);
        {
            Log.e(TAG, "onMessage() called with: text = [" + text + "]");
            if (!TextUtils.isEmpty(text)) {
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    int type = jsonObject.getInt("type");

                    //1.首先判断栈顶是不是有我们的预警页面
                    //2.其次判断是否是在前台如果是前台就发送广播如果是后台就弹出dialog
                    Activity topActivity = activityMonitor.getTopActivity();
                    if (topActivity != null) {
                        if (topActivity.getClass().equals(warningManger.getWaringCalss(type))) {
                            Gson gson = new Gson();
                            WarningContent sendMessage = gson.fromJson(text, WarningContent.class);
                            if (warningManger.isConsume()) {
                                contents.clear();
                                warningManger.setConsume(false);
                            }
                            contents.add(sendMessage);
                            text = gson.toJson(contents);
                            if (foreground) {
                                for (OnReceiveListener onReceiveListener : onReceiveListeners) {
                                    onReceiveListener.OnForeground(text);
                                }
                            } else {
                                for (OnReceiveListener onReceiveListener : onReceiveListeners) {
                                    onReceiveListener.OnBackground(text);
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
