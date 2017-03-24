package com.delta.smt.di.module;

import android.content.Context;

import com.delta.smt.manager.TextToSpeechManager;
import com.delta.smt.manager.WarningManger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 9:28
 */

@Module
public class MangerModule {


    @Singleton
    @Provides
    WarningManger providerWarningManger() {
        return WarningManger.getInstance();
    }

    @Singleton
    @Provides
    TextToSpeechManager provderTextSpeechManager(Context context) {
        return new TextToSpeechManager(context);
    }
}
