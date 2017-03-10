package com.delta.smt.ui.warningSample;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.WaringDialogEntity;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.login.di.DaggerLoginComponent;
import com.delta.smt.ui.login.di.LoginModule;
import com.delta.smt.ui.login.mvp.LoginContract;
import com.delta.smt.ui.login.mvp.LoginPresenter;
import com.delta.smt.widget.DialogLayout;
import com.delta.smt.widget.WarningDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/27 19:39
 */


public class WarningSampleActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, WarningManger.OnWarning {


    private AlertDialog alertDialog;

    @Inject
    WarningManger warningManger;
    private DialogLayout dialogLayout;
    //private List<WarningContent> SimpleWarningContents = new ArrayList<>();
    ArrayList<String> SimpleWarningdatas = new ArrayList<>();
    private SimpleDateFormat dateFormat;
    private WarningDialog warningDialog;
    private WarningDialog dialog;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerLoginComponent.builder().appComponent(appComponent).loginModule(new LoginModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        dateFormat = new SimpleDateFormat("hh:mm:ss");
        //接收那种预警，没有的话自己定义常量
        warningManger.addWarning(9, getClass());
        //是否接收预警 可以控制预警时机
        warningManger.setRecieve(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);


    }

    @Override
    protected void initView() {

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
        warningManger.unregisterWReceriver(this);
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

        if (dialog == null) {
            dialog = createDialog(message);
        } else {
            updateMessage(message);
        }

//        if (alertDialog != null) {
//            SimpleWarningdatas.clear();
//            ArrayList<WarningContent> warningContents = GsonTools.changeGsonToList(message, WarningContent.class);
//
//            for (WarningContent warningContent : warningContents) {
//                if (warningContent.getType() == Constant.SAMPLEWARING) {
//                    String format = dateFormat.format(new Date(System.currentTimeMillis() - Long.valueOf(warningContent.getMessage().getDeadLine())));
//                    SimpleWarningdatas.add(warningContent.getMessage().getProductline() + "--" + format + "\n");
//                }
//
//            }
//            dialogLayout.setDatas(SimpleWarningdatas);
//            alertDialog.show();
//        } else {
//            alertDialog = createDialog(message);
//        }
    }

    public WarningDialog createDialog(String message) {
        WarningDialog warningDialog = new WarningDialog(this);
        warningDialog.setOnClickListener(new WarningDialog.OnClickListener() {
            @Override
            public void onclick(View view) {
                warningManger.setConsume(true);
            }
        });
        updateMessage(message);
        return warningDialog;
    }

    private void updateMessage(String message) {
        List<WaringDialogEntity> datas = warningDialog.getDatas();
        datas.clear();
        WaringDialogEntity warningEntity = new WaringDialogEntity();
        warningEntity.setTitle("预警Sample");
        try {
            JSONArray jsonArray = new JSONArray(message);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                int type = jsonObject.getInt("type");
                //可能有多种预警的情况
                if (type == 9) {
                    Object message1 = jsonObject.get("message");
                    warningEntity.setContent(message1 + "\n");
                }
            }
            datas.add(warningEntity);
            warningDialog.getAdapter().notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
