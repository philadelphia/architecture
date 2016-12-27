package com.delta.smt.ui.feeder.wareSelect.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.feeder.wareSelect.mvp.WareSelectContract;
import com.delta.smt.ui.feeder.wareSelect.mvp.WareSelectModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tao.ZT.Zhang on 2016/12/21.
 */

@Module
public class WareSelectModule {

    WareSelectContract.View view;
    public WareSelectModule(WareSelectContract.View view) {
        this.view = view;
    }
    @ActivityScope
    @Provides
    WareSelectContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    WareSelectContract.Model providerModel(ApiService service) {
        return new WareSelectModel(service);
    }
}
