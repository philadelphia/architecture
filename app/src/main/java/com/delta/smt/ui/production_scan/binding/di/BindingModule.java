package com.delta.smt.ui.production_scan.binding.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.production_scan.binding.mvp.BindingContract;
import com.delta.smt.ui.production_scan.binding.mvp.BindingModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

@Module
public class BindingModule {

    BindingContract.View view;

    public BindingModule(BindingContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BindingContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    BindingContract.Model providerModel(ApiService service) {
        return new BindingModel(service);
    }

}
