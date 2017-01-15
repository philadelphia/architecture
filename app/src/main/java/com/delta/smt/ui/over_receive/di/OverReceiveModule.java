package com.delta.smt.ui.over_receive.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.over_receive.mvp.OverReceiveContract;
import com.delta.smt.ui.over_receive.mvp.OverReceiveModel;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpContract;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpModel;

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
