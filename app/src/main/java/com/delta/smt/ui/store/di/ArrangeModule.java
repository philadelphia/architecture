package com.delta.smt.ui.store.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.store.mvp.ArrangeContract;
import com.delta.smt.ui.store.mvp.ArrangeModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@Module
public class ArrangeModule {
    private ArrangeContract.View view;
    public ArrangeModule(ArrangeContract.View view){
        this.view=view;
    }
    @FragmentScope
    @Provides
    ArrangeContract.View providesview(){
        return view;
    }
    @FragmentScope
    @Provides
    ArrangeContract.Model providesmodel(ApiService service){
        return new ArrangeModel(service);
    }
}
