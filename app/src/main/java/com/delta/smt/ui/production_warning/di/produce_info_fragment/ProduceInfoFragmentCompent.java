package com.delta.smt.ui.production_warning.di.produce_info_fragment;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.mvp.produce_info_fragment.ProduceInfoFragment;

import dagger.Component;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */
@FragmentScope
@Component(modules = ProduceInfoFragmentModule.class,dependencies = AppComponent.class)
public interface ProduceInfoFragmentCompent {
    void inject(ProduceInfoFragment produceInfoFragment);
}
