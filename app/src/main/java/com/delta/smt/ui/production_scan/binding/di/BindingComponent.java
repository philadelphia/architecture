package com.delta.smt.ui.production_scan.binding.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_scan.binding.BindingActivity;

import dagger.Component;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

@ActivityScope
@Component(modules = BindingModule.class, dependencies = AppComponent.class)
public interface BindingComponent {

    void inject(BindingActivity activity);


}
