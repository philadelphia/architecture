package com.delta.smt.ui.over_receive.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.over_receive.OverReceiveActivity;
import com.delta.smt.ui.smt_module.module_up.ModuleUpActivity;
import com.delta.smt.ui.smt_module.module_up.di.ModuleUpModule;

import dagger.Component;

/**
 * Created by Shufeng.Wu on 2017/1/15.
 */

@ActivityScope
@Component(modules =OverReceiveModule.class, dependencies = AppComponent.class)
public interface OverReceiveComponent {
    public void inject(OverReceiveActivity overReceiveActivity);
}
