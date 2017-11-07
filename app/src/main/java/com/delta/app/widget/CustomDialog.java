package com.delta.app.widget;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/27 18:59
 */


public class CustomDialog extends Dialog {
    private CloseSystemDialogsReceiver mCloseSystemDialogsReceiver;
    private Window mWindow;

    public CustomDialog(Context context) {
        super(context);
        //setContentView(R.layout.view_custom);
        mWindow = this.getWindow();
        ViewGroup.LayoutParams attributes = mWindow.getAttributes();
        attributes.width = mWindow.getWindowManager().getDefaultDisplay()
                .getWidth();
        attributes.height = ViewGroup.LayoutParams.MATCH_PARENT;
        mWindow.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        IntentFilter filter = new IntentFilter(
                Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        mCloseSystemDialogsReceiver = new CloseSystemDialogsReceiver();
        mWindow.getContext().registerReceiver(mCloseSystemDialogsReceiver,
                filter);
    }

    private class CloseSystemDialogsReceiver extends BroadcastReceiver {
        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                    CustomDialog.this.dismiss();
                    mWindow.getContext().unregisterReceiver(mCloseSystemDialogsReceiver);
                }
            }

        }
    }
}
