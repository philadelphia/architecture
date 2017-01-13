package com.delta.smt.service.warningService.di;



import com.delta.smt.service.warningService.WebSocketClientListener;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/11 14:58
 */

@Module
public class WebSocketClientModule {

    private HttpUrl httpUrl;
    private WebSocketClientListener webSocketClientListener;
    private Builder builder;

    public WebSocketClientModule(Builder builder) {
        this.builder = builder;
        this.httpUrl = builder.httpUrl;
        this.webSocketClientListener = builder.webSocketClientListener;
    }

    @Singleton
    @Provides
    @Named("webSocket")
    public HttpUrl httpUrl() {
        return httpUrl;
    }


    @Singleton
    @Provides
    public WebSocketListener webSocketListener() {


        return new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                webSocketClientListener.onOpen(webSocket, response);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {

                super.onMessage(webSocket, text);
                webSocketClientListener.onMessage(webSocket, text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);

            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                webSocketClientListener.onClosed(webSocket, reason);

            }
        };
    }

    @Singleton
    @Provides
    @Named("webSocket")
    public Request request(@Named("webSocket") HttpUrl httpUrl) {
        return new Request.Builder().url(httpUrl).build();
    }

    @Singleton
    @Provides
    @Named("webSocket")
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(3000, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3000, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3000, TimeUnit.SECONDS)//设置连接超时时间
                .build();
    }


    @Singleton
    @Provides
    public WebSocket providerWebSocket(@Named("webSocket") OkHttpClient okHttpClient, @Named("webSocket") Request request, WebSocketListener webSocketListener) {

        return okHttpClient.newWebSocket(request, webSocketListener);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {


        private HttpUrl httpUrl;

        private WebSocketClientListener webSocketClientListener;

        public Builder() {
        }

        public Builder httpurl(String url) {
            this.httpUrl = HttpUrl.parse(url);
            return this;
        }

        public Builder webSocketClientListener(WebSocketClientListener webSocketListener) {
            this.webSocketClientListener = webSocketListener;
            return this;
        }

        public WebSocketClientModule build() {

            return new WebSocketClientModule(this);
        }
    }
}
