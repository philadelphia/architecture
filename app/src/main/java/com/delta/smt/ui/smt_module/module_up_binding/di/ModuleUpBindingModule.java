package com.delta.smt.ui.smt_module.module_up_binding.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.smt_module.module_up_binding.mvp.ModuleUpBindingContract;
import com.delta.smt.ui.smt_module.module_up_binding.mvp.ModuleUpBindingModel;

import dagger.Module;
import dagger.Provides;

/**
 * Author Shufeng.Wu
 * Date   2017/1/4
 */

@Module
public class ModuleUpBindingModule {
    private ModuleUpBindingContract.View view;

    public ModuleUpBindingModule(ModuleUpBindingContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public ModuleUpBindingContract.View providesView(){
        return view;
    }

    @ActivityScope
    @Provides
    public ModuleUpBindingContract.Model providesModel(ApiService apiService){
        return  new ModuleUpBindingModel(apiService);
    }
}
