package com.delta.smt.ui.store.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.store.mvp.WarningContract;
import com.delta.smt.ui.store.mvp.WarningModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@Module
public class WarningModule {
    WarningContract.View view;
    public WarningModule(WarningContract.View view){
        this.view=view;
    }
    @FragmentScope
    @Provides
    WarningContract.View providesview(){
        return view;
    }
    @FragmentScope
    @Provides
    WarningContract.Model providesmodel(ApiService service){
        return new WarningModel(service);
    }
}
