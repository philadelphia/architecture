package com.delta.smt.ui.storage_manger.storage_select.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.storage_manger.storage_select.StorageSelectActivity;

import dagger.Component;



@ActivityScope
@Component(modules = StorageSelectModule.class,  dependencies = AppComponent.class)
public interface StorageSelectComponent {
    void inject(StorageSelectActivity activity);
}
