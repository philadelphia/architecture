package com.delta.smt.ui.feeder.handle.feederCheckIn.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.di.component.AppComponent;

import dagger.Component;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */

@FragmentScope
@Component(modules=FeederCheckInModule.class, dependencies = AppComponent.class)

public interface FeederCheckInComponent {
//    void inject(CheckInFragment checkinFragment);
}
