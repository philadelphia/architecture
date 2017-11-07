package com.example.app.ui.main.di;


import com.example.app.api.ApiService;
import com.example.app.ui.main.mvp.MainContract;
import com.example.app.ui.main.mvp.MainModel;
import com.example.commonlibs.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


/**
 * Created by V.Wenju.Tian on 2016/11/29.
 */

@Module
public class MainModule {

    MainContract.View view;
    public MainModule(MainContract.View view) {
        this.view = view;
    }
    @ActivityScope
    @Provides
    MainContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    MainContract.Model providerModel(ApiService service) {
        return new MainModel(service);
    }
}
