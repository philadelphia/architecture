package com.delta.smt.ui.smt_module.module_down.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.smt_module.module_down.mvp.ModuleDownContract;
import com.delta.smt.ui.smt_module.module_down.mvp.ModuleDownModel;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpContract;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

@Module
public class ModuleDownModule {
    private ModuleDownContract.View view;

    public ModuleDownModule(ModuleDownContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public ModuleDownContract.View providesView(){
        return view;
    }

    @ActivityScope
    @Provides
    public ModuleDownContract.Model providesModel(ApiService apiService){
        return  new ModuleDownModel(apiService);
    }
}
