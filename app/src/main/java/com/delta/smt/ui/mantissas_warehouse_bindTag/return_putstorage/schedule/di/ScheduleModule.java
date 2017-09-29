package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.schedule.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.schedule.mvp.ScheduleContract;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.schedule.mvp.ScheduleModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zhenyu.Liu on 2017/9/27.
 */

@Module
public class ScheduleModule {

    ScheduleContract.View view ;

    public ScheduleModule(ScheduleContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ScheduleContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    ScheduleContract.Model providerModel(ApiService service) {
        return new ScheduleModel(service);
    }
    
}
