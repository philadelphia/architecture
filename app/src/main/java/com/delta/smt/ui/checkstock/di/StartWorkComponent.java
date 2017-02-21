package com.delta.smt.ui.checkstock.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.checkstock.CheckStockActivity;
import com.delta.smt.ui.checkstock.StartWorkAndStopWorkActivity;
import com.delta.smt.ui.checkstock.mvp.StartWorkAndStopWorkContract;
import com.delta.smt.ui.checkstock.mvp.StartWorkAndStopWorkModel;

import dagger.Component;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@ActivityScope
@Component(modules= StartWorkModule.class,dependencies = AppComponent.class)
public interface StartWorkComponent {
    void inject(StartWorkAndStopWorkActivity activity);
}
