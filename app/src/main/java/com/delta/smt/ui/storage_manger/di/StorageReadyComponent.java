package com.delta.smt.ui.storage_manger.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.storage_manger.StorageReadyFragment;

import dagger.Component;

/**
 * Created by Zhenyu.Liu on 2016/12/22.
 */


@ActivityScope
@Component(modules = StorageReadyModule.class, dependencies = AppComponent.class)
public interface StorageReadyComponent {

    void inject(StorageReadyFragment fragment);

}
