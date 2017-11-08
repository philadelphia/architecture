package com.example.app.di.component;

import android.app.Application;
import android.content.Context;

import com.example.commonlibs.di.module.AppModule;
import com.example.commonlibs.di.module.ClientModule;
import com.example.commonlibs.rxerrorhandler.RxErrorHandler;
import com.example.commonlibs.utils.DeviceUuidFactory;
import com.example.app.api.ApiService;
import com.example.app.app.App;
import com.example.app.di.module.MangerModule;
import com.example.app.di.module.ServiceModule;
import com.example.app.manager.WarningManger;
import com.example.ttsmanager.TextToSpeechManager;

import javax.inject.Singleton;

import dagger.Component;


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
