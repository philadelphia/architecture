package com.delta.app.ui.feeder.warning.supply.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.app.api.ApiService;
import com.delta.app.ui.feeder.warning.supply.mvp.SupplyContract;
import com.delta.app.ui.feeder.warning.supply.mvp.SupplyModel;

import dagger.Module;
import dagger.Provides;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

@Module
public class SupplyModule {
    private SupplyContract.View view ;

    public SupplyModule(SupplyContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public SupplyContract.View providesView(){
        return view;
    }

    @ActivityScope
    @Provides
    public SupplyContract.Model providesModel(ApiService apiService){
        return  new SupplyModel(apiService);
    }
}
