package com.delta.app.base;

import android.util.Log;
import android.view.KeyEvent;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.Barcode;
import com.delta.demacia.barcode.BarcodeFactory;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.app.app.App;
import com.delta.app.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by V.Wenju.Tian on 2016/11/29.
 */

public abstract class BaseActivity<p extends BasePresenter> extends BaseCommonActivity implements BarCodeIpml.OnScanSuccessListener {

    public final String TAG = getClass().getSimpleName();
    @Inject
    protected p mPresenter;

    private Barcode barCodeIpml;

    private List<OnBarCodeSuccess> events = new ArrayList<>();

    public void addOnBarCodeSuccess(OnBarCodeSuccess onBarCodeSucess) {
        if (onBarCodeSucess != null) {

            events.add(onBarCodeSucess);

        }
    }

    public void removeOnBarCodeSuccess(OnBarCodeSuccess onBarCodeSuccess) {
        if (onBarCodeSuccess != null) {

            events.remove(onBarCodeSuccess);
        }
    }

    @Override
    protected void initCView() {
        initView();
    }

    @Override
    protected void initCData() {
        barCodeIpml = BarcodeFactory.getBarcode(this);
        barCodeIpml.setOnGunKeyPressListener(this);
        componentInject(App.getAppComponent());//依赖注入
        initData();
    }

    protected abstract void componentInject(AppComponent appComponent);

    protected abstract void initData();

    protected abstract void initView();

    public Barcode getBarCodeIpml() {
        return barCodeIpml;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
            return  super.dispatchKeyEvent(event);
        }

        if (barCodeIpml.isEventFromBarCode(event)) {
            barCodeIpml.analysisKeyEvent(event);
            return true;
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
    protected void handError(String contents) {

    }

    @Override
    public void onScanSuccess(String barcode) {

        Log.e(TAG, "onScanSuccess: "+barcode);
        if (events.size() != 0) {
            Log.e(TAG, "onScanSuccess: "+events.size());
            for (OnBarCodeSuccess event : events) {
                event.onScanSuccess(barcode);
            }
        }
    }

    public interface OnBarCodeSuccess {
        void onScanSuccess(String barcode);
    }

}
