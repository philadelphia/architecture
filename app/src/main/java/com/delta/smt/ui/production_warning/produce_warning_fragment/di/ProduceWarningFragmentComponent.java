package com.delta.smt.ui.production_warning.produce_warning_fragment.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.produce_warning_fragment.ProduceWarningFragment;

import dagger.Component;

/**
 * Author Fuxiang.Zhang
 * Date   2016/12/26
 */
@FragmentScope
@Component(modules=ProduceWarningFragmentModule.class,dependencies = AppComponent.class)
public interface ProduceWarningFragmentComponent {
    void inject(ProduceWarningFragment produceWarningFragment);
}