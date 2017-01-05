package com.delta.smt.ui.fault_processing.processing.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.fault_processing.processing.FalutProcessingActivity;
import dagger.Component;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:49
 */
@ActivityScope
@Component(modules = {FaultProcessingModule.class},dependencies = AppComponent.class)
public interface FaultProcessingComponent {
    void inject(FalutProcessingActivity activity);
}
