package com.delta.smt.ui.checkstock.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.checkstock.mvp.CheckStockContract;
import com.delta.smt.ui.checkstock.mvp.CheckStockModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@Module
public class CheckStockModule {

    CheckStockContract.View view;
    public CheckStockModule(CheckStockContract.View view){
        this.view=view;
    }
    @ActivityScope
    @Provides
    CheckStockContract.View providesview(){
        return view;
    }
    @ActivityScope
    @Provides
    CheckStockContract.Model providesModel(ApiService service){
        return new CheckStockModel(service);
    }
}
