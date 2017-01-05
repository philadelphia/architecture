package com.delta.smt.ui.fault_processing.processing.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.fault_processing.processing.mvp.FalutProcessingContract;
import com.delta.smt.ui.fault_processing.processing.mvp.FalutProcessingModel;

import dagger.Module;
import dagger.Provides;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:49
 */

@Module
public class FaultProcessingModule {


    FalutProcessingContract.View view;

    public FaultProcessingModule(FalutProcessingContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FalutProcessingContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    FalutProcessingContract.Model providerModel(ApiService service) {
        return new FalutProcessingModel(service);
    }
}
