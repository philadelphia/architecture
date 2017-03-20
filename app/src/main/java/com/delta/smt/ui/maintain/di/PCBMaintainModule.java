package com.delta.smt.ui.maintain.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.maintain.mvp.PCBMaintainContract;
import com.delta.smt.ui.maintain.mvp.PCBMaintainModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lin.Hou on 2017-03-13.
 */
@Module
public class PCBMaintainModule {
    PCBMaintainContract.View view;
    public PCBMaintainModule(PCBMaintainContract.View view){
        this.view=view;
    }
    @ActivityScope
    @Provides
    PCBMaintainContract.View providesview(){
        return view;
    }
    @ActivityScope
    @Provides
    PCBMaintainContract.Model providesModel(ApiService service){
        return new PCBMaintainModel(service);
    }
}
