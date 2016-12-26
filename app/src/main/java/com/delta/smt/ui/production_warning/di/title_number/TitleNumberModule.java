package com.delta.smt.ui.production_warning.di.title_number;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningContract;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Fuxiang.Zhang on 2016/12/26.
 */
@Module
public class TitleNumberModule {
    ProduceWarningContract.View mView;

    public TitleNumberModule(ProduceWarningContract.View mView){
        this.mView=mView;
    }

    @ActivityScope
    @Provides
    ProduceWarningContract.View providerView(){
        return mView;
    }

    @ActivityScope
    @Provides
    ProduceWarningContract.Model providerModel(ApiService apiService){
        return new ProduceWarningModel(apiService);
    }
}
