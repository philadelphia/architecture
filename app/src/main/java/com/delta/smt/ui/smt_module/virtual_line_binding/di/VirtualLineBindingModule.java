package com.delta.smt.ui.smt_module.virtual_line_binding.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.smt_module.virtual_line_binding.mvp.VirtualLineBindingContract;
import com.delta.smt.ui.smt_module.virtual_line_binding.mvp.VirtualLineBindingModel;

import dagger.Module;
import dagger.Provides;

/**
 * Author Shufeng.Wu
 * Date   2017/1/4
 */

@Module
public class VirtualLineBindingModule {
    private VirtualLineBindingContract.View view;

    public VirtualLineBindingModule(VirtualLineBindingContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public VirtualLineBindingContract.View providesView(){
        return view;
    }

    @ActivityScope
    @Provides
    public VirtualLineBindingContract.Model providesModel(ApiService apiService){
        return  new VirtualLineBindingModel(apiService);
    }
}
