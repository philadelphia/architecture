package com.delta.smt.ui.feederCacheRegion.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;

import dagger.Component;

/**
 * Created by Tao.ZT.Zhang on 2016/12/21.
 */

@ActivityScope
@Component(modules = FeederCacheRegionModule.class,  dependencies = AppComponent.class)
public interface FeederCacheRegionComponent {
}
