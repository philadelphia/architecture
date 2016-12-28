package com.delta.smt.ui.feeder.warning.supply.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.feeder.warning.SupplyFragment;

import dagger.Component;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */
@FragmentScope
@Component(modules = SupplyModule.class, dependencies = AppComponent.class)
public interface SupplyComponent {
    public void inject(SupplyFragment fragment);
}
