package com.delta.smt.ui.storeroom;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.Feeder;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.storeroom.mvp.StoreRoomPresenter;

import butterknife.BindView;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreRoomActivity extends BaseActiviy<StoreRoomPresenter>implements BarCodeIpml.OnScanSuccessListener {
    @BindView(R.id.storage_pcbed)
    EditText storagePcbed;
    @BindView(R.id.storage_vendored)
    EditText storageVendored;
    @BindView(R.id.storage_datacodeed)
    EditText storageDatacodeed;
    @BindView(R.id.storage_labeled)
    EditText storageLabeled;
    @BindView(R.id.storage_ided)
    EditText storageIded;


    private BarCodeIpml barCodeIpml=new BarCodeIpml();

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {
        storagePcbed.setFocusable(true);
    }

    @Override
    protected void initView() {
        barCodeIpml.setOnGunKeyPressListener(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_warning_details;
    }


    @Override
    public void onScanSuccess(String barcode) {
       BarCodeParseIpml barCodeParseIpml=new BarCodeParseIpml();
        try {
            //TODO  这里负责解析二维码，下面的代码不一定能用
            Feeder materialBlockBarCode= (Feeder) barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER);
            Log.e("barcode",materialBlockBarCode.getNumber());
        } catch (EntityNotFountException e) {

            e.printStackTrace();
        }


    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        barCodeIpml.onComplete();
    }

}
