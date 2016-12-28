package com.delta.smt.ui.storage_manger.details.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.storage_manger.details.mvp.StorageDetailsContract;
import com.delta.smt.ui.storage_manger.details.mvp.StorageDetailsModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

@Module
public class StorageDetailsModule {

    StorageDetailsContract.View view;

    public StorageDetailsModule(StorageDetailsContract.View view) {
        view = view;
    }

    @ActivityScope
    @Provides
    StorageDetailsContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    StorageDetailsContract.Model providerModel(ApiService service) {
        return new StorageDetailsModel(service);
    }

}
