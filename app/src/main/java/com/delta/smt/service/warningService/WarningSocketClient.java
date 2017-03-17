package com.delta.smt.service.warningService;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.delta.commonlibs.utils.GsonTools;
import com.delta.smt.entity.SendMessage;
import com.delta.smt.entity.WarningContent;
import com.delta.smt.manager.ActivityMonitor;
import com.delta.smt.manager.WarningManger;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/15 10:48
 */


public class WarningSocketClient extends WebSocketClient implements ActivityMonitor.OnAppStateChangeListener, WarningManger.OnRegister {

    private boolean foreground = true;

    private boolean isConnect = false;
    private List<OnRecieveLisneter> onRecieveLisneters = new ArrayList<>();

    private List<WarningContent> contents = new ArrayList<>();
    private ActivityMonitor activityMonitor;
    private WarningManger warningManger;

    public WarningSocketClient(URI serverURI) {
        this(serverURI, new Draft_17(), ActivityMonitor.getInstance(), WarningManger.getInstance());
    }

    public ActivityMonitor getActivityMonitor() {
        return activityMonitor;
    }

    public void setActivityMonitor(ActivityMonitor activityMonitor) {
        this.activityMonitor = activityMonitor;
    }

    public WarningSocketClient(URI serverUri, Draft draft, ActivityMonitor activityMonitor, WarningManger warningManger) {
        super(serverUri, draft);
        this.activityMonitor = activityMonitor;
        this.warningManger = warningManger;
        warningManger.setOnRegister(this);
        activityMonitor.registerAppStateChangeListener(this);
        ActivityMonitor.setStrictForeground(true);
    }

    public void addOnRecieveLisneter(OnRecieveLisneter onRecieveLisneter) {
        onRecieveLisneters.add(onRecieveLisneter);
    }

    public void removeOnRecieveLisneter(OnRecieveLisneter onRecieveLisneter) {
        onRecieveLisneters.remove(onRecieveLisneter);
    }

    public WarningSocketClient(URI serverUri, Draft draft, Map<String, String> headers, int connecttimeout) {
        super(serverUri, draft, headers, connecttimeout);
    }


    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        Log.e(TAG, "onOpen() called with: serverHandshake = [" + serverHandshake + "]");
        isConnect = true;
    }

    @Override
    public void onMessage(String text) {
        isConnect = true;
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
                        if (topActivity.getClass().equals(warningManger.getWaringClass(String.valueOf(type)))) {
                            Gson gson = new Gson();
                            WarningContent sendMessage = gson.fromJson(text, WarningContent.class);
                            if (warningManger.isConsume()) {
                                contents.clear();
                                warningManger.setConsume(false);
                            }
                            contents.add(sendMessage);
                            text = gson.toJson(contents);
                            if (foreground) {
                                for (OnRecieveLisneter onRecieveLisneter : onRecieveLisneters) {
                                    onRecieveLisneter.OnForeground(text);
                                }
                            } else {
                                for (OnRecieveLisneter onRecieveLisneter : onRecieveLisneters) {
                                    onRecieveLisneter.OnBackground(text);
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

    @Override
    public void onClose(int i, String s, boolean b) {

        isConnect = false;
        Log.e(TAG, "onClose() called with: i = [" + i + "], s = [" + s + "], b = [" + b + "]");
    }

    @Override
    public void onError(Exception e) {

        isConnect = false;
        Log.e(TAG, "onError() called with: e = [" + e + "]");
    }

    @Override
    public void onAppStateChange(boolean foreground) {
        Activity topActivity = ActivityMonitor.getInstance().getTopActivity();
        Log.e("-----", "onAppStateChange: " + foreground + topActivity.getClass().getName());
        this.foreground = foreground;
    }

    @Override
    public void register(SendMessage type) {

        if (isConnect) {
            Map<String, String> maps = new HashMap<>();
            maps.put("type", String.valueOf(type));
            String s = GsonTools.createGsonString(maps);
            Log.e(TAG, "register: " + s);
            this.send(s);
        }
    }

    interface OnRecieveLisneter {

        void OnForeground(String text);

        void OnBackground(String text);

    }
}

