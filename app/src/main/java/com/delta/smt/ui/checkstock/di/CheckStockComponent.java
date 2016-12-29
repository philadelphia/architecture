package com.delta.smt.ui.checkstock.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.checkstock.CheckStockActivity;

import dagger.Component;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@ActivityScope
@Component(modules= CheckStockModule.class,dependencies = AppComponent.class)
public interface CheckStockComponent  {
    void inject(CheckStockActivity activity);
}
