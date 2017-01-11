package com.delta.smt.ui.feeder.warning.supply.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.feeder.warning.supply.mvp.SupplyContract;
import com.delta.smt.ui.feeder.warning.supply.mvp.SupplyModel;

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
