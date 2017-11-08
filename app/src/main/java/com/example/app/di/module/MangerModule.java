package com.example.app.di.module;

import android.content.Context;

import com.example.app.manager.WarningManger;
import com.example.ttsmanager.RawTextToSpeech;
import com.example.ttsmanager.TextToSpeechManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;



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
