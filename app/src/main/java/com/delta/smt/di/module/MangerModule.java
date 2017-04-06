package com.delta.smt.di.module;

import android.content.Context;

import com.delta.smt.manager.WarningManger;
import com.delta.ttsmanager.RawTextToSpeech;
import com.delta.ttsmanager.TextToSpeechManager;

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
        TextToSpeechManager textToSpeechManager = new TextToSpeechManager();
        textToSpeechManager.setOnSpeakListener(new RawTextToSpeech(context));
        return textToSpeechManager;
    }
}
