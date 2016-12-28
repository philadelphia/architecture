package com.delta.smt.ui.feeder.handle.feederSupply.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.feeder.handle.feederSupply.mvp.FeederSupplyContract;
import com.delta.smt.ui.feeder.handle.feederSupply.mvp.FeederSupplyModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

@Module
public class FeederSupplyModule {
    private FeederSupplyContract.View view ;

    public FeederSupplyModule(FeederSupplyContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public FeederSupplyContract.View providesView(){
        return view;
    }

    @FragmentScope
    @Provides
    public FeederSupplyContract.Model providesModel(ApiService apiService){
        return  new FeederSupplyModel(apiService);
    }
}
