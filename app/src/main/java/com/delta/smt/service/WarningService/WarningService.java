package com.delta.smt.service.warningService;

import android.app.IntentService;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.delta.commonlibs.utils.SpUtil;
import com.delta.smt.Constant;
import com.delta.smt.api.API;
import com.delta.smt.app.App;
import com.delta.smt.entity.WaringDialogEntity;
import com.delta.smt.service.warningService.di.DaggerWarningComponent;
import com.delta.smt.service.warningService.di.WarningSocketPresenterModule;
import com.delta.smt.widget.WarningDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
    private WarningDialog warningDialog;
    private ScreenStateReceiver receiver;


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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //startScreenBroadcastReceiver();
        String ip = SpUtil.getStringSF(this, "ip");
        String port = SpUtil.getStringSF(this, "port");
        if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(port)) {
            API.WebSocketURl = "ws://" + ip + ":" + API.SOCKET_PORT;
        }
        WarningSocketPresenterModule warningSocketPresenterModule = WarningSocketPresenterModule.builder().context(this).url(API.WebSocketURl).build();
        DaggerWarningComponent.builder().appComponent(App.getAppComponent()).warningSocketPresenterModule(warningSocketPresenterModule).build().inject(this);
        warningSocketPresenter.addOnReceiveListener(this);
        km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        warningSocketPresenter.startConnect();
        Log.e(TAG, "onStartCommand: " + API.WebSocketURl);
        return super.onStartCommand(intent, flags, startId);
    }

    private void startScreenBroadcastReceiver() {
        IntentFilter intentenfilter = new IntentFilter();
        intentenfilter.addAction(Intent.ACTION_SCREEN_ON);
        intentenfilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentenfilter.addAction(Intent.ACTION_USER_PRESENT);
        receiver = new ScreenStateReceiver();
        registerReceiver(receiver, intentenfilter);
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
//        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void OnForeground(String text) {
        Log.e(TAG, "OnForeground: " + text);
        Intent intent = new Intent();
        intent.setAction(Constant.WARNING_RECEIVE);
        intent.putExtra(Constant.WARNING_MESSAGE, text);
        sendBroadcast(intent);

    }

    /**
     * background 后台
     *
     * @param message
     */
    @Override
    public void OnBackground(final String message) {

        Intent intent = new Intent(WarningService.this, WarningActivity.class);
        intent.putExtra(Constant.WARNING_MESSAGE, message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

//        App.getMainHander().post(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
//            @Override
//            public void run() {
//                if (warningDialog == null) {
//                    warningDialog = createDialog();
//                }
//                if (!warningDialog.isShowing()) {
//                    warningDialog.show();
//                }
//                updateMessage(message);
//
//            }
//        });
    }


    public WarningDialog createDialog() {
        warningDialog = new WarningDialog(this);
        warningDialog.setOnClickListener(new WarningDialog.OnClickListener() {
            @Override
            public void onclick(View view) {
                warningSocketPresenter.setConsume(true);
                warningDialog.dismiss();
                Intent intent = new Intent(WarningService.this, warningSocketPresenter.getTopActivity().getClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        warningDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        warningDialog.show();
        return warningDialog;
    }

    private void updateMessage(String message) {
        List<WaringDialogEntity> datas = warningDialog.getDatas();
        datas.clear();
        WaringDialogEntity warningEntity = new WaringDialogEntity();
        warningEntity.setTitle("");
        String content = "";
        try {
            JSONArray jsonArray = new JSONArray(message);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Object message1 = jsonObject.get("message");
                content = content + message1 + "\n";
            }
            warningEntity.setContent(content + "\n");
            datas.add(warningEntity);
            warningDialog.notifyData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ScreenStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("ndh--", "action=" + action);
            if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏

                Log.d("ndh--", "screen on");
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏

                Log.d("ndh--", "screen off");
//                Intent intent1 = new Intent(context, MainActivity.class);
//                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent1);
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
                Log.d("ndh--", "user_present");
            }
        }
    }
}
