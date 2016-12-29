package com.delta.smt.service;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;

import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.app.App;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.manager.ActivityMonitor;
import com.delta.smt.manager.WarningManger;

import java.util.ArrayList;
import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/26 9:16
 */


public class WarningService extends Service implements ActivityMonitor.OnAppStateChangeListener {

    private Context context;
    private List<String> warningString;
    private int currentType = 0;


    private boolean foreground = true;
    private Handler handler;
    private Activity topActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityMonitor.getInstance().registerAppStateChangeListener(this);
        ActivityMonitor.setStrictForeground(true);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final android.app.AlertDialog dialog = getAlertDialog();
        Log.e("dsfsf", "onStartCommand: ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //模拟预警
                    int randomInt = (int) (Math.random() * 5);
                    Intent intent = new Intent();
                    intent.setAction(Constant.WARNINGRECIEVE);
                    intent.putExtra(Constant.WARNINGTYPE, randomInt);
                    //1.首先判断栈顶是不是有我们的预警页面
                    //2.其次判断是否是在前台如果是前台就发送广播如果是后台就弹出dialog
                    topActivity = ActivityMonitor.getInstance().getTopActivity();
                    if (topActivity != null) {
                        if (topActivity.getClass().equals(WarningManger.getInstance().getWaringCalss(randomInt))) {
                            if (foreground) {
                                sendBroadcast(intent);
                            } else {
                                App.getMainHander().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.show();
                                    }
                                });
                            }
                        }
                    }
//                    if (foreground) {
//                        sendBroadcast(intent);
//                    } else {
//                        topActivity = ActivityMonitor.getInstance().getTopActivity();
//                        if (topActivity != null) {
//                            if (topActivity.getClass().equals(WarningManger.getInstance().getWaringCalss(randomInt))) {
//                                App.getMainHander().post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        dialog.show();
//                                    }
//                                });
//                            }
//                        }
//                    }
                    Log.e("---", "run: " + randomInt);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        ).

                start();


        return super.

                onStartCommand(intent, flags, startId);
    }

    @NonNull
    public AlertDialog getAlertDialog() {
        //1.创建这个DialogRelativelayout
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(this);
        //2.传入的是红色字体的标题
        dialogRelativelayout.setStrTitle("测试标题");
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("预警异常");
        //4.传入的是一个ArrayList<String>
        ArrayList<String> datas = new ArrayList<>();
        datas.add("dsfdsf");
        datas.add("sdfsdf1");
        datas.add("dsfsdf2");
        dialogRelativelayout.setStrContent(datas);

        //5.构建Dialog，setView的时候把这个View set进去。
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogCustom).setView(dialogRelativelayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(WarningService.this, topActivity.getClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder1.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        return dialog;
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


    @Override
    public void onAppStateChange(boolean foreground) {
        Activity topActivity = ActivityMonitor.getInstance().getTopActivity();
        Log.e("-----", "onAppStateChange: " + foreground + topActivity.getClass().getName());
        this.foreground = foreground;
        //  this.topActivity = ActivityMonitor.getInstance().getTopActivity();
    }
}
