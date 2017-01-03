package com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.MantissaWarehousePutstorageFragment;

import dagger.Component;

/**
 * Created by Zhenyu.Liu on 2016/12/30.
 */

@FragmentScope
@Component(modules = MantissaWarehousePutstorageModule.class, dependencies = AppComponent.class)
public interface MantissaWarehousePutstorageComponent {

    void inject(MantissaWarehousePutstorageFragment fragment);

}
