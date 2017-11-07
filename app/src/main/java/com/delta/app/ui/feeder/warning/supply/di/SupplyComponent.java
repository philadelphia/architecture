package com.delta.app.ui.feeder.warning.supply.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.app.di.component.AppComponent;
import com.delta.app.ui.feeder.warning.supply.FeederSupplyListActivity;

import dagger.Component;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */
@SuppressWarnings("all")
@ActivityScope
@Component(modules = SupplyModule.class, dependencies = AppComponent.class)
public interface SupplyComponent {
     void inject(FeederSupplyListActivity feederSupplyListActivity);
}