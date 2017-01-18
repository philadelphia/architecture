package com.delta.smt.service.warningService;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;

import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.api.API;
import com.delta.smt.app.App;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.entity.TypeObject;
import com.delta.smt.manager.ActivityMonitor;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.service.warningService.di.DaggerWarningComponent;
import com.delta.smt.service.warningService.di.WebSocketClientModule;
import com.google.gson.Gson;

import org.java_websocket.drafts.Draft_17;

import java.util.ArrayList;

import javax.inject.Inject;

//import com.delta.smt.ui.store.di.DaggerWarningComponent;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/26 9:16
 */


public class WarningService extends Service implements WarningSocketClient.OnRecieveLisneter, WarningManger.OnRegister {

    private static final String TAG = "WarningService";
    @Inject
    WarningSocketClient warningSocketClient;
    @Inject
    WarningManger warningManger;
    @Inject
    ActivityMonitor activityMonitor;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
        WebSocketClientModule webSocketClientModule = WebSocketClientModule.builder().draft(new Draft_17()).uri(API.WebSocketURl).build();
        DaggerWarningComponent.builder().appComponent(App.getAppComponent()).webSocketClientModule(webSocketClientModule).build().inject(this);
        try {
            warningSocketClient.connectBlocking();
            warningSocketClient.addOnRecieveLisneter(this);
            warningManger.setOnRegister(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");
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

    @Override
    public void OnForeground(String text) {
        Log.e(TAG, "OnForeground: " + text);
        Intent intent = new Intent();
        intent.setAction(Constant.WARNINGRECIEVE);
        intent.putExtra(Constant.WARNINGMESSAGE, text);
        sendBroadcast(intent);
    }

    @Override
    public void OnBackground(final String text) {

        App.getMainHander().post(new Runnable() {
            @Override
            public void run() {
                getAlertDialog(text).show();
            }
        });
    }

    @Override
    public void register(int type) {
        String s = new Gson().toJson(new TypeObject(type));
        Log.e(TAG, "register: " + s);
        warningSocketClient.send(s);
    }

    @NonNull
    public AlertDialog getAlertDialog(String text) {
        //1.创建这个DialogRelativelayout
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(this);
        //2.传入的是红色字体的标题
        dialogRelativelayout.setStrTitle("预警信息");
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("预警异常");
        //4.传入的是一个ArrayList<String>
        ArrayList<String> datas = new ArrayList<>();
        datas.add(text);
        dialogRelativelayout.setStrContent(datas);
        //5.构建Dialog，setView的时候把这个View set进去。
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogCustom).setView(dialogRelativelayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(WarningService.this, activityMonitor.getTopActivity().getClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder1.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        return dialog;
    }


}
