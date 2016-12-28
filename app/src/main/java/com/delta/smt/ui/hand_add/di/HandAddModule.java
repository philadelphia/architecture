package com.delta.smt.ui.hand_add.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.hand_add.mvp.HandAddContract;
import com.delta.smt.ui.hand_add.mvp.HandAddModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */
@Module
public class HandAddModule {
    HandAddContract.View mView;

    public HandAddModule(HandAddContract.View mView){
        this.mView=mView;
    }

    @ActivityScope
    @Provides
    HandAddContract.View providerView(){
        return mView;
    }

    @ActivityScope
    @Provides
    HandAddContract.Model providerModel(ApiService apiService){
        return new HandAddModel(apiService);
    }

}
