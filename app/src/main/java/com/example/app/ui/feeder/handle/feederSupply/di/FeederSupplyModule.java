package com.example.app.ui.feeder.handle.feederSupply.di;

import com.example.commonlibs.di.scope.ActivityScope;
import com.example.app.api.ApiService;
import com.example.app.ui.feeder.handle.feederSupply.mvp.FeederSupplyContract;
import com.example.app.ui.feeder.handle.feederSupply.mvp.FeederSupplyModel;

import dagger.Module;
import dagger.Provides;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */

@Module
public class FeederSupplyModule {
    private FeederSupplyContract.View view ;

    public FeederSupplyModule(FeederSupplyContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public FeederSupplyContract.View providesView(){
        return view;
    }

    @ActivityScope
    @Provides
    public FeederSupplyContract.Model providesModel(ApiService apiService){
        return  new FeederSupplyModel(apiService);
    }
}
