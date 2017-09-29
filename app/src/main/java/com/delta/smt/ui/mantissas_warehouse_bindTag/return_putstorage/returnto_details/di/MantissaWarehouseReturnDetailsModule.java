package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.returnto_details.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.returnto_details.mvp.MantissaWarehouseReturnDetailsContract;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.returnto_details.mvp.MantissaWarehouseReturnDetailsModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

@Module
public class MantissaWarehouseReturnDetailsModule {

    MantissaWarehouseReturnDetailsContract.View view;

    public MantissaWarehouseReturnDetailsModule(MantissaWarehouseReturnDetailsContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    MantissaWarehouseReturnDetailsContract.View providerView() {
        return view;
    }

    @FragmentScope
    @Provides
    MantissaWarehouseReturnDetailsContract.Model providerModel(ApiService service) {
        return new MantissaWarehouseReturnDetailsModel(service);
    }

}
