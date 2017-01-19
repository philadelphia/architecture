package com.delta.smt.ui.fault_processing.fault_add.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.fault_processing.fault_add.FaultProcessingAddActivity;

import dagger.Component;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:49
 */
@ActivityScope
@Component(modules = {FaultProcessingAddModule.class},dependencies = AppComponent.class)
public interface FaultProcessingAddComponent {
    void inject(FaultProcessingAddActivity activity);
}
