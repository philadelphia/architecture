package com.delta.smt.ui.feederwarning.feederCheckIn.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.feederwarning.CheckinFragment;

import dagger.Component;
import dagger.Module;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

@ActivityScope
@Component(modules=FeederCheckInModule.class, dependencies = AppComponent.class)

public interface FeederCheckInComponent {
    void inject(CheckinFragment checkinFragment);
}
