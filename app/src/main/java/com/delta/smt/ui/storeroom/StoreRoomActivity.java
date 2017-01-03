package com.delta.smt.ui.storeroom;

import android.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.FrameLocation;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.storeroom.mvp.StoreRoomContract;
import com.delta.smt.ui.storeroom.mvp.StoreRoomPresenter;
import com.delta.smt.utils.BarCodeUtils;

import butterknife.BindView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreRoomActivity extends BaseActiviy<StoreRoomPresenter> implements StoreRoomContract.View{

    @BindView(R.id.storage_pcbed)
    EditText storagePcbed;
    @BindView(R.id.storage_vendored)
    EditText storageVendored;
    @BindView(R.id.storage_datacodeed)
    EditText storageDatacodeed;
    @BindView(R.id.storage_ided)
    EditText storageIded;
    @BindView(R.id.header_title)
    TextView headerTitle;


    private BarCodeIpml barCodeIpml = new BarCodeIpml();


    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {
        storagePcbed.setFocusable(true);
    }

    @Override
    protected void initView() {
        headerTitle.setText(getResources().getString(R.string.pcbku));
        barCodeIpml.setOnGunKeyPressListener(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_warning_details;
    }


    @Override
    public void onScanSuccess(String barcode) {
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        try {
            switch (BarCodeUtils.barCodeType(barcode)){
                case MATERIAL_BLOCK_BARCODE:
                MaterialBlockBarCode barCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                Log.e("barcode", barCode.getDeltaMaterialNumber());
                storagePcbed.setText(barCode.getDeltaMaterialNumber());
                storageVendored.setText(barCode.getBusinessCode());
                storageDatacodeed.setText(barCode.getDC());
                    break;
                case FRAME_LOCATION:
                    FrameLocation frameCode = (FrameLocation) barCodeParseIpml.getEntity(barcode, BarCodeType.FRAME_LOCATION);
                    storageIded.setText(frameCode.getSource());
                    break;
            }

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


    @Override
    public void storeSuccess(String s) {
        storagePcbed.setText(null);
        storageVendored.setText(null);
        storageDatacodeed.setText(null);
        storageIded.setText(null);
    }

    @Override
    public void storeFaild(String s) {

    }


}
