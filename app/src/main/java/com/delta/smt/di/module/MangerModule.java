package com.delta.smt.di.module;

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

        return  new WarningManger();
    }
}
