package com.delta.smt.ui.production_warning.di.produce_warning_fragment;


import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment.ProduceBreakdownFragmentContract;
import com.delta.smt.ui.production_warning.mvp.produce_info_fragment.ProduceInfoFragment;
import com.delta.smt.ui.production_warning.mvp.produce_info_fragment.ProduceInfoFragmentContract;
import com.delta.smt.ui.production_warning.mvp.produce_info_fragment.ProduceInfoFragmentModel;
import com.delta.smt.ui.production_warning.mvp.produce_warning_fragment.ProduceWarningFragmentContract;
import com.delta.smt.ui.production_warning.mvp.produce_warning_fragment.ProduceWarningFragmentModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Fuxiang.Zhang on 2016/12/26.
 */
@Module
public class ProduceWarningFragmentModule {
    ProduceWarningFragmentContract.View mView;

    public ProduceWarningFragmentModule(ProduceWarningFragmentContract.View mView){
        this.mView= mView;
    }

    @FragmentScope
    @Provides
    ProduceWarningFragmentContract.View providerView(){
        return mView;
    }

    @FragmentScope
    @Provides
    ProduceWarningFragmentContract.Model ProviderModel(ApiService apiService){
        return new ProduceWarningFragmentModel(apiService);
    }

}
