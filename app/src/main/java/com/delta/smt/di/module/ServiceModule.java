package com.delta.smt.di.module;


import com.delta.smt.api.ApiService;
import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.User;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import rx.Observable;


@Module
public class ServiceModule {

    @Singleton
    @Provides
    ApiService provideCommonService(Retrofit retrofit) {
        return new ApiService() {
            @Override
            public Observable<LoginResult> login(@Body User user) {
                LoginResult result = new LoginResult();
                LoginResult.MessageBean messageBean = new LoginResult.MessageBean();
                messageBean.setUserName("王五");
                messageBean.setToken("---------------");
                result.setMessage(messageBean);
                return Observable.just(result);
            }
        };
    }

}
