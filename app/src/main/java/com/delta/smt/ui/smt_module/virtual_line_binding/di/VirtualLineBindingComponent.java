package com.delta.smt.ui.smt_module.virtual_line_binding.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.smt_module.virtual_line_binding.VirtualLineBindingActivity;

import dagger.Component;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

@ActivityScope
@Component(modules = VirtualLineBindingModule.class, dependencies = AppComponent.class)
public interface VirtualLineBindingComponent {
    public void inject(VirtualLineBindingActivity virtualLineBindingActivity);
}
