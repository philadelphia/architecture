package com.delta.smt.service.warningService;

import android.app.IntentService;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
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

        //warningSocketClient.addOnRecieveListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String ip = SpUtil.getStringSF(this, "ip");
        String port = SpUtil.getStringSF(this, "port");
        if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(port)) {
            API.WebSocketURl = "ws://" + ip + ":" + API.SOCKET_PORT;
        }
        WarningSocketPresenterModule warningSocketPresenterModule = WarningSocketPresenterModule.builder().context(this).url(API.WebSocketURl).build();
        DaggerWarningComponent.builder().appComponent(App.getAppComponent()).warningSocketPresenterModule(warningSocketPresenterModule).build().inject(this);
        warningSocketPresenter.addOnRecieveListener(this);
        km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        warningSocketPresenter.startConntect();
        Log.e(TAG, "onStartCommand: " + API.WebSocketURl);
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
    public void OnBackground(final String message) {

        App.getMainHander().post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void run() {
                if (warningDialog == null) {
                    warningDialog = createDialog();
                }
                if (!warningDialog.isShowing()) {
                    warningDialog.show();
                }
                updateMessage(message);

            }
        });
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

}
