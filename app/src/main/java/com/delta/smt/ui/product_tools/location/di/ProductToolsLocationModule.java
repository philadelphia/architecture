package com.delta.smt.ui.product_tools.location.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.product_tools.location.mvp.ProduceToolsLocationContract;
import com.delta.smt.ui.product_tools.location.mvp.ProduceToolsLocationModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shaoqiang.Zhang on 2017/1/18.
 */
@Module
public class ProductToolsLocationModule {

    ProduceToolsLocationContract.View view;

    public ProductToolsLocationModule(ProduceToolsLocationContract.View view){this.view=view;}

    @ActivityScope
    @Provides
    ProduceToolsLocationContract.View getView(){return this.view;}

    @ActivityScope
    @Provides
    ProduceToolsLocationContract.Model getModel(ApiService apiService){
        return new ProduceToolsLocationModel(apiService);
    }

}
