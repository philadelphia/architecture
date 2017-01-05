package com.delta.smt.ui.smt_module.module_down_details.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.smt_module.module_down.mvp.ModuleDownContract;
import com.delta.smt.ui.smt_module.module_down.mvp.ModuleDownModel;
import com.delta.smt.ui.smt_module.module_down_details.mvp.ModuleDownDetailsContract;
import com.delta.smt.ui.smt_module.module_down_details.mvp.ModuleDownDetailsModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shufeng.Wu on 2017/1/5.
 */

@Module
public class ModuleDownDetailsModule {
    private ModuleDownDetailsContract.View view;

    public ModuleDownDetailsModule(ModuleDownDetailsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public ModuleDownDetailsContract.View providesView(){
        return view;
    }

    @ActivityScope
    @Provides
    public ModuleDownDetailsContract.Model providesModel(ApiService apiService){
        return  new ModuleDownDetailsModel(apiService);
    }
}
