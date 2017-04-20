package com.delta.smt.ui.quality_manage.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.quality_manage.QualityManageActivity;

import dagger.Component;

/**
 * Created by Zhenyu.Liu on 2017/4/19.
 */

@ActivityScope
@Component(modules = QualityManageModule.class, dependencies = AppComponent.class)
public interface QualityManageComponent {

    void inject(QualityManageActivity activity);

}
