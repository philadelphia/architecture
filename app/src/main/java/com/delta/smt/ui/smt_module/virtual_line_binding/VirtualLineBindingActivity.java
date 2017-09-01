package com.delta.smt.ui.smt_module.virtual_line_binding;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.entity.VirtualModuleID;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.VirtualLineItem;
import com.delta.smt.ui.smt_module.module_down_details.ModuleDownDetailsActivity;
import com.delta.smt.ui.smt_module.virtual_line_binding.di.DaggerVirtualLineBindingComponent;
import com.delta.smt.ui.smt_module.virtual_line_binding.di.VirtualLineBindingModule;
import com.delta.smt.ui.smt_module.virtual_line_binding.mvp.VirtualLineBindingContract;
import com.delta.smt.ui.smt_module.virtual_line_binding.mvp.VirtualLineBindingPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class VirtualLineBindingActivity extends BaseActivity<VirtualLineBindingPresenter> implements VirtualLineBindingContract.View{

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recy_title)
    RecyclerView recyclerViewTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyclerViewContent;
    List<VirtualLineItem> data_tmp = null;
    String materialBlockNumber;
    String feederNumber;
    String serialNo;
    @BindView(R.id.tv_showWorkOrder)
    TextView tv_showWorkOrder;
    @BindView(R.id.tv_showProductNameMain)
    TextView tv_showProductNameMain;
    @BindView(R.id.tv_showProductName)
    TextView tv_showProductName;
    @BindView(R.id.tv_showLineName)
    TextView tv_showLineName;
    @BindView(R.id.tv_showSide)
    TextView tv_showSide;
    @BindView(R.id.tv_showScan_1)
    TextView tv_showScan_1;
    @BindView(R.id.tv_showScan_2)
    TextView tv_showScan_2;
    String workItemID;
    String side;
    String productNameMain;
    String productName;
    String linName;
    String scan1_label = null;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    int state = 1;
    @BindView(R.id.showMessage)
    TextView showMessage;
    private CommonBaseAdapter<VirtualLineItem> adapterTitle;
    private CommonBaseAdapter<VirtualLineItem> adapter;
    private List<VirtualLineItem> dataList = new ArrayList<>();
    private List<VirtualLineItem> dataSource = new ArrayList<>();
    private String moduleID;
    private static final String TAG = "VirtualLineBindingActiv";
    //二维码
    private int scan_position = -1;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerVirtualLineBindingComponent.builder().appComponent(appComponent).virtualLineBindingModule(new VirtualLineBindingModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {

        Intent intent = this.getIntent();
        workItemID = intent.getStringExtra(Constant.WORK_ITEM_ID);
        side = intent.getStringExtra(Constant.SIDE);
        linName = intent.getStringExtra(Constant.LINE_NAME);
        productName = intent.getStringExtra(Constant.PRODUCT_NAME);
        productNameMain = intent.getStringExtra(Constant.PRODUCT_NAME_MAIN);
        Map<String, String> map = new HashMap<>();
        map.put("work_order", workItemID);
        map.put("side", side);

        String argument = GsonTools.createGsonListString(map);
        getPresenter().getAllVirtualLineBindingItems(argument);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("虚拟线体绑定");

        tv_showWorkOrder.setText("工单号: "+workItemID);
        tv_showProductNameMain.setText("主板: "+productNameMain);
        tv_showProductName.setText("小板: "+productName);
        tv_showLineName.setText("线别："+linName);
        tv_showSide.setText("面别: "+side);

        dataList.add(new VirtualLineItem("模组序号", "虚拟模组ID"));
        adapterTitle = new CommonBaseAdapter<VirtualLineItem>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, VirtualLineItem item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                holder.setText(R.id.tv_moduleID, item.getModel_id());
                holder.setText(R.id.tv_virtualModuleID, item.getVitual_id());
            }

            @Override
            protected int getItemViewLayoutId(int position, VirtualLineItem item) {
                return R.layout.item_virtual_line_binding;
            }
        };
        recyclerViewTitle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTitle.setAdapter(adapterTitle);
        adapter = new CommonBaseAdapter<VirtualLineItem>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, VirtualLineItem item, int position) {
//                if (scan_position == -1) {
//                    holder.itemView.setBackgroundColor(Color.WHITE);
//                } else if (scan_position == position) {
//                    holder.itemView.setBackgroundColor(Color.YELLOW);
//                } else {
//                    holder.itemView.setBackgroundColor(Color.WHITE);
//                }
                if(item.getModel_id().equalsIgnoreCase(moduleID)){
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                }else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_moduleID, item.getModel_id());
                holder.setText(R.id.tv_virtualModuleID, item.getVitual_id());
            }

            @Override
            protected int getItemViewLayoutId(int position, VirtualLineItem item) {
                return R.layout.item_virtual_line_binding;
            }

        };
        recyclerViewContent.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerViewContent.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_virtual_line_binding;
    }

    @Override
    public void onSuccess(List<VirtualLineItem> data) {
        dataSource.clear();
        data_tmp = data ;
        dataSource.addAll(data_tmp);
        adapter.notifyDataSetChanged();
        adapterTitle.notifyDataSetChanged();
        if (isAllModuleBinded(dataSource)) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.WORK_ITEM_ID, workItemID);
            bundle.putString(Constant.PRODUCT_NAME_MAIN, productNameMain);
            bundle.putString(Constant.PRODUCT_NAME, productName);
            bundle.putString(Constant.SIDE, side);
            bundle.putString(Constant.LINE_NAME, linName);
            IntentUtils.showIntent(VirtualLineBindingActivity.this, ModuleDownDetailsActivity.class, bundle);
            VirtualLineBindingActivity.this.finish();

        }


    }

    @Override
    public void onFailed(String message) {
        ToastUtils.showMessage(this, message);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void onNetFailed(Throwable throwable) {
        ToastUtils.showMessage(this, throwable.getMessage());
    }

    @Override
    public void onGetModuleIDSuccess(String value) {
        Log.i(TAG, "onGetModuleIDSuccess: value== " + value);
        moduleID = value;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetModuleIDFailed() {

    }

    @Override
    public void showLoadingView() {
        statusLayout.showLoadingView();
    }

    @Override
    public void showContentView() {
        statusLayout.showContentView();
    }

    @Override
    public void showErrorView() {
        statusLayout.showErrorView();
        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("work_order", workItemID);
                map.put("side", side);
                Gson gson = new Gson();
                String argument = gson.toJson(map);
                getPresenter().getAllVirtualLineBindingItems(argument);
            }
        });
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("work_order", workItemID);
                map.put("side", side);
                String argument = GsonTools.createGsonListString(map);
                getPresenter().getAllVirtualLineBindingItems(argument);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.showMessage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.showMessage:
                showMessage.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onScanSuccess(String barcode) {
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (state) {
            //扫描料盘
            case 1:
                try {
                    showMessage.setVisibility(View.GONE);
                    MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                    materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
                    serialNo = materialBlockBarCode.getStreamNumber();
                    scan1_label = "material";
                    tv_showScan_1.setText(materialBlockNumber);
                    tv_showScan_2.setText("");
                    VibratorAndVoiceUtils.correctVibrator(VirtualLineBindingActivity.this);
                    VibratorAndVoiceUtils.correctVoice(VirtualLineBindingActivity.this);
                    Map<String, String> map = new HashMap<>();
                    map.put("work_order", workItemID);
                    map.put("side", side);
                    map.put("material_no", materialBlockNumber);
                    map.put("serial_no", serialNo);
                    String condition = GsonTools.createGsonListString(map);
                    getPresenter().getVirtualModuleID(condition);
                    state = 2;
                } catch (EntityNotFountException e) {
                    /*try{
                        showMessage.setVisibility(View.GONE);
                        Feeder feeder = (Feeder)barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER);
                        feederNumber = barcode;
                        scan1_label="feeder";
                        tv_showScan_1.setText(feederNumber);
                        VibratorAndVoiceUtils.correctVibrator(VirtualLineBindingActivity.this);
                        VibratorAndVoiceUtils.correctVoice(VirtualLineBindingActivity.this);
                        state = 2;

                    }catch (EntityNotFountException ee) {
                        VibratorAndVoiceUtils.wrongVibrator(VirtualLineBindingActivity.this);
                        VibratorAndVoiceUtils.wrongVoice(VirtualLineBindingActivity.this);
                        showMessage.setText("请扫描料盘！");
                        showMessage.setVisibility(View.VISIBLE);
                    }*/
                    VibratorAndVoiceUtils.wrongVibrator(VirtualLineBindingActivity.this);
                    VibratorAndVoiceUtils.wrongVoice(VirtualLineBindingActivity.this);
                    showMessage.setText("请扫描料盘！");
                    showMessage.setVisibility(View.VISIBLE);
                } catch (ArrayIndexOutOfBoundsException e){
                    VibratorAndVoiceUtils.wrongVibrator(VirtualLineBindingActivity.this);
                    VibratorAndVoiceUtils.wrongVoice(VirtualLineBindingActivity.this);
                    showMessage.setText("请扫描料盘！");
                    showMessage.setVisibility(View.VISIBLE);

                }
                break;
            //扫描虚拟线体
            case 2:
                try {
                    showMessage.setVisibility(View.GONE);
                    VirtualModuleID virtualModuleID = (VirtualModuleID) barCodeParseIpml.getEntity(barcode, BarCodeType.VIRTUALMODULE_ID);
                    tv_showScan_2.setText(virtualModuleID.getSource());
                    //检查此模组是否被绑定
                    if("material".equals(scan1_label)){
                        Map<String, String> map = new HashMap<>();
                        map.put("work_order", workItemID);
                        map.put("side", side);
                        map.put("material_no",materialBlockNumber);
                        map.put("serial_no",serialNo);
                        map.put("virtual_id",virtualModuleID.getSource());

                        String argument = GsonTools.createGsonListString(map);
                        getPresenter().getAllVirtualBindingResult(argument);
                        scan1_label = null;
                        VibratorAndVoiceUtils.correctVibrator(VirtualLineBindingActivity.this);
                        VibratorAndVoiceUtils.correctVoice(VirtualLineBindingActivity.this);
                        state = 1;
                    }else if("feeder".equals(scan1_label)){
                        Map<String, String> map = new HashMap<>();
                        map.put("work_order", workItemID);
                        map.put("side", side);
                        map.put("feeder_id",feederNumber);
                        map.put("virtual_id",virtualModuleID.getSource());
                        String argument = GsonTools.createGsonListString(map);
                        getPresenter().getAllVirtualBindingResult(argument);
                        VibratorAndVoiceUtils.correctVibrator(VirtualLineBindingActivity.this);
                        VibratorAndVoiceUtils.correctVoice(VirtualLineBindingActivity.this);
                        scan1_label = null;
                        state = 1;
                    }

                } catch (EntityNotFountException e) {
                    try {
                        showMessage.setVisibility(View.GONE);
                        MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                        materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
                        serialNo = materialBlockBarCode.getStreamNumber();
                        scan1_label = "material";
                        tv_showScan_1.setText(materialBlockNumber);
                        tv_showScan_2.setText("");
                        VibratorAndVoiceUtils.correctVibrator(VirtualLineBindingActivity.this);
                        VibratorAndVoiceUtils.correctVoice(VirtualLineBindingActivity.this);
                        state = 2;
                    } catch (EntityNotFountException ee) {
                        /*try{
                            showMessage.setVisibility(View.GONE);
                            Feeder feeder = (Feeder)barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER);
                            feederNumber = barcode;
                            scan1_label="feeder";
                            tv_showScan_1.setText(feederNumber);
                            VibratorAndVoiceUtils.correctVibrator(VirtualLineBindingActivity.this);
                            VibratorAndVoiceUtils.correctVoice(VirtualLineBindingActivity.this);
                            state = 2;
                        }catch (EntityNotFountException eee) {
                            VibratorAndVoiceUtils.wrongVibrator(VirtualLineBindingActivity.this);
                            VibratorAndVoiceUtils.wrongVoice(VirtualLineBindingActivity.this);
                            showMessage.setText("请扫描虚拟模组！");
                            showMessage.setVisibility(View.VISIBLE);
                        }*/
                        VibratorAndVoiceUtils.wrongVibrator(VirtualLineBindingActivity.this);
                        VibratorAndVoiceUtils.wrongVoice(VirtualLineBindingActivity.this);
                        showMessage.setText("请扫描虚拟模组！");
                        showMessage.setVisibility(View.VISIBLE);
                    }catch (ArrayIndexOutOfBoundsException ee){
                        VibratorAndVoiceUtils.wrongVibrator(VirtualLineBindingActivity.this);
                        VibratorAndVoiceUtils.wrongVoice(VirtualLineBindingActivity.this);

                        showMessage.setText("请扫描虚拟模组！");
                        showMessage.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }

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

    //判断是否所有的模组被绑定
    public boolean isAllModuleBinded(List<VirtualLineItem> rb){
        boolean res = true;
        if (rb.size()>0){
            for(int i=0;i<rb.size();i++){
                VirtualLineItem virtualLineItem = rb.get(i);
                    if (TextUtils.isEmpty(virtualLineItem.getVitual_id())) {
                        res = false;
                        break;
                    }
            }
        }else{
            res = false;
        }

        return res;
    }
}
