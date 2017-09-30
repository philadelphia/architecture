package com.delta.smt.ui.smt_module.module_up.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.smt_module.module_up.ModuleUpActivity;

import dagger.Component;

/**
 * Author Shufeng.Wu
 * Date   2017/1/3
 */

@ActivityScope
@Component(modules = ModuleUpModule.class, dependencies = AppComponent.class)
public interface ModuleUpComponent {
    void inject(ModuleUpActivity moduleUpActivity);
}
