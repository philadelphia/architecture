package com.delta.smt.ui.store.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;

import com.delta.smt.ui.store.StoreIssueActivity;

import dagger.Component;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@ActivityScope
@Component(modules = StoreModule.class, dependencies = AppComponent.class)
public interface StoreComponent  {

    void inject(StoreIssueActivity activity);
}
