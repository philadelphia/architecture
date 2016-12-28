package com.delta.smt.ui.feeder.handle.feederCheckIn.di;


import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.feeder.warning.checkin.mvp.CheckInContract;
import com.delta.smt.ui.feeder.warning.checkin.mvp.CheckInModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

@Module
public class FeederCheckInModule {
    CheckInContract.View view;

    public FeederCheckInModule(CheckInContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    CheckInContract.View providesView(){
        return  view;
    }

    @FragmentScope
    @Provides
    CheckInContract.Model providesModel(ApiService apiService){
        return new CheckInModel(apiService);
    }
}

