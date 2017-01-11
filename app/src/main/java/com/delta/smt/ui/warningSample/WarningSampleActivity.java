package com.delta.smt.ui.warningSample;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.login.di.DaggerLoginComponent;
import com.delta.smt.ui.login.di.LoginModule;
import com.delta.smt.ui.login.mvp.LoginContract;
import com.delta.smt.ui.login.mvp.LoginPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/27 19:39
 */


public class WarningSampleActivity extends BaseActiviy<LoginPresenter> implements LoginContract.View, WarningManger.OnWarning {


    private AlertDialog alertDialog;

    @Inject
    WarningManger warningManger;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerLoginComponent.builder().appComponent(appComponent).loginModule(new LoginModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {

        //接收那种预警，没有的话自己定义常量
        warningManger.addWarning(Constant.SAMPLEWARING, getClass());
        //是否接收预警 可以控制预警时机
        warningManger.setRecieve(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);

        getPresenter().login("sdf", "sdf");
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
        if (alertDialog != null) {
            alertDialog.show();
        } else {
            alertDialog = createDialog(message);
        }
    }

    public AlertDialog createDialog(String message) {
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
        dialogRelativelayout.setStrSecondTitle("预警异常2");
        ArrayList<String> datass = new ArrayList<>();
        datass.add("dsfdsf");
        datass.add("sdfsdf1");
        datass.add("dsfsdf2");
        dialogRelativelayout.setStrContent(datass);
        //5.构建Dialog，setView的时候把这个View set进去。
        return new AlertDialog.Builder(this).setView(dialogRelativelayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}
