package com.delta.smt.ui.warningSample;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.delta.commonlibs.utils.DeviceUuidFactory;
import com.delta.smt.R;
import com.delta.smt.app.App;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.SendMessage;
import com.delta.smt.entity.WaringDialogEntity;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.service.warningService.WarningActivity;
import com.delta.smt.ui.login.di.DaggerLoginComponent;
import com.delta.smt.ui.login.di.LoginModule;
import com.delta.smt.ui.login.mvp.LoginContract;
import com.delta.smt.ui.login.mvp.LoginPresenter;
import com.delta.smt.widget.DialogLayout;
import com.delta.smt.widget.WarningDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/27 19:39
 */


public class WarningSampleActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, WarningManger.OnWarning {
    @Inject
    WarningManger warningManger;
    private WarningDialog warningDialog;
    private DialogLayout dialogLayout;


    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerLoginComponent.builder().appComponent(appComponent).loginModule(new LoginModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        Log.e(TAG, "initData: " + DeviceUuidFactory.getUuid().toString());
        //接收那种预警
        warningManger.addWarning(String.valueOf(9), getClass());
        //需要定制的信息
        warningManger.sendMessage(new SendMessage("9"));
        //是否接收预警 可以控制预警时机
        warningManger.setReceive(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);
        if (warningDialog == null) {
            warningDialog = createDialog("dfsfd");
        }
        if (!warningDialog.isShowing()) {
            warningDialog.show();
        }
        updateMessage("fdsf");


    }

    @Override
    protected void initView() {

        TimerTask timerTask =new TimerTask() {
            @Override
            public void run() {

                App.getMainHander().post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent1 = new Intent(WarningSampleActivity.this, WarningActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        WarningSampleActivity.this.startActivity(intent1);
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask,1000,20000);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_diseasereport;
    }

    @Override
    protected void onResume() {
        warningManger.registerWReceiver(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        warningManger.unregisterWReceiver(this);
        super.onStop();
    }

    @Override
    public void loginSucess() {

    }

    @Override
    public void loginFailed() {

    }

    @Override
    public void warningComing(String message) {
        Log.e(TAG, "warningComing: " + message);

        if (warningDialog == null) {
            warningDialog = createDialog(message);
        }
        if (!warningDialog.isShowing()) {
            warningDialog.show();
        }
        updateMessage(message);

    }

    public WarningDialog createDialog(String message) {


        warningDialog = new WarningDialog(this);
        warningDialog.setOnClickListener(new WarningDialog.OnClickListener() {
            @Override
            public void onclick(View view) {
                warningManger.setConsume(true);
            }
        });
        warningDialog.show();

        return warningDialog;
    }

    /**
     * type == 9  代表你要发送的是哪个
     *
     * @param message
     */
    private void updateMessage(String message) {
        List<WaringDialogEntity> datas = warningDialog.getDatas();
        datas.clear();
        WaringDialogEntity warningEntity = new WaringDialogEntity();
        warningEntity.setTitle("预警Sample");
        String content = "";
        try {
            JSONArray jsonArray = new JSONArray(message);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
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
