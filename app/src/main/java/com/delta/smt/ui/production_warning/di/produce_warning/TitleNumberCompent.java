package com.delta.smt.ui.production_warning.di.produce_warning;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningActivity;

import dagger.Component;

/**
 * Created by Fuxiang.Zhang on 2016/12/26.
 */

@ActivityScope
@Component(modules=TitleNumberModule.class,dependencies = AppComponent.class)
public interface TitleNumberCompent {

    void inject(ProduceWarningActivity produceWarningActivity);

}