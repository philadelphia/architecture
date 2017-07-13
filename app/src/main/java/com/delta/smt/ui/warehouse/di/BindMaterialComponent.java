package com.delta.smt.ui.warehouse.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.warehouse.BindMaterialCarActivity;

import dagger.Component;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2017
 * @creator : YangS
 * @create-time : 2017/7/11 11:33
 * @description :
 */
@ActivityScope
@Component(modules = BindMaterialModule.class, dependencies = AppComponent.class)
public interface BindMaterialComponent {
    void inject(BindMaterialCarActivity activity);
}
