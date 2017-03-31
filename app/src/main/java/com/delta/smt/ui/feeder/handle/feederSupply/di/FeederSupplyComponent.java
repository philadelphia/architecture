package com.delta.smt.ui.feeder.handle.feederSupply.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.feeder.handle.feederSupply.FeederSupplyActivity;

import dagger.Component;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */
@SuppressWarnings("all")
@ActivityScope
@Component(modules =FeederSupplyModule.class, dependencies = AppComponent.class)
public interface FeederSupplyComponent {
     void inject(FeederSupplyActivity feederSupplyActivity);
}
