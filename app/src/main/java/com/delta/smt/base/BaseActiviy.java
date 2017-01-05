package com.delta.smt.base;

import android.view.KeyEvent;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by V.Wenju.Tian on 2016/11/29.
 */

public abstract class BaseActiviy<p extends BasePresenter> extends BaseCommonActivity implements BarCodeIpml.OnScanSuccessListener {

    public final String TAG = getClass().getSimpleName();
    @Inject
    protected p mPresenter;


    private BarCodeIpml barCodeIpml;


    private List<OnBarCodeSucess> events = new ArrayList<>();

    public void addOnBarCodeSucess(OnBarCodeSucess onBarCodeSucess) {
        if (onBarCodeSucess != null) {

            events.add(onBarCodeSucess);
        }
    }

    public void removeOnBarCodeSuecss(OnBarCodeSucess onBarCodeSucess) {
        if (onBarCodeSucess != null) {

            events.remove(onBarCodeSucess);
        }
    }

    @Override
    protected void initCView() {
        initView();
    }

    @Override
    protected void initCData() {
        barCodeIpml = new BarCodeIpml();
        barCodeIpml.setOnGunKeyPressListener(this);
        componentInject(getMApplication().getAppComponent());//依赖注入
        initData();
    }

    protected abstract void componentInject(AppComponent appComponent);

    protected abstract void initData();


    protected abstract void initView();

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (barCodeIpml.isEventFromBarCode(event)) {
            barCodeIpml.analysisKeyEvent(event);
            return true;
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            //具体的操作代码
            ToastUtils.showMessage(this,"返回按钮");
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            barCodeIpml.hasConnectBarcode();
        } catch (DevicePairedNotFoundException e) {
            e.printStackTrace();
        }
    }

    public p getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.ondestory();
        }
        if (barCodeIpml != null) {

            barCodeIpml.onComplete();
        }
        super.onDestroy();
    }


    @Override
    public void onScanSuccess(String barcode) {

        if (events.size() != 0) {
            for (OnBarCodeSucess event : events) {
                event.onScanSucess(barcode);
            }
        }
    }

    public interface OnBarCodeSucess {
        void onScanSucess(String barcode);
    }
}
