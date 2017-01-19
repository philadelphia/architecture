package com.delta.smt.ui.fault_processing.fault_solution.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.fault_processing.fault_solution.mvp.FaultSolutionContract;
import com.delta.smt.ui.fault_processing.fault_solution.mvp.FaultSolutionModel;

import dagger.Module;
import dagger.Provides;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:49
 */

@Module
public class FaultSolutionModule {


    FaultSolutionContract.View view;

    public FaultSolutionModule(FaultSolutionContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FaultSolutionContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    FaultSolutionContract.Model providerModel(ApiService service) {
        return new FaultSolutionModel(service);
    }
}
