package com.delta.smt.ui.feeder.feederWarning.FeederSupply.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.feeder.feederWarning.SupplyFragment;

import dagger.Component;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */
@FragmentScope
@Component(modules = FeederSupplyModule.class, dependencies = AppComponent.class)
public interface FeederSupplyComponent {
    public void inject(SupplyFragment fragment);
}
