package com.delta.app.di.component;

import android.app.Application;
import android.content.Context;

import com.delta.commonlibs.di.module.AppModule;
import com.delta.commonlibs.di.module.ClientModule;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandler;
import com.delta.commonlibs.utils.DeviceUuidFactory;
import com.delta.app.api.ApiService;
import com.delta.app.app.App;
import com.delta.app.di.module.MangerModule;
import com.delta.app.di.module.ServiceModule;
import com.delta.app.manager.WarningManger;
import com.delta.ttsmanager.TextToSpeechManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by V.Wenju.Tian on 2016/11/22.
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, ServiceModule.class, MangerModule.class})
public interface AppComponent {

    Application Application();

    Context context();

    ApiService apiService();

    WarningManger warningManger();

    DeviceUuidFactory deviceUuidFactory();

    void inject(App app);

    TextToSpeechManager TEXT_TO_SPEECH_MANAGER();

    //Rxjava错误处理管理类
    RxErrorHandler rxErrorHandler();

}
