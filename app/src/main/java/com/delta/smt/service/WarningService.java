package com.delta.smt.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/26 9:16
 */


public class WarningService extends Service {

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        List<String> warningString = new ArrayList<>();
        warningString.add("PCB仓库预警");
        warningString.add("故障处理预警");
        warningString.add("生成中预警");
        warningString.add("手部件预警");
        warningString.add("下模组提醒");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {



                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
