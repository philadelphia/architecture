package com.delta.smt.ui.feeder.feederCacheRegion.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.feeder.feederCacheRegion.mvp.FeederCacheRegionContract;
import com.delta.smt.ui.feeder.feederCacheRegion.mvp.FeederCacheRegionModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tao.ZT.Zhang on 2016/12/21.
 */

@Module
public class FeederCacheRegionModule {

    FeederCacheRegionContract.View view;
    public FeederCacheRegionModule(FeederCacheRegionContract.View view) {
        this.view = view;
    }
    @ActivityScope
    @Provides
    FeederCacheRegionContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    FeederCacheRegionContract.Model providerModel(ApiService service) {
        return new FeederCacheRegionModel(service);
    }
}
