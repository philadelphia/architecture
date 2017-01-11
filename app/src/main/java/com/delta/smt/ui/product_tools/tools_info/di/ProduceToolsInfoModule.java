package com.delta.smt.ui.product_tools.tools_info.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.product_tools.tools_info.mvp.ProduceToolsInfoContract;
import com.delta.smt.ui.product_tools.tools_info.mvp.ProduceToolsInfoModel;
import com.delta.smt.ui.product_tools.tools_info.mvp.ProduceToolsInfoPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shaoqiang.Zhang on 2017/1/10.
 */
@Module
public class ProduceToolsInfoModule {

    ProduceToolsInfoContract.View view;

    public ProduceToolsInfoModule(ProduceToolsInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ProduceToolsInfoContract.View getView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ProduceToolsInfoContract.Model getModel(ApiService apiService){
        return new ProduceToolsInfoModel(apiService);
    }

}
