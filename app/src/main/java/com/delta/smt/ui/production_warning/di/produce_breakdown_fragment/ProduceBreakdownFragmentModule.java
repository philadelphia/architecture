package com.delta.smt.ui.production_warning.di.produce_breakdown_fragment;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment.ProduceBreakdownFragmentContract;
import com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment.ProduceBreakdownFragmentModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */
@Module
public class ProduceBreakdownFragmentModule {
    ProduceBreakdownFragmentContract.View mView;

    public ProduceBreakdownFragmentModule(ProduceBreakdownFragmentContract.View mview){
        this.mView=mview;
    }

    @FragmentScope
    @Provides
    ProduceBreakdownFragmentContract.View providerView(){
        return  mView;
    }

    @FragmentScope
    @Provides
    ProduceBreakdownFragmentContract.Model providerModel(ApiService apiService){
        return new ProduceBreakdownFragmentModel(apiService);
    }

}
