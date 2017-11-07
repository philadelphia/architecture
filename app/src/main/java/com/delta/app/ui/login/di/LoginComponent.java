package com.delta.app.ui.login.di;


import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.app.di.component.AppComponent;
import com.delta.app.ui.login.LoginActivity;
import com.delta.app.ui.warningSample.WarningSampleActivity;

import dagger.Component;

/**
 * Created by V.Wenju.Tian on 2016/11/29.
 */
@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);

    void inject(WarningSampleActivity activity);
}
