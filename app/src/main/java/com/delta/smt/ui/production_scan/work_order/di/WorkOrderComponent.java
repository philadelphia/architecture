package com.delta.smt.ui.production_scan.work_order.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_scan.work_order.WorkOrderActivity;

import dagger.Component;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

@SuppressWarnings("all")
@ActivityScope
@Component(modules = WorkOrderModule.class, dependencies = AppComponent.class)
public interface WorkOrderComponent {
    void inject(WorkOrderActivity moduleUpActivity);
}
