package com.delta.smt.ui.main.di.update;

import com.delta.smt.api.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * 更新
 * Created by Shufeng.Wu on 2016/12/28.
 */

@Module
public class UpdateServiceModule {
    @Singleton
    @Provides
    public ApiService provideService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }
}
