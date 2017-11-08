package com.example.app.app;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.example.app.api.API;
import com.example.commonlibs.utils.DeviceUuidFactory;
import com.example.commonlibs.utils.SpUtil;
import com.example.app.BuildConfig;
import com.example.app.R;
import com.example.app.base.BaseApplication;
import com.example.app.di.component.AppComponent;
import com.example.app.di.component.DaggerAppComponent;
import com.example.app.manager.ActivityMonitor;
import com.example.app.manager.ActivityState;
import com.example.app.warningService.WarningService;
import com.example.ttsmanager.TextToSpeechManager;
import com.example.updatelibs.UpdateUtils;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

import javax.inject.Inject;

/**
 */
@ReportsCrashes(
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text,
        formUri = "http://172.22.34.198:8888/acra-smartsmt",
        httpMethod = HttpSender.Method.PUT,
        reportType = HttpSender.Type.JSON
)
public class App extends BaseApplication implements Application.ActivityLifecycleCallbacks {

    public static AppComponent appComponent;
    private static int appCount = 0;
    private static Handler mainHandler;
    private static Context mContext;
    @Inject
    TextToSpeechManager textToSpeechManager;

    public static Context getmContext() {
        return mContext;
    }

    public static Handler getMainHandler() {
        return mainHandler;
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
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (!BuildConfig.DEBUG) {
            ACRA.init(this);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new DeviceUuidFactory(this);
        mContext = this;
        mainHandler = new Handler(Looper.getMainLooper());
        appComponent = DaggerAppComponent.builder().clientModule(getClientModule()).appModule(getAppModule()).serviceModule(getServiceModule()).build();
        appComponent.inject(this);
        textToSpeechManager.setRead(SpUtil.getBooleanSF(this, "speech_switch"));
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
            mContext = activity.getParent();
        } else
            mContext = activity;
        ActivityMonitor.getInstance().onActivityEvent(activity, ActivityState.CREATED);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activity.getParent() != null) {
            mContext = activity.getParent();
        } else
            mContext = activity;
        ActivityMonitor.getInstance().onActivityEvent(activity, ActivityState.STARTED);
        appCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity.getParent() != null) {
            mContext = activity.getParent();
        } else
            mContext = activity;
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
