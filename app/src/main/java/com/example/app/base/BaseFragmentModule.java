package com.example.app.base;

import com.example.commonlibs.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;


@Module
public abstract class BaseFragmentModule<E> {

    private E e;

    public BaseFragmentModule(E e) {
        this.e = e;
    }

    @FragmentScope
    @Provides
    public E providerView(E e) {
        return e;
    }
}
