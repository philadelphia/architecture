package com.delta.smt.ui.production_warning.di.produce_line;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.production_warning.mvp.produce_line.ProduceLineContract;
import com.delta.smt.ui.production_warning.mvp.produce_line.ProduceLineModel;

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
