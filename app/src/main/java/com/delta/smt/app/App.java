package com.delta.smt.app;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.delta.commonlibs.utils.DeviceUuidFactory;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.smt.api.API;
import com.delta.smt.base.BaseApplication;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.di.component.DaggerAppComponent;
import com.delta.smt.manager.ActivityMonitor;
import com.delta.smt.manager.ActivityState;
import com.delta.smt.service.warningService.WarningService;
import com.delta.ttsmanager.TextToSpeechManager;
import com.delta.updatelibs.UpdateUtils;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

import javax.inject.Inject;

/**
 * Created by V.Wenju.Tian on 2016/11/29.
 */
@ReportsCrashes(
        mode = ReportingInteractionMode.TOAST,
        //resToastText =  R.string.crash_toast_text,
        formUri = "http://172.22.34.198:8888/acra-smartsmt",
        httpMethod = HttpSender.Method.PUT,
        reportType = HttpSender.Type.JSON

)
public class App extends BaseApplication implements Application.ActivityLifecycleCallbacks {

    public static AppComponent appComponent;
    private static int appCount = 0;
    private static Handler mainHander;
    private static Context mContenxt;
    @Inject
    TextToSpeechManager textToSpeechManager;

    public static Context getmContenxt() {
        return mContenxt;
    }

    public static Handler getMainHander() {
        return mainHander;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static int getAppCount() {
        return appCount;
    }

    public static boolean isForeground() {
        return getAppCount() > 0;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new DeviceUuidFactory(this);
        ACRA.init(this);
        mContenxt = this;
        mainHander = new Handler(Looper.getMainLooper());
        appComponent = DaggerAppComponent.builder().clientModule(getClientModule()).appModule(getAppModule()).serviceModule(getServiceModule()).build();
        registerActivityLifecycleCallbacks(this);
        Intent intent = new Intent(this, WarningService.class);
        startService(intent);
        UpdateUtils.init("http://172.22.34.198:8809/mobile/DG3_Release/smt_pda/update/");
    }

    @Override
    public String getBaseUrl() {
        String ip = SpUtil.getStringSF(this, "ip");
        String port = SpUtil.getStringSF(this, "port");
        if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(port)) {

            API.BASE_URL = "http://" + ip + ":" + port + "/";
//        if(SpUtil.getStringSF(this,"server_address")==null||"".equals(SpUtil.getStringSF(this,"server_address"))){
//
//        }else{
//            API.BASE_URL = SpUtil.getStringSF(this,"server_address");
//        }

        }
        return API.BASE_URL;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        if (activity.getParent() != null) {
            mContenxt = activity.getParent();
        } else
            mContenxt = activity;
        ActivityMonitor.getInstance().onActivityEvent(activity, ActivityState.CREATED);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activity.getParent() != null) {
            mContenxt = activity.getParent();
        } else
            mContenxt = activity;
        ActivityMonitor.getInstance().onActivityEvent(activity, ActivityState.STARTED);
        appCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity.getParent() != null) {
            mContenxt = activity.getParent();
        } else
            mContenxt = activity;
        ActivityMonitor.getInstance().onActivityEvent(activity, ActivityState.RESUMED);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ActivityMonitor.getInstance().onActivityEvent(activity, ActivityState.PAUSED);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ActivityMonitor.getInstance().onActivityEvent(activity, ActivityState.STOPPED);
        appCount--;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityMonitor.getInstance().onActivityEvent(activity, ActivityState.DESTROYED);
    }
}
