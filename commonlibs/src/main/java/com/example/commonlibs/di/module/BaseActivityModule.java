package com.example.commonlibs.di.module;

import com.example.commonlibs.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @description :
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
