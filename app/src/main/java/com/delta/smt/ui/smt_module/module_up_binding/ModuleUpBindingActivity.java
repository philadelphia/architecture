package com.delta.smt.ui.smt_module.module_up_binding;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.RecycleViewUtils;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.ui.smt_module.module_up_binding.di.DaggerModuleUpBindingComponent;
import com.delta.smt.ui.smt_module.module_up_binding.di.ModuleUpBindingModule;
import com.delta.smt.ui.smt_module.module_up_binding.mvp.ModuleUpBindingContract;
import com.delta.smt.ui.smt_module.module_up_binding.mvp.ModuleUpBindingPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


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

public class ModuleUpBindingActivity extends BaseActivity<ModuleUpBindingPresenter> implements ModuleUpBindingContract.View, CompoundButton.OnCheckedChangeListener {

    private String moduleUpAutomaticUpload = null;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recy_title)
    RecyclerView recyclerViewTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyclerViewContent;
    @BindView(R.id.btn_upload)
    AppCompatButton btnUpload;
  
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @BindView(R.id.automatic_upload)
    AppCompatCheckBox automaticUpload;


    @BindView(R.id.tv_work_order)
    TextView tv_workOrder;
    @BindView(R.id.tv_line_num)
    TextView tv_side;
    @BindView(R.id.tv_line_name)
    TextView tv_line;

    private int state = 1;
    //private Snackbar mSnackbar = null;
    @BindView(R.id.showMessage)
    TextView showMessage;
    private CommonBaseAdapter<ModuleUpBindingItem> adapterTitle;
    private CommonBaseAdapter<ModuleUpBindingItem> adapter;
    private List<ModuleUpBindingItem> dataList = new ArrayList<>();
    private List<ModuleUpBindingItem> dataSource = new ArrayList<>();
    private int scan_position = -1;
    private String workItemID;
    private String side;
//    private String productNameMain;
//    private String productName;
    private String lineName;
    private String materialBlockNumber;
    private String serialNo;
    private String argument;
    private static final String TAG = "ModuleUpBindingActivity";
    private LinearLayoutManager linearLayoutManager;
    private String quantaty;

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
        lineName = intent.getStringExtra(Constant.LINE_NAME);
