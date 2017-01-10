package com.delta.smt.ui.storage_manger.storage_select.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.storage_manger.storage_select.mvp.StorageSelectContract;
import com.delta.smt.ui.storage_manger.storage_select.mvp.StorageSelectModel;

import dagger.Module;
import dagger.Provides;


@Module
public class StorageSelectModule {

    StorageSelectContract.View view;
    public StorageSelectModule(StorageSelectContract.View view) {
        this.view = view;
    }
    @ActivityScope
    @Provides
    StorageSelectContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    StorageSelectContract.Model providerModel(ApiService service) {
        return new StorageSelectModel(service);
    }
}
