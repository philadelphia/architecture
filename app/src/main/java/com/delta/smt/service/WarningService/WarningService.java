package com.delta.smt.service.warningService;

import android.app.AlertDialog;
import android.app.IntentService;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.WindowManager;

import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.api.API;
import com.delta.smt.app.App;
import com.delta.smt.service.warningService.di.DaggerWarningComponent;
import com.delta.smt.service.warningService.di.WarningSocketPresenterModule;
import com.delta.smt.widget.DialogLayout;

import java.util.ArrayList;

import javax.inject.Inject;

//import com.delta.smt.ui.store.di.DaggerWarningComponent;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/26 9:16
 */


@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class WarningService extends IntentService implements WarningSocketPresenter.OnReceiveListener {

    private static final String TAG = "WarningService";
    //    @Inject
//    WarningSocketClient warningSocketClient;
//    @Inject
//    ActivityMonitor activityMonitor;
    @Inject
    WarningSocketPresenter warningSocketPresenter;
    private KeyguardManager km;

    public WarningService() {
        this("WarningService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WarningService(String name) {
        super("WarningService");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
//        WebSocketClientModule webSocketClientModule = WebSocketClientModule.builder().draft(new Draft_17()).uri(API.WebSocketURl).build();
//        DaggerWarningComponent.builder().appComponent(App.getAppComponent()).webSocketClientModule(webSocketClientModule).build().inject(this);
        WarningSocketPresenterModule warningSocketPresenterModule = WarningSocketPresenterModule.builder().context(this).url(API.WebSocketURl).build();
        DaggerWarningComponent.builder().appComponent(App.getAppComponent()).warningSocketPresenterModule(warningSocketPresenterModule).build().inject(this);
        warningSocketPresenter.addOnRecieveLisneter(this);
        km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        //warningSocketClient.addOnRecieveLisneter(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

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
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void run() {
                if (km.inKeyguardRestrictedInputMode()) {
                    Intent alarmIntent = new Intent(WarningService.this, WarningDialogActivity.class);
                    alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(alarmIntent);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @NonNull
    public AlertDialog getAlertDialog(String text) {
        // 1.创建这个DialogRelativelayout
        DialogLayout dialogLayout = new DialogLayout(this);
        //2.传入的是红色字体的标题
        dialogLayout.setStrTitle("预警信息");
        //3.传入的是黑色字体的二级标题
        dialogLayout.setStrSecondTitle("预警异常");
        //4.传入的是一个ArrayList<String>
        ArrayList<String> datas = new ArrayList<>();
        datas.add(text);
        dialogLayout.setStrContent(datas);
        //5.构建Dialog，setView的时候把这个View set进去。
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogCustom).setView(dialogLayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(WarningService.this, warningSocketPresenter.getTopActivity().getClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder1.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        return dialog;
    }
}
