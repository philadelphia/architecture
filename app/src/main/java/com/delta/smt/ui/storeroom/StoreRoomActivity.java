package com.delta.smt.ui.storeroom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.FrameLocation;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.storeroom.di.DaggerStoreRoomComponent;
import com.delta.smt.ui.storeroom.di.StoreRoomModule;
import com.delta.smt.ui.storeroom.mvp.StoreRoomContract;
import com.delta.smt.ui.storeroom.mvp.StoreRoomPresenter;
import com.delta.smt.utils.BarCodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
    @BindView(R.id.storage_show)
    TextView storageShow;
    @BindView(R.id.storage_submit)
    Button storageSubmit;
    @BindView(R.id.storage_clear)
    Button storageClear;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;

    AlertDialog.Builder builder ;
    private boolean isButtonOnclick = false;
    private StringBuffer stringBuffer = new StringBuffer();

    private List<MaterialBlockBarCode> materialBlockBarCodes = new ArrayList<>();


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerStoreRoomComponent.builder().appComponent(appComponent).storeRoomModule(new StoreRoomModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        storagePcbed.setFocusable(true);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText(getResources().getString(R.string.pcbku));
        builder= new AlertDialog.Builder(this);


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
                    if (materialBlockBarCodes.size() < 3) {
                        materialBlockBarCodes.add(barCode);
                    }
                    setTextView();
                    break;
                case FRAME_LOCATION:
                    FrameLocation frameCode = (FrameLocation) barCodeParseIpml.getEntity(barcode, FRAME_LOCATION);
                    storageIded.setText(frameCode.getSource());
                    if (materialBlockBarCodes.size() == 3) {
                        if (!TextUtils.isEmpty(storageIded.getText()))
                            getPresenter().fatchPutInStorage(materialBlockBarCodes, storageIded.getText().toString());
                    }

                    break;
            }

        } catch (EntityNotFountException e) {

            e.printStackTrace();
        }


    }

    private void setTextView() {

        stringBuffer.append("\n" + storagePcbed.getText() + storageVendored.getText() + storageDatacodeed.getText());
        storageShow.setText(stringBuffer);


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
        ToastUtils.showMessage(this,"点灯操作成功");
        materialBlockBarCodes.clear();

    }

    @Override
    public void lightfaild() {
        ToastUtils.showMessage(this,"点灯操作失败");
    }

    @Override
    public void storageSuccsee() {

    }

    @Override
    public void storagefaild() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.storage_clear, R.id.storage_submit})
    public void onHeaderClick(View v) {

        switch (v.getId()) {
            case R.id.storage_clear:
                if (isButtonOnclick) {
                storagePcbed.setText(null);
                storageVendored.setText(null);
                storageDatacodeed.setText(null);
                materialBlockBarCodes.clear();
                storageShow.setText("");
                }
                break;
            case R.id.storage_submit:
                final Dialog dialo = builder.create();
                dialo.show();
                View view = View.inflate(this, R.layout.dialog_storehint, null);
                dialo.setContentView(view);
                Button confirmButton = (Button) view.findViewById(R.id.storehint_confirm);
                Button cancelButton = (Button) view.findViewById(R.id.storehint_cancel);
                 confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        storageClear.setBackgroundColor(Color.GRAY);
                        storageClear.setEnabled(false);


                        if (dialo.isShowing()) {
                            dialo.cancel();
                        }
                        isButtonOnclick=true;
                        if(materialBlockBarCodes.size()!=0){
                        getPresenter().fatchOnLight(materialBlockBarCodes);}else {
                            ToastUtils.showMessage(StoreRoomActivity.this,"请扫码后在点击确认");
                        }
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialo.isShowing()) {
                            dialo.cancel();
                        }
                    }
                });


                break;
        }
    }



}
