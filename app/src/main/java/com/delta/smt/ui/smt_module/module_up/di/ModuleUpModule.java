package com.delta.smt.ui.smt_module.module_up.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.feeder.handle.feederSupply.mvp.FeederSupplyContract;
import com.delta.smt.ui.feeder.handle.feederSupply.mvp.FeederSupplyModel;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpContract;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

@Module
public class ModuleUpModule {
    private ModuleUpContract.View view;

    public ModuleUpModule(ModuleUpContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public ModuleUpContract.View providesView(){
        return view;
    }

    @ActivityScope
    @Provides
    public ModuleUpContract.Model providesModel(ApiService apiService){
        return  new ModuleUpModel(apiService);
    }
}
