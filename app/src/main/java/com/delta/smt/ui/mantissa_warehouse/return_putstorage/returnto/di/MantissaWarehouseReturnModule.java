package com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.mvp.MantissaWarehouseReturnContract;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.mvp.MantissaWarehouseReturnModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

@Module
public class MantissaWarehouseReturnModule {

    MantissaWarehouseReturnContract.View view;

    public MantissaWarehouseReturnModule( MantissaWarehouseReturnContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    MantissaWarehouseReturnContract.View providerView() {
        return view;
    }

    @FragmentScope
    @Provides
    MantissaWarehouseReturnContract.Model providerModel(ApiService service) {
        return new MantissaWarehouseReturnModel(service);
    }

}
