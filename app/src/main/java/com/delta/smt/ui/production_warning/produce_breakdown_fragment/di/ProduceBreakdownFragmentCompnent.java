package com.delta.smt.ui.production_warning.produce_breakdown_fragment.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.produce_breakdown_fragment.ProduceBreakdownFragment;

import dagger.Component;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */
@FragmentScope
@Component(modules=ProduceBreakdownFragmentModule.class,dependencies = AppComponent.class)
public interface ProduceBreakdownFragmentCompnent {
    void inject(ProduceBreakdownFragment produceBreakdownFragment);

}
