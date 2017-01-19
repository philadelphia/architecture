package com.delta.smt.ui.fault_processing.fault_add.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.fault_processing.fault_add.mvp.FaultProcessingAddContract;
import com.delta.smt.ui.fault_processing.fault_add.mvp.FaultProcessingAddModel;

import dagger.Module;
import dagger.Provides;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:49
 */

@Module
public class FaultProcessingAddModule {


    FaultProcessingAddContract.View view;

    public FaultProcessingAddModule(FaultProcessingAddContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FaultProcessingAddContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    FaultProcessingAddContract.Model providerModel(ApiService service) {
        return new FaultProcessingAddModel(service);
    }
}
