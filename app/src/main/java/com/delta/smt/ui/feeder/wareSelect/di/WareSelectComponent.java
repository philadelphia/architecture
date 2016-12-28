package com.delta.smt.ui.feeder.wareSelect.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.feeder.wareSelect.WareSelectActivity;

import dagger.Component;

/**
 * Created by Tao.ZT.Zhang on 2016/12/21.
 */

@ActivityScope
@Component(modules = WareSelectModule.class,  dependencies = AppComponent.class)
public interface WareSelectComponent {
    void inject(WareSelectActivity activity);
}
