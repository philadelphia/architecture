package com.delta.smt.ui.mantissa_warehouse.detail.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.mantissa_warehouse.detail.MantissaWarehouseDetailsActivity;

import dagger.Component;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

@ActivityScope
@Component(modules = MantissaWarehouseDetailsModule.class, dependencies = AppComponent.class)
public interface MantissaWarehouseDetailsComponent {

    void inject(MantissaWarehouseDetailsActivity activity);

}
