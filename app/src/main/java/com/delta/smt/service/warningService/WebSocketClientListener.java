package com.delta.smt.service.warningService;

import okhttp3.Response;
import okhttp3.WebSocket;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/12 9:35
 */


public interface WebSocketClientListener {


    public void onOpen(WebSocket webSocket, Response response);


    public void onMessage(WebSocket webSocket, String text);


    public void onClosed(WebSocket webSocket, String reason);
}
