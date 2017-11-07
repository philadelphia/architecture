package com.example.commonlibs.di.module;

import com.example.commonlibs.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/13 14:24
 */

@Module
public abstract class BaseActivityModule<E> {

    private E view;

    public BaseActivityModule(E view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    E providerModel() {
        return view;
    }

}
