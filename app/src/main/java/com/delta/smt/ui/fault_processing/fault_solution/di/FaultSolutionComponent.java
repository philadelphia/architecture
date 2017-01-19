package com.delta.smt.ui.fault_processing.fault_solution.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.fault_processing.fault_solution.FaultSolutionDetailActivity;

import dagger.Component;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:49
 */
@ActivityScope
@Component(modules = {FaultSolutionModule.class},dependencies = AppComponent.class)
public interface FaultSolutionComponent {
    void inject(FaultSolutionDetailActivity activity);
}
