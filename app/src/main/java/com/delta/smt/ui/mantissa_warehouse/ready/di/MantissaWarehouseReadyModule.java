package com.delta.smt.ui.mantissa_warehouse.ready.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.mantissa_warehouse.ready.mvp.MantissaWarehouseReadyContract;
import com.delta.smt.ui.mantissa_warehouse.ready.mvp.MantissaWarehouseReadyModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */

@Module
public class MantissaWarehouseReadyModule {

    MantissaWarehouseReadyContract.View view ;

    public MantissaWarehouseReadyModule(MantissaWarehouseReadyContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MantissaWarehouseReadyContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    MantissaWarehouseReadyContract.Model providerModel(ApiService service) {
        return new MantissaWarehouseReadyModel(service);
    }


}
