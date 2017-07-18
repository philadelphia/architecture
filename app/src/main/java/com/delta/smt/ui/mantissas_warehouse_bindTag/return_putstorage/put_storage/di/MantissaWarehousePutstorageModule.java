package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.put_storage.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.put_storage.mvp.MantissaWarehousePutstorageContract;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.put_storage.mvp.MantissaWarehousePutstorageModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

@Module
public class MantissaWarehousePutstorageModule {

    MantissaWarehousePutstorageContract.View view;

    public MantissaWarehousePutstorageModule(MantissaWarehousePutstorageContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    MantissaWarehousePutstorageContract.View providerView() {
        return view;
    }

    @FragmentScope
    @Provides
    MantissaWarehousePutstorageContract.Model providerModel(ApiService service) {
        return new MantissaWarehousePutstorageModel(service);
    }

}
