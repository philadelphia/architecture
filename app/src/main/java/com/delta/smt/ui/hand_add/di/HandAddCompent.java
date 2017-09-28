package com.delta.smt.ui.hand_add.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.hand_add.HandAddActivity;

import dagger.Component;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */
@ActivityScope
@Component(modules = HandAddModule.class,dependencies = AppComponent.class)
public interface HandAddCompent {

    void inject(HandAddActivity handAddActivity);
}
