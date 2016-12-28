package com.delta.smt.ui.store.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.store.mvp.WarningListContract;
import com.delta.smt.ui.store.mvp.WarningListModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lin.Hou on 2016-12-27.
 */
@Module
public class WarningListModule {
    WarningListContract.View view;
    public WarningListModule(WarningListContract.View view){
        this.view=view;
    }
    @ActivityScope
    @Provides
    WarningListContract.View  providesview(){
        return view;
    }
    @ActivityScope
    @Provides
    WarningListContract.Model providesmodel(ApiService service){
        return new WarningListModel(service);
    }
}
