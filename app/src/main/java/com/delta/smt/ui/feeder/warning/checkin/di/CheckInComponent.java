package com.delta.smt.ui.feeder.warning.checkin.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.feeder.warning.CheckInFragment;

import dagger.Component;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

@FragmentScope
@Component(modules=CheckInModule.class, dependencies = AppComponent.class)

public interface CheckInComponent {
    void inject(CheckInFragment checkinFragment);
}
