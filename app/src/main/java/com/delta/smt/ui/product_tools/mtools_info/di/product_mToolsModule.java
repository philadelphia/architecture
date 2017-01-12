package com.delta.smt.ui.product_tools.mtools_info.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.product_tools.mtools_info.mvp.Produce_mToolsContract;
import com.delta.smt.ui.product_tools.mtools_info.mvp.Produce_mToolsModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shaoqiang.Zhang on 2017/1/11.
 */

@Module
public class Product_mToolsModule {

    Produce_mToolsContract.View view;

    public Product_mToolsModule(Produce_mToolsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    Produce_mToolsContract.View getView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    Produce_mToolsContract.Model getModel(ApiService apiService){
        return new Produce_mToolsModel(apiService);
    }

}
