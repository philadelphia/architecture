package com.delta.smt.ui.feeder.warning.checkin.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.feeder.warning.CheckinFragment;

import dagger.Component;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

@FragmentScope
@Component(modules=CheckInModule.class, dependencies = AppComponent.class)

public interface CheckInComponent {
    void inject(CheckinFragment checkinFragment);
}
