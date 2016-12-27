package com.delta.smt.ui.production_warning.di.produce_warning_fragment;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.mvp.produce_warning_fragment.ProduceWarningFragment;

import dagger.Component;

/**
 * Created by Fuxiang.Zhang on 2016/12/26.
 */
@FragmentScope
@Component(modules=ProduceWarningFragmentModule.class,dependencies = AppComponent.class)
public interface ProduceWarningFragmentCompnent {
    void inject(ProduceWarningFragment produceWarningFragment);
}
