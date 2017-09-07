package com.delta.smt.ui.production_warning.accept_materials_detail.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.production_warning.accept_materials_detail.mvp.AcceptMaterialsContract;
import com.delta.smt.ui.production_warning.accept_materials_detail.mvp.AcceptMaterialsModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */
@Module
public class AcceptMaterialsModule {
    private AcceptMaterialsContract.View mView;

    public AcceptMaterialsModule(AcceptMaterialsContract.View mView){
        this.mView=mView;
    }

    @ActivityScope
    @Provides
    AcceptMaterialsContract.View providerView(){
        return mView;
    }

    @ActivityScope
    @Provides
    AcceptMaterialsContract.Model providerModel(ApiService apiService){
        return new AcceptMaterialsModel(apiService);
    }
}
