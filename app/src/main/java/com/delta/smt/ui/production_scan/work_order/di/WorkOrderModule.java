package com.delta.smt.ui.production_scan.work_order.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.production_scan.work_order.mvp.WorkOrderContract;
import com.delta.smt.ui.production_scan.work_order.mvp.WorkOrderModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

@Module
public class WorkOrderModule {
    private WorkOrderContract.View view;

    public WorkOrderModule(WorkOrderContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public WorkOrderContract.View providesView() {
        return view;
    }

    @ActivityScope
    @Provides
    public WorkOrderContract.Model providesModel(ApiService apiService) {
        return new WorkOrderModel(apiService);
    }
}
