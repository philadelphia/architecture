package com.example.app.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.commonlibs.di.module.AppModule;
import com.example.commonlibs.di.module.ClientModule;
import com.example.commonlibs.http.GlobeHttpHandler;
import com.example.commonlibs.rxerrorhandler.ResponseErrorListener;
import com.example.app.BuildConfig;
import com.example.app.di.module.ServiceModule;
import com.example.app.utils.UiUtils;
import com.facebook.stetho.Stetho;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.LinkedList;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public abstract class BaseApplication extends Application implements ResponseErrorListener {
    static private BaseApplication mApplication;
    private LinkedList<BaseCommonActivity> mActivityList;
    private static ClientModule mClientModule;
    private static AppModule mAppModule;
    private static ServiceModule serviceModule;
    protected final String TAG = this.getClass().getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }
        AutoLayoutConifg.getInstance().useDeviceSize();
        mApplication = this;
        mClientModule = ClientModule//用于提供okhttp和retrofit的单列
                .buidler()
                .baseurl(getBaseUrl())
                .globeHttpHandler(getHttpHandler())
                .interceptors(getInterceptors())
                .responseErroListener(getResponseErrorListener())
                .build();
        mAppModule = new AppModule(this);//提供application
        serviceModule = new ServiceModule();
    }


    /**
     * 提供基础url给retrofit
     *
     * @return
     */
    protected abstract String getBaseUrl();


    public static ServiceModule getServiceModule() {
        return serviceModule;
    }

    public static ClientModule getClientModule() {
        return mClientModule;
    }

    public static AppModule getAppModule() {
        return mAppModule;
    }

    /**
     * 这里可以提供一个全局处理http响应结果的处理类,
     * 这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
     * 默认不实现,如果有需求可以重写此方法
     *
     * @return
     */

    public static GlobeHttpHandler getHttpHandler() {
        return new GlobeHttpHandler() {
            @Override
            public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                //这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                //重新请求token,并重新执行请求
//                try {
//                    JSONArray array = new JSONArray(httpResult);
//                    JSONObject object = (JSONObject) array.get(0);
//                    String login = object.getString("login");
//                    String avatar_url = object.getString("avatar_url");
//                    Timber.tag(TAG).w("result ------>" + login + "    ||   avatar_url------>" + avatar_url);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//

                //这里如果发现token过期,可以先请求最新的token,然后在拿新的token去重新请求之前的http请求
                // create a new request and modify it accordingly using the new token
//                    Request newRequest = chain.request().newBuilder().header("token", newToken)
//                            .build();
//
//                    // retry the request
//
//                    response.body().close();
//                    try {
//                        return chain.proceed(newRequest);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                //如果需要返回新的结果,则直接把response参数返回出去
                return response;
            }

            @Override
            public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                //如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的requeat如增加header,不做操作则返回request

                //return chain.request().newBuilder().header("token", tokenId)
//                .build();
                return request;
            }
        };
    }

    /**
     * 用来提供interceptor,如果要提供额外的interceptor可以让子application实现此方法
     *
     * @return
     */
    public static Interceptor[] getInterceptors() {
//        return new Interceptor[]{new StethoInterceptor()}
//                ;
        return null;
    }

    /**
     * 返回上下文
     *
     * @return
     */
    public static Context getContext() {
        return mApplication;
    }

    /**
     * 返回一个存储所有存在的activity的列表
     *
     * @return
     */
    public LinkedList<BaseCommonActivity> getActivityList() {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        return mActivityList;
    }

    /**
     * 退出所有activity
     */
    public static void killAll() {
        Intent intent = new Intent(BaseCommonActivity.ACTION_RECEIVER_ACTIVITY);
        intent.putExtra("type", "killAll");
        getContext().sendBroadcast(intent);
    }

    @Override
    public void handlerResponseError(Context context, Exception e) {
        Timber.tag("error").w(e.getMessage());
//        UiUtils.SnackbarText("net error");
        UiUtils.HandError("net error");
    }

    public ResponseErrorListener getResponseErrorListener() {
        return this;
    }
}
