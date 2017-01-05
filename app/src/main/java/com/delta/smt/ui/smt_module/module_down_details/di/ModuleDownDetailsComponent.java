package com.delta.smt.ui.smt_module.module_down_details.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.smt_module.module_down.ModuleDownActivity;
import com.delta.smt.ui.smt_module.module_down_details.ModuleDownDetailsActivity;

import dagger.Component;

/**
 * Created by Shufeng.Wu on 2017/1/5.
 */

@ActivityScope
@Component(modules =ModuleDownDetailsModule.class, dependencies = AppComponent.class)
public interface ModuleDownDetailsComponent {
    public void inject(ModuleDownDetailsActivity moduleDownDetailsActivity);
}