//        productName = intent.getStringExtra(Constant.PRODUCT_NAME);
//        productNameMain = intent.getStringExtra(Constant.PRODUCT_NAME_MAIN);
        tv_workOrder.setText(getResources().getString(R.string.WorkID) +":   "+ workItemID);
        tv_line.setText(getResources().getString(R.string.Line) +":   "+  lineName);
        tv_side.setText(getResources().getString(R.string.Side) +":   "+  side);
        Map<String, String> map = new HashMap<>();
        map.put("work_order", workItemID);
        map.put("side", side);
        argument = GsonTools.createGsonListString(map);
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
        toolbarTitle.setText("上模组");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

        recyclerViewTitle.setLayoutManager(new LinearLayoutManager(getContext()));
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        recyclerViewContent.setLayoutManager(linearLayoutManager);
//        dataList.add(new ModuleUpBindingItem("料号", "FeederID", "模组料站"));
        dataList.add(new ModuleUpBindingItem());
        adapterTitle = new CommonBaseAdapter<ModuleUpBindingItem>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleUpBindingItem item, int position) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleUpBindingItem item) {
                return R.layout.item_module_up_binding;
            }
        };

        recyclerViewTitle.setAdapter(adapterTitle);
        adapter = new CommonBaseAdapter<ModuleUpBindingItem>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleUpBindingItem item, int position) {
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
            protected int getItemViewLayoutId(int position, ModuleUpBindingItem item) {
                return R.layout.item_module_up_binding;
            }

        };

        recyclerViewContent.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_up_binding;
    }

    @Override
    public void onSuccess(List<ModuleUpBindingItem> data) {
        state = 1;
        scan_position = -1;
        dataSource.clear();
        dataSource.addAll(data);
        Log.i(TAG, "onSuccess:后台返回的数据长度是： " + dataSource.size());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onFailed(String  message) {
        ToastUtils.showMessage(this, message);
    }

    @Override
    public void onNetFailed(Throwable throwable) {
        ToastUtils.showMessage(this, throwable.getMessage());
    }

    @SuppressWarnings("all")
    @Override
    public void onSuccessBinding(List<ModuleUpBindingItem> dataSource) {
        dataSource.clear();
        List<ModuleUpBindingItem> rowsBeen = dataSource;
        dataSource.addAll(rowsBeen);
        scan_position = -1;
        adapter.notifyDataSetChanged();
        state = 1;
        if (isAllFeederBinded()) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("工单" + workItemID + "上模组完成！")
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


    }

//    @Override
//    public void onFailedBinding(ModuleUpBindingItem data) {
//        ToastUtils.showMessage(this, data.getMsg());
//    }

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
                getPresenter().getAllModuleUpBindingItems(argument);
            }
        });
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllModuleUpBindingItems(argument);
            }
        });
    }

    @OnClick({R.id.btn_upload, R.id.showMessage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upload:
                getPresenter().upLoadToMES();
                break;
            case R.id.showMessage:
                showMessage.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getAllModuleUpBindingItems(argument);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressWarnings("all")
    @Override
    public void onScanSuccess(String barcode) {
        showMessage.setVisibility(View.GONE);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (state) {
            case 1:     //扫描料盘
                try {
                    parseMaterial(barcode, barCodeParseIpml);

                } catch (EntityNotFountException e) {
                    VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                    VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                    showMessage.setText("请先扫描料盘码！");
                    showMessage.setVisibility(View.VISIBLE);
                    state = 1;
                } catch (ArrayIndexOutOfBoundsException e) {
                    VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                    VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                    showMessage.setText("请先扫描料盘码！");
                    showMessage.setVisibility(View.VISIBLE);
                    state = 1;
                }
                break;
            case 2:     //扫描Feeder
                try {
                    Feeder feederCode = (Feeder) barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER);
                    showMessage.setVisibility(View.GONE);
                    if (isFeederExistInDataSource(barcode, dataSource)) {
                        VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                        VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                        showMessage.setText("此Feeder已经被绑定！");
                        showMessage.setVisibility(View.VISIBLE);
                    } else {
                        VibratorAndVoiceUtils.correctVibrator(ModuleUpBindingActivity.this);
                        VibratorAndVoiceUtils.correctVoice(ModuleUpBindingActivity.this);
                        JsonArray jsonArray = new JsonArray();
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("work_order", workItemID);
                        jsonObject.addProperty("material_no", materialBlockNumber);
                        jsonObject.addProperty("feeder_id", barcode);
                        jsonObject.addProperty("serial_no", serialNo);
                        jsonObject.addProperty("side", side);
                        jsonObject.addProperty("qty", quantaty);
                        jsonArray.add(jsonObject);
                        String argument = jsonArray.toString();
                        getPresenter().getMaterialAndFeederBindingResult(argument);
                    }
                } catch (EntityNotFountException e) {

                    try {
                        parseMaterial(barcode, barCodeParseIpml);

                    } catch (EntityNotFountException ee) {
                        ee.printStackTrace();
                        VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                        VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                        showMessage.setText("此处不能识别此码！");
                        showMessage.setVisibility(View.VISIBLE);
                    } catch (ArrayIndexOutOfBoundsException ee) {
                        ee.printStackTrace();
                        VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                        VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                        showMessage.setText("此处不能识别此码！");
                        showMessage.setVisibility(View.VISIBLE);
                    }
                    e.printStackTrace();
                }
                break;
        }
    }

    private void parseMaterial(String barcode, BarCodeParseIpml barCodeParseIpml) throws EntityNotFountException {
        MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
        Log.i(TAG, "onScanSuccess: "+ barcode);
        showMessage.setVisibility(View.GONE);
        materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
        serialNo = materialBlockBarCode.getStreamNumber();
        quantaty =  materialBlockBarCode.getCount();
        Log.i(TAG, "materialBlockNumber: " + materialBlockNumber);
        Log.i(TAG, "serialNo: " + serialNo
        );
        if (!isExistInDataSourceAndHighLight(materialBlockNumber, serialNo, dataSource)) {
            VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
            VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
            showMessage.setText("该料盘不属于此套工单，请确认工单及扫描是否正确！");
            showMessage.setVisibility(View.VISIBLE);
            state = 1;
        } else {
            if (isMaterialBinded(materialBlockBarCode)){
                VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                Toast.makeText(this, "注意:该料盘已经绑定", Toast.LENGTH_SHORT).show();
            }else {
                VibratorAndVoiceUtils.correctVibrator(ModuleUpBindingActivity.this);
                VibratorAndVoiceUtils.correctVoice(ModuleUpBindingActivity.this);
            }
            state = 2;
            Log.i(TAG, "onScanSuccess: "+"开始扫描Feeder");
        }
    }


    public void setItemHighLightBasedOnMID(int position) {
        scan_position = position;
        RecycleViewUtils.scrollToMiddle(linearLayoutManager, position, recyclerViewContent);
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

    public boolean isExistInDataSourceAndHighLight(String item_m, String item_s, List<ModuleUpBindingItem> list) {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getMaterial_no().equals(item_m) && list.get(i).getSerial_no().equals(item_s)) {
                    setItemHighLightBasedOnMID(i);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }


    public boolean isFeederExistInDataSource(String item, List<ModuleUpBindingItem> list) {

        if (list.size() > 0) {

            for (ModuleUpBindingItem list_item : list) {

                if (list_item.getFeeder_id() != null && list_item.getFeeder_id().equals(item)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }

    @SuppressWarnings("all")
    public boolean isAllFeederBinded() {
        boolean res = true;
        if (dataSource.size() > 0) {
            for (ModuleUpBindingItem listItem : dataSource) {
                if (listItem.getFeeder_id() != null && listItem.getFeeder_id().length() > 0) {
                } else {
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
        if (buttonView == automaticUpload) {
            if (isChecked) {
                SpUtil.SetStringSF(ModuleUpBindingActivity.this, "module_up_automatic_upload", "true");
            } else {
                SpUtil.SetStringSF(ModuleUpBindingActivity.this, "module_up_automatic_upload", "false");
            }
        }
    }

    public boolean isMaterialBinded(MaterialBlockBarCode materialBlockBarCode){
     boolean flag = false;
        for (ModuleUpBindingItem rowsBean : dataSource) {
            if (rowsBean.getMaterial_no().equalsIgnoreCase(materialBlockBarCode.getDeltaMaterialNumber()) && rowsBean.getSerial_no().equalsIgnoreCase(materialBlockBarCode.getStreamNumber())){
                if (!TextUtils.isEmpty(rowsBean.getFeeder_id())){
                   flag = true;
                    break;
                }

            }
        }
        return flag;
    }

}
