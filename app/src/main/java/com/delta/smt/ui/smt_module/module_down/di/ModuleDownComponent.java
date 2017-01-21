package com.delta.smt.ui.smt_module.module_down.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.smt_module.module_down.ModuleDownActivity;

import dagger.Component;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

@ActivityScope
@Component(modules =ModuleDownModule.class, dependencies = AppComponent.class)
public interface ModuleDownComponent {
    public void inject(ModuleDownActivity moduleDownActivity);
}
