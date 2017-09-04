package com.delta.smt.ui.production_scan.binding;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.Feeder;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.entity.MaterialStation;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.production_scan.binding.di.BindingModule;
import com.delta.smt.ui.production_scan.binding.di.DaggerBindingComponent;
import com.delta.smt.ui.production_scan.binding.mvp.BindingContract;
import com.delta.smt.ui.production_scan.binding.mvp.BindingPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public class BindingActivity extends BaseActivity<BindingPresenter> implements BindingContract.View/*, View.OnClickListener*/ {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.tv_work_order)
    TextView tvWorkOrder;
    @BindView(R.id.tv_line_name)
    TextView tvLineName;
    @BindView(R.id.tv_line_num)
    TextView tvLineNum;
    @BindView(R.id.tv_material_no)
    TextView etMaterialCode;
    @BindView(R.id.tv_material_station)
    TextView etMaterialStation;
    @BindView(R.id.tv_feeder)
    TextView etFeeder;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.update_progress)
    LinearLayout updateProgress;

    String workOrderStr;
    String sideStr;
    String lineStr;
    String serialNo;

    BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
    String materialBlockBarcodeStr = null;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerBindingComponent.builder().appComponent(appComponent).bindingModule(new BindingModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        mToolbarTitle.setText("仓库");
       /* Bundle bundle = getIntent().getExtras();
        tvWorkOrder.setText(bundle.getString("pro_scan_work_order", ""));
        tvLineName.setText(bundle.getString("pro_scan_line", ""));
        tvLineNum.setText(bundle.getString("pro_scan_face", ""));*/
        Bundle bundle = getIntent().getExtras();
        workOrderStr = bundle.getString("pro_scan_work_order", "");
        sideStr = bundle.getString("pro_scan_face", "");
        lineStr = bundle.getString("pro_scan_line", "");
        tvWorkOrder.setText("工单：" + workOrderStr);
        tvLineName.setText("线别：" + lineStr);
        tvLineNum.setText("面别：" + sideStr);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_pro_scan_binding;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getSuccess(Result result) {
        materialBlockBarcodeStr = null;
        updateProgress.setVisibility(View.GONE);
        txtClear();
        if (!TextUtils.isEmpty(result.getMessage())) {
            tvHint.setText(result.getMessage());
        }
    }

    @Override
    public void getFailed(Result result) {
        materialBlockBarcodeStr = null;
        updateProgress.setVisibility(View.GONE);
        txtClear();
        if (!TextUtils.isEmpty(result.getMessage())) {
            tvHint.setText(result.getMessage());
        }
    }

    @Override
    public void getFailed(Throwable throwable) {
        materialBlockBarcodeStr = null;
        updateProgress.setVisibility(View.GONE);
        txtClear();
        if (!TextUtils.isEmpty(throwable.getMessage())) {
            tvHint.setText(throwable.getMessage());
        }
    }

    @Override
    public void showLoadingView() {

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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //mToolbarHeight = mToolbar.getHeight();
        super.onWindowFocusChanged(hasFocus);
    }

    @SuppressWarnings("all")
    @Override
    public void onScanSuccess(String barcode) {

        Log.i(TAG, "onScanSuccess: " + barcode);

        try {
            MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
            materialBlockBarcodeStr = barcode;
            tvHint.setText("");
            etMaterialCode.setText(materialBlockBarCode.getDeltaMaterialNumber());
            serialNo = materialBlockBarCode.getStreamNumber();
            if (isAllTvNotEmpty()) {
                List<Map<String, String>> listRes = new ArrayList<>();
                Map<String, String> map = new HashMap<>();
                map.put("material_no", etMaterialCode.getText().toString());
                map.put("serial_no", serialNo);
                map.put("slot", etMaterialStation.getText().toString());
                map.put("feeder", etFeeder.getText().toString());
                map.put("line", lineStr);
                map.put("work_order", workOrderStr);
                map.put("side", sideStr);
                map.put("barcode", materialBlockBarcodeStr);
                listRes.add(map);
                Log.i(TAG, "onScanSuccess: " + new Gson().toJson(listRes));
                getPresenter().uploadToMesFromProcessline(new Gson().toJson(listRes));
                updateProgress.setVisibility(View.VISIBLE);
            }
            return;
        } catch (EntityNotFountException e) {
            try {
                MaterialStation materialStation = (MaterialStation) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_STATION);
                tvHint.setText("");
                etMaterialStation.setText(materialStation.getSource());
                if (isAllTvNotEmpty()) {
                    List<Map<String, String>> listRes = new ArrayList<>();
                    Map<String, String> map = new HashMap<>();
                    map.put("material_no", etMaterialCode.getText().toString());
                    map.put("serial_no", serialNo);
                    map.put("slot", etMaterialStation.getText().toString());
                    map.put("feeder", etFeeder.getText().toString());
                    map.put("line", lineStr);
                    map.put("work_order", workOrderStr);
                    map.put("side", sideStr);
                    map.put("barcode", materialBlockBarcodeStr);
                    listRes.add(map);
                    Log.i(TAG, "onScanSuccess: " + new Gson().toJson(listRes));
                    getPresenter().uploadToMesFromProcessline(new Gson().toJson(listRes));
                    updateProgress.setVisibility(View.VISIBLE);
                }
                return;
            } catch (EntityNotFountException e2) {
                try {
                    Feeder mFeeder = (Feeder) barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER);
                    tvHint.setText("");
                    etFeeder.setText(barcode);
                    if (isAllTvNotEmpty()) {
                        List<Map<String, String>> listRes = new ArrayList<>();
                        Map<String, String> map = new HashMap<>();
                        map.put("material_no", etMaterialCode.getText().toString());
                        map.put("serial_no", serialNo);
                        map.put("slot", etMaterialStation.getText().toString());
                        map.put("feeder", etFeeder.getText().toString());
                        map.put("line", lineStr);
                        map.put("work_order", workOrderStr);
                        map.put("side", sideStr);
                        map.put("barcode", materialBlockBarcodeStr);
                        listRes.add(map);
                        Log.i(TAG, "onScanSuccess: " + new Gson().toJson(listRes));
                        getPresenter().uploadToMesFromProcessline(new Gson().toJson(listRes));
                        updateProgress.setVisibility(View.VISIBLE);
                    }
                    return;
                } catch (EntityNotFountException e3) {
                    tvHint.setText("请扫描正确的条码");
                }
            }
        }
    }

    public boolean isAllTvNotEmpty() {
        String materialCode = etMaterialCode.getText().toString();
        String materialStation = etMaterialStation.getText().toString();
        String feeder = etFeeder.getText().toString();
        return !TextUtils.isEmpty(materialCode) && !TextUtils.isEmpty(materialStation) && !TextUtils.isEmpty(feeder);
    }

    private void txtClear() {
        serialNo = null;
        etFeeder.setText("");
        etMaterialCode.setText("");
        etMaterialStation.setText("");
    }


}
