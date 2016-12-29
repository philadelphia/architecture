package com.delta.smt.ui.mantissa_warehouse.detail.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.mantissa_warehouse.detail.mvp.MantissaWarehouseDetailsContract;
import com.delta.smt.ui.mantissa_warehouse.detail.mvp.MantissaWarehouseDetailsModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */
@Module
public class MantissaWarehouseDetailsModule {

    MantissaWarehouseDetailsContract.View view;

    public MantissaWarehouseDetailsModule( MantissaWarehouseDetailsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MantissaWarehouseDetailsContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    MantissaWarehouseDetailsContract.Model providerModel(ApiService service) {
        return new MantissaWarehouseDetailsModel(service);
    }


}
