package com.delta.smt.ui.storeroom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.FrameLocation;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.IntentUtils;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.setting.SettingActivity;
import com.delta.smt.ui.storeroom.mvp.StoreRoomContract;
import com.delta.smt.ui.storeroom.mvp.StoreRoomPresenter;
import com.delta.smt.utils.BarCodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.delta.buletoothio.barcode.parse.BarCodeType.FRAME_LOCATION;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreRoomActivity extends BaseActivity<StoreRoomPresenter> implements StoreRoomContract.View {

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
    @BindView(R.id.header_back)
    RelativeLayout headerBack;
    @BindView(R.id.header_setting)
    TextView headerSetting;
    @BindView(R.id.storage_show)
    TextView storageShow;
    @BindView(R.id.storage_submit)
    Button storageSubmit;
    @BindView(R.id.storage_clear)
    Button storageClear;


    private boolean isButtonOnclick=false;
    private StringBuffer stringBuffer=new StringBuffer();

    private List<MaterialBlockBarCode> materialBlockBarCodes=new ArrayList<>();


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

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_warning_details;
    }


    @Override
    public void onScanSuccess(String barcode) {
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        try {
            switch (BarCodeUtils.barCodeType(barcode)) {
                case MATERIAL_BLOCK_BARCODE:
                    MaterialBlockBarCode barCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                    Log.e("barcode", barCode.getDeltaMaterialNumber());
                    storagePcbed.setText(barCode.getDeltaMaterialNumber());
                    storageVendored.setText(barCode.getBusinessCode());
                    storageDatacodeed.setText(barCode.getDC());
                    if (materialBlockBarCodes.size()<3){
                    materialBlockBarCodes.add(barCode);
                    }else if (materialBlockBarCodes.size()==3){
                        getPresenter().fatchOnLight(materialBlockBarCodes);
                    }

                    setTextView();
                    break;
                case FRAME_LOCATION:
                    FrameLocation frameCode = (FrameLocation) barCodeParseIpml.getEntity(barcode, FRAME_LOCATION);
                    storageIded.setText(frameCode.getSource());
                    break;
            }

        } catch (EntityNotFountException e) {

            e.printStackTrace();
        }


    }

    private void setTextView() {
        stringBuffer.append("\n"+storagePcbed.getText()+storageVendored.getText()+storageDatacodeed.getText());
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

    @Override
    public void lightSuccsee() {
        materialBlockBarCodes.clear();
        if(materialBlockBarCodes.size()==3){
            if (!TextUtils.isEmpty(storageIded.getText()))
        getPresenter().fatchPutInStorage(materialBlockBarCodes,storageIded.getText().toString());}
    }

    @Override
    public void lightfaild() {

    }

    @Override
    public void storageSuccsee() {

    }

    @Override
    public void storagefaild() {

    }

    @OnClick({R.id.header_back, R.id.header_setting,R.id.storage_clear,R.id.storage_submit})
    public void onHeaderClick(View v) {

        switch (v.getId()) {
            case R.id.header_back:
                IntentUtils.showIntent(this, MainActivity.class);
                break;
            case R.id.header_setting:
                IntentUtils.showIntent(this, SettingActivity.class);
                break;
            case R.id.storage_clear:
                if(isButtonOnclick){
                    storageClear.setBackgroundColor(Color.GRAY);
                    storageClear.setEnabled(false);
                }
                break;
            case R.id.storage_submit:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                final Dialog dialo=builder.create();
                dialo.setContentView(R.layout.dialog_storehint);
                Button confirmButton= (Button) dialo.findViewById(R.id.storehint_confirm);
                Button cancelButton= (Button) dialo.findViewById(R.id.storehint_cancel);
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialo.isShowing()){
                            dialo.cancel();
                        }
                    }
                });
                dialo.show();
                break;
        }
    }


  
}
