package com.delta.smt.ui.production_warning.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.mvp.contract.ProduceLineContract;
import com.delta.smt.ui.production_warning.mvp.view.ProduceLineActivity;

import dagger.Component;
import dagger.Module;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */
@ActivityScope
@Component(modules=ProduceLineModule.class,dependencies = AppComponent.class)
public interface ProduceLineCompnent {
    void inject(ProduceLineActivity produceLineActivity);
}
