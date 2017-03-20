package com.delta.smt.ui.maintain.di;


import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.maintain.PCBMaintainActivity;

import dagger.Component;

/**
 * Created by Lin.Hou on 2017-03-13.
 */
@ActivityScope
@Component(modules= PCBMaintainModule.class,dependencies = AppComponent.class)
public interface PCBMaintainComponent {
    void inject(PCBMaintainActivity activity);
}
