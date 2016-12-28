package com.delta.smt.ui.mantissa_warehouse.ready.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.mantissa_warehouse.ready.MantissaWarehouseReadyActivity;

import dagger.Component;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */
@ActivityScope
@Component(modules = MantissaWarehouseReadyModule.class, dependencies = AppComponent.class)
public interface MantissaWarehouseReadyComponent {

    void inject(MantissaWarehouseReadyActivity activity);

}
