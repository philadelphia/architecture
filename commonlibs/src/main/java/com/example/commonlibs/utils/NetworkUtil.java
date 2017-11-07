package com.example.commonlibs.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.net.InetAddress;

import retrofit2.adapter.rxjava.HttpException;

public class NetworkUtil {

    /**
     * Returns true if the UnifyThrowable is an instance of RetrofitError with an
     * http status code equals to the given one.
     */
    public static boolean isHttpStatusCode(Throwable throwable, int statusCode) {
        return throwable instanceof HttpException
                && ((HttpException) throwable).code() == statusCode;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == cm) {
            return false;
        }

        NetworkInfo info = cm.getActiveNetworkInfo();
        if (null != info) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity, int requestCode) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction(Intent.ACTION_VIEW);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void getNetIP(final String host, final OnGetHostAddress mOnGetHostAddress) {

        final Handler mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {

                super.handleMessage(msg);
                if (msg.what == 1) {
                    Bundle mData = msg.getData();
                    String mHost = mData.getString("host");
                    mOnGetHostAddress.getHostAddressSuccess(mHost);
                } else {
                    mOnGetHostAddress.getHostAddressField();
                }
            }
        };
        mOnGetHostAddress.startLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message mObtain = Message.obtain();
                Bundle mBundle = new Bundle();
                try {
                    InetAddress mByName = InetAddress.getByName(host);
                    mObtain.what = 1;
                    mBundle.putString("host", mByName.getHostAddress());

                } catch (Exception mE) {
                    mE.printStackTrace();
                    mObtain.what = -1;
                }
                mObtain.setData(mBundle);
                mHandler.sendMessage(mObtain);
            }
        }).start();


    }


}

