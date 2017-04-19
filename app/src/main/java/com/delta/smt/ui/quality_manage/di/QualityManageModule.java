package com.delta.smt.ui.quality_manage.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.quality_manage.mvp.QualityManageContract;
import com.delta.smt.ui.quality_manage.mvp.QualityManageModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zhenyu.Liu on 2017/4/19.
 */

@Module
public class QualityManageModule {

    QualityManageContract.View view ;


    public QualityManageModule( QualityManageContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    QualityManageContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    QualityManageContract.Model providerModel(ApiService service) {
        return new QualityManageModel(service);
    }


}
