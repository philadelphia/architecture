package com.delta.smt.ui.production_warning.produce_info_fragment.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.production_warning.produce_info_fragment.mvp.ProduceInfoFragmentContract;
import com.delta.smt.ui.production_warning.produce_info_fragment.mvp.ProduceInfoFragmentModel;

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
