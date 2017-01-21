package com.delta.smt.ui.over_receive.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.over_receive.mvp.OverReceiveContract;
import com.delta.smt.ui.over_receive.mvp.OverReceiveModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shufeng.Wu on 2017/1/15.
 */

@Module
public class OverReceiveModule {
    private OverReceiveContract.View view;

    public OverReceiveModule(OverReceiveContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public OverReceiveContract.View providesView(){
        return view;
    }

    @ActivityScope
    @Provides
    public OverReceiveContract.Model providesModel(ApiService apiService){
        return  new OverReceiveModel(apiService);
    }
}
