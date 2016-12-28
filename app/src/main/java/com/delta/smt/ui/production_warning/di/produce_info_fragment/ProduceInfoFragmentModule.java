package com.delta.smt.ui.production_warning.di.produce_info_fragment;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.production_warning.mvp.produce_info_fragment.ProduceInfoFragmentContract;
import com.delta.smt.ui.production_warning.mvp.produce_info_fragment.ProduceInfoFragmentModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */
@Module
public class ProduceInfoFragmentModule {
    ProduceInfoFragmentContract.View mView;

    public ProduceInfoFragmentModule(ProduceInfoFragmentContract.View mView){
        this.mView=mView;
    }

    @FragmentScope
    @Provides
    ProduceInfoFragmentContract.View ProvideView(){
        return mView;
    }

    @FragmentScope
    @Provides
    ProduceInfoFragmentContract.Model ProvideModel(ApiService apiService){
        return new ProduceInfoFragmentModel(apiService);
    }
}
