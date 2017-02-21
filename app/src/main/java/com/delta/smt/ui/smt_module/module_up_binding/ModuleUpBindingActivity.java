package com.delta.smt.ui.smt_module.module_up_binding;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.Feeder;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.SpUtil;
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
import com.delta.smt.entity.MaterialAndFeederBindingResult;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.ui.smt_module.module_down_details.ModuleDownDetailsActivity;
import com.delta.smt.ui.smt_module.module_up_binding.di.DaggerModuleUpBindingComponent;
import com.delta.smt.ui.smt_module.module_up_binding.di.ModuleUpBindingModule;
import com.delta.smt.ui.smt_module.module_up_binding.mvp.ModuleUpBindingContract;
import com.delta.smt.ui.smt_module.module_up_binding.mvp.ModuleUpBindingPresenter;
import com.delta.smt.ui.smt_module.virtual_line_binding.VirtualLineBindingActivity;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingActivity extends BaseActivity<ModuleUpBindingPresenter> implements ModuleUpBindingContract.View, BarCodeIpml.OnScanSuccessListener,CompoundButton.OnCheckedChangeListener{


    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyContent;
    @BindView(R.id.btn_upload)
    AppCompatButton btnUpload;
    @BindView(R.id.container)
    CoordinatorLayout container;

    private CommonBaseAdapter<ModuleUpBindingItem.RowsBean> adapterTitle;
    private CommonBaseAdapter<ModuleUpBindingItem.RowsBean> adapter;
    private List<ModuleUpBindingItem.RowsBean> dataList = new ArrayList<>();
    private List<ModuleUpBindingItem.RowsBean> dataSource = new ArrayList<>();

    //二维码
    private BarCodeIpml barCodeIpml = new BarCodeIpml();

    private int scan_position = -1;

    private String materialBlockCodeCache = null;
    private String feederCodeCache = null;

    private String workItemID;
    private String side;
    private String productNameMain;
    private String productName;
    private String linName;

    private String materialBlockNumber;
    private String serialNo;

    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;

    @BindView(R.id.automatic_upload)
    AppCompatCheckBox automaticUpload;

    public String moduleUpAutomaticUpload = null;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleUpBindingComponent.builder().appComponent(appComponent).moduleUpBindingModule(new ModuleUpBindingModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        automaticUpload.setOnCheckedChangeListener(this);
        moduleUpAutomaticUpload = SpUtil.getStringSF(ModuleUpBindingActivity.this, "module_up_automatic_upload");

        Intent intent = ModuleUpBindingActivity.this.getIntent();
        workItemID = intent.getStringExtra(Constant.WORK_ITEM_ID);
        side = intent.getStringExtra(Constant.SIDE);
        linName = intent.getStringExtra(Constant.LINE_NAME);
        productName = intent.getStringExtra(Constant.PRODUCT_NAME);
        productNameMain = intent.getStringExtra(Constant.PRODUCT_NAME_MAIN);

        Map<String, String> map = new HashMap<>();
        map.put("work_order", workItemID);
        map.put("side",side);
        Gson gson = new Gson();
        String argument = gson.toJson(map);
        getPresenter().getAllModuleUpBindingItems(argument);
        barCodeIpml.setOnGunKeyPressListener(this);
    }

    @Override
    protected void initView() {
        if (moduleUpAutomaticUpload == null) {
            SpUtil.SetStringSF(ModuleUpBindingActivity.this, "module_up_automatic_upload", "false");
            moduleUpAutomaticUpload = "false";
            automaticUpload.setChecked(false);
        } else if ("false".equals(moduleUpAutomaticUpload)) {
            automaticUpload.setChecked(false);
        } else {
            automaticUpload.setChecked(true);
        }

        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("上模组");

        recyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        dataList.add(new ModuleUpBindingItem.RowsBean("工单","料号", "流水码", "FeederID", "模组料站", "",""));
        adapterTitle = new CommonBaseAdapter<ModuleUpBindingItem.RowsBean>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleUpBindingItem.RowsBean item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                holder.setText(R.id.tv_materialID, item.getMaterial_no());
                holder.setText(R.id.tv_feederID, item.getFeeder_id());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getSlot());
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleUpBindingItem.RowsBean item) {
                return R.layout.item_module_up_binding;
            }
        };

        recyTitle.setAdapter(adapterTitle);
        adapter = new CommonBaseAdapter<ModuleUpBindingItem.RowsBean>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleUpBindingItem.RowsBean item, int position) {
                if (scan_position == -1) {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else if (scan_position == position) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_materialID, item.getMaterial_no());
                holder.setText(R.id.tv_feederID, item.getFeeder_id());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getSlot());
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleUpBindingItem.RowsBean item) {
                return R.layout.item_module_up_binding;
            }

        };

        recyContent.setAdapter(adapter);
        //adapter.setOnItemClickListener(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_up_binding;
    }

    @Override
    public void onSuccess(ModuleUpBindingItem data) {
        if (data.getMsg().toLowerCase().equals("success")){
            dataSource.clear();
            List<ModuleUpBindingItem.RowsBean> rowsBeen = data.getRows();
            dataSource.addAll(rowsBeen);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onFalied() {
        Toast.makeText(this, "上模组产线列表网络请求失败！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessBinding(ModuleUpBindingItem data) {
        if(data.getMsg().toLowerCase().equals("success")){
            Snackbar.make(container,dataSource.get(scan_position).getSlot()+"绑定成功！",Snackbar.LENGTH_INDEFINITE).show();
            dataSource.clear();
            List<ModuleUpBindingItem.RowsBean> rowsBeen = data.getRows();
            dataSource.addAll(rowsBeen);
            scan_position = -1;
            adapter.notifyDataSetChanged();

            state = 1;
            if(isAllFeederBinded()){
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("工单"+workItemID+"上模组完成！")
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ModuleUpBindingActivity.this.finish();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        }else{

        }
    }

    @Override
    public void onFailedBinding() {

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
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
    }

    @OnClick({R.id.btn_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upload:
                getPresenter().upLoadToMES();
                break;
        }
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

    int state = 1;

    @Override
    public void onScanSuccess(String barcode) {
        Toast.makeText(this, barcode, Toast.LENGTH_SHORT).show();
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (state) {
            case 1:
                try {
                    //ArrayIndexOutOfBoundsException
                    MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                    materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
                    serialNo = materialBlockBarCode.getStreamNumber();
                    if(!isExistInDataSourceAndHighLight(materialBlockNumber,serialNo,dataSource)){
                        VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                        VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                        Snackbar.make(container,"该料盘不属于此套工单，请确认工单及扫描是否正确！",Snackbar.LENGTH_INDEFINITE).show();
                    }else{
                        VibratorAndVoiceUtils.correctVibrator(ModuleUpBindingActivity.this);
                        VibratorAndVoiceUtils.correctVoice(ModuleUpBindingActivity.this);
                        state = 2;
                    }

                } catch (EntityNotFountException e) {
                    Snackbar.make(container,"请先扫描料盘码！",Snackbar.LENGTH_INDEFINITE).show();
                } catch (ArrayIndexOutOfBoundsException e){
                    Snackbar.make(container,"请先扫描料盘码！",Snackbar.LENGTH_INDEFINITE).show();
                }
                break;
            case 2:
                    try {
                        Feeder feederCode = (Feeder) barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER);
                        if(isFeederExistInDataSource(barcode,dataSource)){
                            Snackbar.make(container,"此Feeder已经被绑定！",Snackbar.LENGTH_INDEFINITE).show();
                        }else{
                            Map<String, String> map = new HashMap<>();
                            map.put("work_order", workItemID);
                            map.put("material_no", materialBlockNumber);
                            map.put("feeder_id", barcode);
                            map.put("serial_no", serialNo);
                            map.put("side",side);
                            Gson gson = new Gson();
                            String argument = gson.toJson(map);
                            getPresenter().getMaterialAndFeederBindingResult(argument);
                        }
                    } catch (EntityNotFountException e) {
                        try {
                            MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                            materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
                            serialNo = materialBlockBarCode.getStreamNumber();
                            if(!isExistInDataSourceAndHighLight(materialBlockNumber,serialNo,dataSource)){
                                VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                                VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                                Snackbar.make(container,"该料盘不属于此套工单，请确认工单及扫描是否正确！",Snackbar.LENGTH_INDEFINITE).show();
                            }else{
                                VibratorAndVoiceUtils.correctVibrator(ModuleUpBindingActivity.this);
                                VibratorAndVoiceUtils.correctVoice(ModuleUpBindingActivity.this);
                                state = 2;
                            }

                        } catch (EntityNotFountException ee) {
                            ee.printStackTrace();
                            Snackbar.make(container,"请扫描feeder ID！",Snackbar.LENGTH_INDEFINITE).show();
                        } catch (ArrayIndexOutOfBoundsException ee){
                            ee.printStackTrace();
                            Snackbar.make(container,"请扫描feeder ID！",Snackbar.LENGTH_INDEFINITE).show();
                        }
                        e.printStackTrace();
                    } catch (ArrayIndexOutOfBoundsException e){
                        Snackbar.make(container,"请先扫描料盘码！",Snackbar.LENGTH_INDEFINITE).show();
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


    public void setItemHighLightBasedOnMID(int position) {
        scan_position = position;
        adapter.notifyDataSetChanged();
    }

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

    public boolean isExistInDataSourceAndHighLight(String item_m,String item_s, List<ModuleUpBindingItem.RowsBean> list) {
        if (list.size() > 0) {
            for (int i=0;i<list.size();i++) {
                if(list.get(i).getMaterial_no().equals(item_m)&&list.get(i).getSerial_no().equals(item_s)){
                    setItemHighLightBasedOnMID(i);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }



    public boolean isFeederExistInDataSource(String item, List<ModuleUpBindingItem.RowsBean> list) {

        if (list.size() > 0) {

            for (ModuleUpBindingItem.RowsBean list_item : list) {

                if (list_item.getFeeder_id()!=null&&list_item.getFeeder_id().equals(item)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }

    public boolean isAllFeederBinded() {
        boolean res = true;

        if (dataSource.size() > 0) {
            for (ModuleUpBindingItem.RowsBean listItem : dataSource) {
                if (listItem.getFeeder_id()!=null&&listItem.getFeeder_id().length()>0) {
                }else{
                    res = false;
                    break;
                }
            }
        } else {
            res = false;
        }
        return res;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView==automaticUpload){
            if(isChecked){
                SpUtil.SetStringSF(ModuleUpBindingActivity.this, "module_up_automatic_upload", "true");
            }else{
                SpUtil.SetStringSF(ModuleUpBindingActivity.this, "module_up_automatic_upload", "false");
            }
        }
    }
}
