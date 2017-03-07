package com.delta.smt.service.warningService.di;

import android.content.Context;
import android.text.TextUtils;

import com.delta.WebSocketLibs.BaseWebSocketStrategy;
import com.delta.WebSocketLibs.OkHttpWebSocketStrategy;
import com.delta.WebSocketLibs.WsManager;
import com.delta.smt.manager.ActivityMonitor;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.service.warningService.WarningSocketPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/3/6 16:29
 */

@Module
public class WarningSocketPresenterModule {

    private String url;
    private Context context;

    public WarningSocketPresenterModule(Builder builder) {

        this.url = builder.url;
        this.context = builder.context;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Named("url")
    @ServiceScope
    @Provides
    String providerUrl() {
        return url;
    }

    @Named("context")
    @ServiceScope
    @Provides
    Context providerContext() {
        return context;
    }

    @ServiceScope
    @Provides
    WarningSocketPresenter providerWarningPresenter(WsManager wsManager, WarningManger warningManger, BaseWebSocketStrategy baseWebSocketStrategy, @Named("context") Context context, ActivityMonitor activityMonitor) {
        return new WarningSocketPresenter(wsManager, warningManger, context, baseWebSocketStrategy, activityMonitor);
    }

    @ServiceScope
    @Provides
    WsManager providerWsManager() {
        return WsManager.getInstance();
    }

    @ServiceScope
    @Provides
    ActivityMonitor providerActivityMonitor() {
        return ActivityMonitor.getInstance();
    }

    @ServiceScope
    @Provides
    BaseWebSocketStrategy providerBaseWebSocketStrategy(@Named("context") Context context, @Named("url") String url) {
        return new OkHttpWebSocketStrategy.Builder(context).wsUrl(url).build();
    }

    public static final class Builder {
        private String url;
        private Context context;


        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public WarningSocketPresenterModule build() {
            if (TextUtils.isEmpty(url)) {
                throw new IllegalStateException("url is required!");
            }
            return new WarningSocketPresenterModule(this);
        }


    }


}
