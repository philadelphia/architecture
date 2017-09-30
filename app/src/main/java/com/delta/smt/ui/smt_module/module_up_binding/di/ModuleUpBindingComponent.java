package com.delta.smt.ui.smt_module.module_up_binding.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.smt_module.module_up_binding.ModuleUpBindingActivity;

import dagger.Component;

/**
 * Author Shufeng.Wu
 * Date   2017/1/4
 */

@ActivityScope
@Component(modules =ModuleUpBindingModule.class, dependencies = AppComponent.class)
public interface ModuleUpBindingComponent {
     void inject(ModuleUpBindingActivity moduleUpBindingActivity);
}
