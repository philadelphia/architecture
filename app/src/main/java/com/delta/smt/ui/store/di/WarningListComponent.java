package com.delta.smt.ui.store.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.store.WarningListActivity;

import dagger.Component;

/**
 * Created by Lin.Hou on 2016-12-27.
 */
@ActivityScope
@Component(modules = WarningListModule.class,dependencies = AppComponent.class)
public interface WarningListComponent {
    void inject(WarningListActivity activity);
}
