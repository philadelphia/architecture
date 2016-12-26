package com.delta.smt.ui.feederwarning.feederCheckIn.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.feederwarning.feederCheckIn.mvp.FeederCheckInContract;
import com.delta.smt.ui.feederwarning.feederCheckIn.mvp.FeederCheckInModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

@Module
public class FeederCheckInModule {
    FeederCheckInContract.View view;

    public FeederCheckInModule(FeederCheckInContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    FeederCheckInContract.View providesView(){
        return  view;
    }

    @ActivityScope
    @Provides
    FeederCheckInContract.Model providesModel(ApiService apiService){
        return new FeederCheckInModel(apiService);
    }
}

