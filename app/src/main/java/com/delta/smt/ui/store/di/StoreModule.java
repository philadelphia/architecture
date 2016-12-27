package com.delta.smt.ui.store.di;


import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.store.mvp.StoreContract;
import com.delta.smt.ui.store.mvp.StoreModel;

import dagger.Module;
import dagger.Provides;


/**
 * Created by Lin.Hou on 2016-12-26.
 */
@Module
public class StoreModule {

    StoreContract.View view;
    public StoreModule(StoreContract.View view) {
        this.view = view;
    }
    @ActivityScope
    @Provides
    StoreContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    StoreContract.Model providerModel(ApiService service) {
        return new StoreModel(service);
    }
}
