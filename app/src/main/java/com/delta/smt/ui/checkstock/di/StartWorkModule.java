package com.delta.smt.ui.checkstock.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.checkstock.mvp.StartWorkAndStopWorkContract;
import com.delta.smt.ui.checkstock.mvp.StartWorkAndStopWorkModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@Module
public class StartWorkModule {

    StartWorkAndStopWorkContract.View view;

    public StartWorkModule(StartWorkAndStopWorkContract.View view){
        this.view=view;
    }
    @ActivityScope
    @Provides
    StartWorkAndStopWorkContract.View providesview(){
        return view;
    }
    @ActivityScope
    @Provides
    StartWorkAndStopWorkContract.Model providesModel(ApiService service){
        return new StartWorkAndStopWorkModel(service);
    }
}
