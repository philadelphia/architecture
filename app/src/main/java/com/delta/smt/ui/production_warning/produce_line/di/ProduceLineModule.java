package com.delta.smt.ui.production_warning.produce_line.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.production_warning.produce_line.mvp.ProduceLineContract;
import com.delta.smt.ui.production_warning.produce_line.mvp.ProduceLineModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */

@Module
public class ProduceLineModule {
    ProduceLineContract.View mView;

    public ProduceLineModule(ProduceLineContract.View mView){
        this.mView=mView;
    }

    @ActivityScope
    @Provides
    ProduceLineContract.View providerview(){
        return mView;
    }

    @ActivityScope
    @Provides
    ProduceLineContract.Model providermdel(ApiService apiService){
        return  new ProduceLineModel(apiService);
    }
}
