package com.delta.smt.ui.product_tools.back.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.product_tools.back.mvp.ProduceToolsBackContract;
import com.delta.smt.ui.product_tools.back.mvp.ProduceToolsBackModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shaoqiang.Zhang on 2017/1/11.
 */

@Module
public class ProduceToolsBackModule {

    ProduceToolsBackContract.View view;

    public ProduceToolsBackModule(ProduceToolsBackContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ProduceToolsBackContract.View getView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ProduceToolsBackContract.Model getModel(ApiService service){
        return new ProduceToolsBackModel(service);
    }

}
