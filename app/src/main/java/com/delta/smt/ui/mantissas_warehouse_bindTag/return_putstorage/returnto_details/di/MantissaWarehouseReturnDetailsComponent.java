package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.returnto_details.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.returnto_details.MantissaWarehouseReturnDetailsActivity;

import dagger.Component;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

@FragmentScope
@Component(modules = MantissaWarehouseReturnDetailsModule.class, dependencies = AppComponent.class)
public interface MantissaWarehouseReturnDetailsComponent {
    
    void inject(MantissaWarehouseReturnDetailsActivity activity);

}
