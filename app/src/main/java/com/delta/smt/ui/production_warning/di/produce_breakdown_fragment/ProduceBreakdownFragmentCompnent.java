package com.delta.smt.ui.production_warning.di.produce_breakdown_fragment;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment.ProduceBreakdownFragment;

import dagger.Component;
import dagger.Module;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */
@FragmentScope
@Component(modules=ProduceBreakdownFragmentModule.class,dependencies = AppComponent.class)
public interface ProduceBreakdownFragmentCompnent {
    void inject(ProduceBreakdownFragment produceBreakdownFragment);

}
