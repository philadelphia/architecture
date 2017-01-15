package com.delta.smt.ui.main.di.update;

import com.delta.smt.ui.main.update.DownloadProgressInterceptor;
import com.delta.smt.utils.StringUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 更新
 * Created by Shufeng.Wu on 2016/12/28.
 */
@Module
public class UpdateClientModule {

    private static final int TOME_OUT = 5;
    private String urlStr;
    private DownloadProgressInterceptor interceptor;

    public UpdateClientModule(String urlStr, DownloadProgressInterceptor interceptor){
        this.urlStr = urlStr;
        this.interceptor = interceptor;
    }

    @Singleton
    @Provides
    public Retrofit provideUpdateRetrofit(String urlStr, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(StringUtils.getHostName(urlStr))
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public String provideString(){
        return this.urlStr;
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(DownloadProgressInterceptor interceptor){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(TOME_OUT, TimeUnit.SECONDS)
                .build();
        return client;
    }

    @Singleton
    @Provides
    public DownloadProgressInterceptor provideDownloadProgressInterceptor(){
        return this.interceptor;
    }

}
