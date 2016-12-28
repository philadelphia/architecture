package com.delta.smt.ui.feeder.warning.supply.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.feeder.warning.supply.mvp.SupplyContract;
import com.delta.smt.ui.feeder.warning.supply.mvp.SupplyModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

@Module
public class SupplyModule {
    private SupplyContract.View view ;

    public SupplyModule(SupplyContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SupplyContract.View providesView(){
        return view;
    }

    @FragmentScope
    @Provides
    public SupplyContract.Model providesModel(ApiService apiService){
        return  new SupplyModel(apiService);
    }
}
