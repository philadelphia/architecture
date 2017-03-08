package com.delta.smt.service.warningService;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.view.WindowManager;

import com.delta.smt.R;
import com.delta.smt.base.BaseCommonActivity;

public class WarningDialogActivity extends BaseCommonActivity {


    @Override
    protected void initWindow() {
        super.initWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    @Override
    protected void initCView() {

    }

    @Override
    protected void initCData() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_warning_dialog;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        if (!pm.isScreenOn()) {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            wl.acquire();
            wl.release();
        }
    }
}
