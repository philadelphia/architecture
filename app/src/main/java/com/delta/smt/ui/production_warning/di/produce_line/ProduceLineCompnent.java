package com.delta.smt.ui.production_warning.di.produce_line;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.mvp.produce_line.ProduceLineActivity;

import dagger.Component;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */
@ActivityScope
@Component(modules = ProduceLineModule.class, dependencies = AppComponent.class)
public interface ProduceLineCompnent {
    void inject(ProduceLineActivity produceLineActivity);
}
