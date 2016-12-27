package com.delta.smt.ui.storeroom.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.storeroom.StoreRoomActivity;

import dagger.Component;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@ActivityScope
@Component(modules = StoreRoomModule.class,dependencies = AppComponent.class)
public interface StoreRoomComponent {
    void inject(StoreRoomActivity activity);

}
