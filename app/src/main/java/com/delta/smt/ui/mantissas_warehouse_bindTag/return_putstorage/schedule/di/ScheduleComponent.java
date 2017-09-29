package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.schedule.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.schedule.ScheduleFragment;

import dagger.Component;

/**
 * Created by Zhenyu.Liu on 2017/9/27.
 */
@ActivityScope
@Component(modules = ScheduleModule.class, dependencies = AppComponent.class)
public interface ScheduleComponent {

    void inject(ScheduleFragment fragment);

}
