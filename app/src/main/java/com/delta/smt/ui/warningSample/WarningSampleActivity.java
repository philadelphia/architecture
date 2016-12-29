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

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/27 19:39
 */


public class WarningSampleActivity extends BaseActiviy<LoginPresenter> implements LoginContract.View, WarningManger.OnWarning {


    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerLoginComponent.builder().appComponent(appComponent).loginModule(new LoginModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {

        WarningManger.getInstance().addWarning(Constant.SAMPLEWARING, getClass());
        WarningManger.getInstance().setRecieve(true);
        WarningManger.getInstance().setOnWarning(this);
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
        WarningManger.getInstance().registWReceiver(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        WarningManger.getInstance().unregistWReceriver(this);
        super.onStop();
    }

    @Override
    public void loginSucess() {

    }

    @Override
    public void loginFailed() {

    }

    @Override
    public void warningComming() {
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
        new AlertDialog.Builder(this).setView(dialogRelativelayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}
