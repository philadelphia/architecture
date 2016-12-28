package com.delta.smt.ui.storage_manger.details.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.storage_manger.details.StorageDetailsActivity;

import dagger.Component;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

@ActivityScope
@Component(modules = StorageDetailsModule.class, dependencies = AppComponent.class)
public interface StorageDetailsComponent {

    void inject(StorageDetailsActivity activity);


}
