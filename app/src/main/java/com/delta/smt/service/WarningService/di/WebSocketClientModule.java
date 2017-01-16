package com.delta.smt.service.warningService.di;


import com.delta.smt.manager.ActivityMonitor;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.service.warningService.WarningSocketClient;

import org.java_websocket.drafts.Draft;

import java.net.URI;
import java.net.URISyntaxException;

import dagger.Module;
import dagger.Provides;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/11 14:58
 */

@Module
public class WebSocketClientModule {

    private URI uri;
    private Draft draft;

    public WebSocketClientModule(Builder builder) {
        this.uri = builder.uri;
        this.draft = builder.draft;
    }


    @ServiceScope
    @Provides
    WarningSocketClient warningSocketClient(URI uri, Draft draft, ActivityMonitor activityMonitor, WarningManger warningManger) {
        return new WarningSocketClient(uri, draft, activityMonitor, warningManger);
    }

    @ServiceScope
    @Provides
    URI uri() {
        return uri;
    }

    @ServiceScope
    @Provides
    Draft draft() {
        return draft;
    }

    @ServiceScope
    @Provides
    ActivityMonitor activityMonitor() {
        return ActivityMonitor.getInstance();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private URI uri;
        private Draft draft;

        public Builder draft(Draft draft) {
            this.draft = draft;
            return this;
        }

        public Builder uri(String uri) {
            try {
                this.uri = new URI(uri);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return this;
        }

        public WebSocketClientModule build() {
            return new WebSocketClientModule(this);
        }
    }
}
