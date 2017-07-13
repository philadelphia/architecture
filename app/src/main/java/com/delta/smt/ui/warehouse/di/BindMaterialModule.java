package com.delta.smt.ui.warehouse.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.warehouse.mvp.BindMaterialContract;
import com.delta.smt.ui.warehouse.mvp.BindMaterialModel;

import dagger.Module;
import dagger.Provides;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2017
 * @creator : YangS
 * @create-time : 2017/7/11 11:24
 * @description :
 */
@Module
public class BindMaterialModule {
    BindMaterialContract.View view;

    public BindMaterialModule(BindMaterialContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BindMaterialContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    BindMaterialContract.Model providerModel(ApiService service) {
        return new BindMaterialModel(service);
    }
}
