package com.delta.smt.ui.smt_module.virtual_line_binding;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.Feeder;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.entity.VirtualModuleID;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.VirtualLineBindingItem;
import com.delta.smt.ui.smt_module.module_down_details.ModuleDownDetailsActivity;
import com.delta.smt.ui.smt_module.virtual_line_binding.di.DaggerVirtualLineBindingComponent;
import com.delta.smt.ui.smt_module.virtual_line_binding.di.VirtualLineBindingModule;
import com.delta.smt.ui.smt_module.virtual_line_binding.mvp.VirtualLineBindingContract;
import com.delta.smt.ui.smt_module.virtual_line_binding.mvp.VirtualLineBindingPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class VirtualLineBindingActivity extends BaseActivity<VirtualLineBindingPresenter> implements VirtualLineBindingContract.View, BarCodeIpml.OnScanSuccessListener{

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyContent;
    @BindView(R.id.container)
    CoordinatorLayout container;
    List<VirtualLineBindingItem.RowsBean> data_tmp = null;
    String materialBlockNumber;
    String feederNumber;
    String virtualModuleID;
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
    private CommonBaseAdapter<VirtualLineBindingItem.RowsBean> adapterTitle;
    private CommonBaseAdapter<VirtualLineBindingItem.RowsBean> adapter;
    private List<VirtualLineBindingItem.RowsBean> dataList = new ArrayList<>();
    private List<VirtualLineBindingItem.RowsBean> dataSource = new ArrayList<>();
    //二维码
    private BarCodeIpml barCodeIpml = new BarCodeIpml();
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
        Gson gson = new Gson();
        String argument = gson.toJson(map);
        getPresenter().getAllVirtualLineBindingItems(argument);
        barCodeIpml.setOnGunKeyPressListener(this);
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

        dataList.add(new VirtualLineBindingItem.RowsBean("模组序号", "虚拟模组ID"));
        adapterTitle = new CommonBaseAdapter<VirtualLineBindingItem.RowsBean>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, VirtualLineBindingItem.RowsBean item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                holder.setText(R.id.tv_moduleID, item.getModel_id());
                holder.setText(R.id.tv_virtualModuleID, item.getVitual_id());
            }

            @Override
            protected int getItemViewLayoutId(int position, VirtualLineBindingItem.RowsBean item) {
                return R.layout.item_virtual_line_binding;
            }
        };
        recyTitle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyTitle.setAdapter(adapterTitle);
        adapter = new CommonBaseAdapter<VirtualLineBindingItem.RowsBean>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, VirtualLineBindingItem.RowsBean item, int position) {
                if (scan_position == -1) {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else if (scan_position == position) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_moduleID, item.getModel_id());
                holder.setText(R.id.tv_virtualModuleID, item.getVitual_id());
            }

            @Override
            protected int getItemViewLayoutId(int position, VirtualLineBindingItem.RowsBean item) {
                return R.layout.item_virtual_line_binding;
            }

        };
        recyContent.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyContent.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_virtual_line_binding;
    }

    @Override
    public void onSuccess(VirtualLineBindingItem data) {
        ToastUtils.showMessage(this, data.getMsg());
        dataSource.clear();
        data_tmp = data.getRows();
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
    public void onFalied(VirtualLineBindingItem data) {
        ToastUtils.showMessage(this, data.getMsg());
    }

    @Override
    public void onNetFailed(Throwable throwable) {
        ToastUtils.showMessage(this, throwable.getMessage());
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
    public void onScanSuccess(String barcode) {
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (state) {
            case 1:
                try {
                    MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                    materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
                    serialNo = materialBlockBarCode.getStreamNumber();
                    scan1_label = "material";
                    tv_showScan_1.setText(materialBlockNumber);
                    state = 2;
                } catch (EntityNotFountException e) {
                    try{
                        Feeder feeder = (Feeder)barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER);
                        feederNumber = barcode;
                        scan1_label="feeder";
                        tv_showScan_1.setText(feederNumber);
                        state = 2;
                    }catch (EntityNotFountException ee) {
                        new AlertDialog.Builder(this)
                                .setTitle("提示")
                                .setMessage("请扫描料盘或feederID！")
                                .setCancelable(false)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .create()
                                .show();
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("请扫描料盘或feederID！")
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create()
                            .show();

                }
                break;
            case 2:
                try {
                    VirtualModuleID virtualModuleID = (VirtualModuleID) barCodeParseIpml.getEntity(barcode, BarCodeType.VIRTUALMODULE_ID);
                    tv_showScan_2.setText(virtualModuleID.getSource());
                    //检查此模组是否被绑定
                    if("material".equals(scan1_label)){
                        Map<String, String> map = new HashMap<>();
                        map.put("work_order", workItemID);
                        map.put("side", side);
                        map.put("material_no",materialBlockNumber);
                        map.put("serial_no",serialNo);
                        map.put("vitual_id",virtualModuleID.getSource());
                        Gson gson = new Gson();
                        String argument = gson.toJson(map);
                        getPresenter().getAllVirtualBindingResult(argument);
                        scan1_label = null;
                        state = 1;
                    }else if("feeder".equals(scan1_label)){
                        Map<String, String> map = new HashMap<>();
                        map.put("work_order", workItemID);
                        map.put("side", side);
                        map.put("feeder_id",feederNumber);
                        map.put("vitual_id",virtualModuleID.getSource());
                        Gson gson = new Gson();
                        String argument = gson.toJson(map);
                        getPresenter().getAllVirtualBindingResult(argument);
                        scan1_label = null;
                        state = 1;
                    }else{

                    }

                } catch (EntityNotFountException e) {
                    try {
                        MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                        materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
                        serialNo = materialBlockBarCode.getStreamNumber();
                        scan1_label = "material";
                        tv_showScan_1.setText(materialBlockNumber);
                        state = 2;
                    } catch (EntityNotFountException ee) {
                        try{
                            Feeder feeder = (Feeder)barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER);
                            feederNumber = barcode;
                            scan1_label="feeder";
                            tv_showScan_1.setText(feederNumber);
                            state = 2;
                        }catch (EntityNotFountException eee) {
                            new AlertDialog.Builder(this)
                                    .setTitle("提示")
                                    .setMessage("请扫描虚拟模组！")
                                    .setCancelable(false)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    }catch (ArrayIndexOutOfBoundsException ee){
                        new AlertDialog.Builder(this)
                                .setTitle("提示")
                                .setMessage("请扫描虚拟模组！")
                                .setCancelable(false)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .create()
                                .show();
                    }
                }
                break;
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
    public boolean isAllModuleBinded(List<VirtualLineBindingItem.RowsBean> rb){
        boolean res = true;
        if (rb.size()>0){
            for(int i=0;i<rb.size();i++){
                VirtualLineBindingItem.RowsBean rowsBean = rb.get(i);
                if(rowsBean.getVitual_id()==null||"".equals(rowsBean.getVitual_id())){
                    res = false;
                    break;
                }
            }
        }else{
            res = false;
        }

        return res;
    }

    /*public boolean isExistInDataSource(String item,List<VirtualLineBindingItem.RowsBean> data_tmp) {
        if (data_tmp.size() > 0) {
            for (VirtualLineBindingItem.RowsBean list_item : data_tmp) {
                if (list_item.getVitual_id().equals(item)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }

    public boolean isVirtualExistInDataSource(String item, List<VirtualLineBindingItemNative> list) {
        if (list.size() > 0) {
            for (VirtualLineBindingItemNative list_item : list) {
                if (list_item.getVirtual_module_id().equals(item)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }*/
}
