package com.delta.smt.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/26 9:55
 */


public class WarningBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

    }


//    public boolean isAppOnForeground(Context context) {
//        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> tasksInfo = am.getRunningTasks(1);
//        if (tasksInfo.size() > 0) {
//
//            if (mPackageName.equals(tasksInfo.get(0).topActivity
//                    .getPackageName())) {
//                return true;
//            }
//        }
//        return false;
//    }
}
