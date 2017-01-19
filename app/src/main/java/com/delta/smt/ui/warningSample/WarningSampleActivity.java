package com.delta.smt.ui.warningSample;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.delta.commonlibs.utils.GsonTools;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.WarningContent;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.login.di.DaggerLoginComponent;
import com.delta.smt.ui.login.di.LoginModule;
import com.delta.smt.ui.login.mvp.LoginContract;
import com.delta.smt.ui.login.mvp.LoginPresenter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/27 19:39
 */


public class WarningSampleActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, WarningManger.OnWarning {


    private AlertDialog alertDialog;

    @Inject
    WarningManger warningManger;
    private DialogRelativelayout dialogRelativelayout;
    //private List<WarningContent> SimpleWarningContents = new ArrayList<>();
    ArrayList<String> SimpleWarningdatas = new ArrayList<>();
    private SimpleDateFormat dateFormat;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerLoginComponent.builder().appComponent(appComponent).loginModule(new LoginModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        dateFormat = new SimpleDateFormat("hh:mm:ss");
        //接收那种预警，没有的话自己定义常量
        warningManger.addWarning(Constant.SAMPLEWARING, getClass());
        //是否接收预警 可以控制预警时机
        warningManger.setRecieve(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);
        // getPresenter().login("sdf", "sdf");
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
//创建一个Request
        final Request request = new Request.Builder()
                .url("http://172.22.34.24:8081/SMM/AlarmManager/alarm")
                .build();
//new call
        Call call = mOkHttpClient.newCall(request);
//请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.e(TAG, "onResponse: "+response.body().string());
            }
        });

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
        if (alertDialog != null) {
            SimpleWarningdatas.clear();
            ArrayList<WarningContent> warningContents = GsonTools.changeGsonToList(message, WarningContent.class);

            for (WarningContent warningContent : warningContents) {
                if (warningContent.getType() == Constant.SAMPLEWARING) {
                    String format = dateFormat.format(new Date(System.currentTimeMillis() - Long.valueOf(warningContent.getMessage().getDeadLine())));
                    SimpleWarningdatas.add(warningContent.getMessage().getProductline() + "--" + format + "\n");
                }

            }
            dialogRelativelayout.setDatas(SimpleWarningdatas);
            alertDialog.show();
        } else {
            alertDialog = createDialog(message);
        }
    }

    public AlertDialog createDialog(String message) {

        Log.e(TAG, "createDialog: " + message);
        dialogRelativelayout = new DialogRelativelayout(this);
        //2.传入的是红色字体的标题
        dialogRelativelayout.setStrTitle("预警信息");
        ArrayList<WarningContent> warningContents = GsonTools.changeGsonToList(message, WarningContent.class);
        for (WarningContent warningContent : warningContents) {
            if (warningContent.getType() == Constant.SAMPLEWARING) {
                String format = dateFormat.format(new Date(System.currentTimeMillis() - Long.valueOf(warningContent.getMessage().getDeadLine())));
                SimpleWarningdatas.add(warningContent.getMessage().getProductline() + "--" + format);
            }
        }
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("simple预警");
        //4.传入的是一个ArrayList<String>
        dialogRelativelayout.setStrContent(SimpleWarningdatas);
        //5.构建Dialog，setView的时候把这个View set进去。
        return new AlertDialog.Builder(this).setView(dialogRelativelayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                warningManger.setConsume(true);
            }
        }).show();
    }
}
