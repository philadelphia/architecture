package com.delta.smt.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.Constant;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.warningSample.WarningSampleActivity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/27 20:55
 */


public abstract class WarningBaseActivity<p extends BasePresenter> extends BaseActiviy<p> {


    private WarningBroadcastReciever mBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        WarningManger.getInstance().addWarning(Constant.SAMPLEWARING, WarningSampleActivity.class);
        WarningManger.getInstance().setRecieve(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        registWReceiver();
        super.onResume();
    }

    @Override
    protected void onStop() {
        unregistWReceriver();
        super.onStop();
    }

    /**
     * 注册广播
     */
    public void registWReceiver() {
        try {
            if (mBroadcastReceiver == null) {
                mBroadcastReceiver = new WarningBroadcastReciever();
            }
            IntentFilter filter = new IntentFilter(Constant.WARNINGRECIEVE);
            registerReceiver(mBroadcastReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解除注册广播
     */
    public void unregistWReceriver() {
        if (mBroadcastReceiver == null) return;
        try {
            unregisterReceiver(mBroadcastReceiver);
            mBroadcastReceiver = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class WarningBroadcastReciever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


            int intExtra = intent.getIntExtra(Constant.WARNINGTYPE, -1);
            Class waringCalss = WarningManger.getInstance().getWaringCalss(intExtra);
            if (waringCalss != null) {
                if (TAG.equals(waringCalss.getSimpleName())) {
                    Log.e(TAG, "onReceive: " + "-----------------------------"+waringCalss);
                    if (WarningManger.getInstance().isRecieve()) {

                        warningComming();
                    }
                }
            }

            Log.e(TAG, "onReceive: " + intExtra );

        }
    }

   // protected abstract boolean warningTime();

    protected abstract void warningComming();

}
