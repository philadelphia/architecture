package com.delta.smt.ui.storage_manger.ready.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyContract;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zhenyu.Liu on 2016/12/22.
 */

@Module
public class StorageReadyModule {


    StorageReadyContract.View view ;

    public StorageReadyModule(StorageReadyContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    StorageReadyContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    StorageReadyContract.Model providerModel(ApiService service) {
        return new StorageReadyModel(service);
    }

}
