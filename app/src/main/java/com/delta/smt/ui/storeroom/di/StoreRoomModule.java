package com.delta.smt.ui.storeroom.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.storeroom.mvp.StoreRoomContract;
import com.delta.smt.ui.storeroom.mvp.StoreRoomModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@Module
public class StoreRoomModule  {
    StoreRoomContract.View view;
    public StoreRoomModule(StoreRoomContract.View view){
        this.view=view;
    }
    @ActivityScope
    @Provides
    StoreRoomContract.View providesView(){
        return view;
    }
    @ActivityScope
    @Provides
    StoreRoomContract.Model providesModel(ApiService service){
        return new StoreRoomModel(service);
    }
}

